package com.wyj.test.interview;

import java.util.HashMap;
import java.util.Map;

/**
 * 请基于最不经常使用（LFU）缓存失效策略设计缓存公共组件，接入其他服务
 * Please design a common cache components based on the least frequently used (LFU) cache failure strategy to access other services
 * 1、LFU: 当缓存达到其容量 capacity  时，则应该在插入新项之前，移除最使用频率（读和写都算一次）最低的项。
 * 1、LFU: When the cache reaches its capacity, it should invalidate and remove the least frequently（read and set makes count plus one） used key before inserting a new item.
 *
 * 2、缓存数据读写必须以 O(1) 的平均时间复杂度运行。
 * 2. read and write must each run in O(1) average time complexity.
 */
public class LFU {

    private static class Node {
        Node pre;
        Node next;
        final String key;
        Integer val;
        /**
         * 访问频率
         */
        int frequency;

        Node() {
            key = null;
        }

        Node(String key, int val) {
            this.key = key;
            this.val = val;
            this.frequency = 1;
        }

    }

    private static class Segment {

        /**
         * 本访问频率段，的头指针节点
         */
        final Node head;

        final Node tail;

        final int frequency;


        Segment(int frequency) {
            this.frequency = frequency;
            head = new Node();
            tail = new Node();
            head.next = tail;
            tail.pre = head;
        }

        void add(Node node) {
            Node next = head.next;

            node.pre = head;
            head.next = node;

            node.next = next;
            next.pre = node;
        }

        boolean isEmpty() {
            return this.head.next == this.tail;
        }
    }

    private final Map<String, Node> dataMap;

    /**
     * 各频率段，包含头指针 O1拿段的头节点
     */
    private final Map<Integer, Segment> segmentMap;

    /**
     * 最小访问次数的 frequency
     */
    private int minFrequency;

    /**
     * 容量
     */
    private final int capacity;

    private int size;


    public LFU(int capacity) {
        if (capacity <= 0) {
            throw new RuntimeException("invalid capacity, need > 0");
        }
        this.capacity = capacity;
        this.size = 0;

        dataMap = new HashMap<>();
        segmentMap = new HashMap<>();
    }

    /**
     * 添加一个元素
     * @param key 元素的键
     * @param value 元素的值
     */
    public synchronized void put(String key, int value) {
        //1. 放入到map
        Node node = dataMap.get(key);
        if (node != null) {
            // 已经存在的一个节点
            node.val = value;
            // 增加频次
            incrFrequency(node);
        } else {
            // 添加新的节点
            node = new Node(key, value);
            // 添加到dataMap中
            dataMap.put(key, node);
            // 添加到对应的 segment中
            addInSegment(node);
            // 新元素的添加，直接更新minFrequency=1
            minFrequency = 1;
            // 更新总数
            size ++;
            // 如果达到阈值，删除最后一个元素
            removeIfFull();
        }
    }


    /**
     * 查询 键对应的值
     * @param key 键
     * @return 不存在返回null
     */
    public synchronized Integer get(String key) {
        Node node = dataMap.get(key);
        if (node == null) {
            return null;
        }
        incrFrequency(node);
        return node.val;
    }

    /**
     * 删除某个键值对
     * @param key 键
     * @return 删除已存在的数据 返回该数据对应的值。如果键不存在，则返回null
     */
    public synchronized Integer remove(String key) {
        Node node = dataMap.remove(key);
        if (node == null) {
            return null;
        }

        // 节点从segment中移除
        node.pre.next = node.next;
        node.next.pre = node.pre;

        Segment segment = segmentMap.get(node.frequency);

        // 如果这个segment 空了
        if (segment.isEmpty()) {
            segmentMap.remove(segment.frequency);
            // 如果正好是 最小的segment 空了，则 更新最小 minFrequency
            if (segment.frequency == minFrequency) {
                int minFre = minFrequency +1;
                while (!segmentMap.containsKey(minFre)) {
                    minFre++;
                }
                minFrequency = minFre;
            }
        }
        size--;

        return node.val;
    }


    private void incrFrequency(Node node) {
        int oldFrequency = node.frequency;
        // 先把该节点从老的 segment中 remove掉
        node.pre.next = node.next;
        node.next.pre = node.pre;

        // 访问频次+1
        node.frequency++;

        // 添加到新的segment中
        addInSegment(node);

        // 如果老的segment 中已经没有数据了，就把这个segment 干掉
        Segment oldSegment = segmentMap.get(oldFrequency);
        if (oldSegment.isEmpty()) {
            segmentMap.remove(oldFrequency);
            // 被干掉的segment 就是频次最小的 segment  需要更新 minFrequency 的值
            if (oldFrequency == minFrequency) {
                minFrequency++;
            }
        }
    }

    private void addInSegment(Node node) {
        Segment segment = segmentMap.get(node.frequency);
        if (segment == null) {
            segment = new Segment(node.frequency);
            segmentMap.put(node.frequency, segment);
        }
        segment.add(node);
    }

    private void removeIfFull() {
        if (size <= capacity) {
            return;
        }

        // minFreSegment 一定有数据
        // 移除最小segment 的 最后一条数据
        Segment minFreSegment = segmentMap.get(minFrequency);
        Node removedNode = minFreSegment.tail.pre;
        remove(removedNode.key);
    }


    public Integer last() {
        if (size > 0) {
            return segmentMap.get(minFrequency).tail.pre.val;
        }
        return null;
    }

    public static void main(String[] args) {
        LFU lfu = new LFU(3);
        lfu.put("1", 1);
        lfu.put("2", 2);
        lfu.put("3", 3);
        // 预期1
        System.out.println("last:" + lfu.last());

        lfu.put("4", 4);
        // 预期2， 1被自动删除
        System.out.println("last:" + lfu.last());

        // 预期null last=2
        System.out.println("1:"+lfu.get("1")+", last:" + lfu.last());

        // 预期2， last=3
        System.out.println("2:"+lfu.get("2")+", last:" + lfu.last());

        lfu.get("2");
        lfu.get("3");
        lfu.remove("4");
        // 预期3
        System.out.println("last:" + lfu.last());

        lfu.get("3");
        // 预期2
        System.out.println("last:" + lfu.last());
    }

}
