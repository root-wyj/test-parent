package com.wyj.test.interview;


/**
 * 给你一个链表，删除链表的倒数第 n 个结点，并且返回链表的头结点。
 *
 * 输入：head = [1,2], n = 2
 * 输出：[1,2,3,5]
 */
public class C {

    static class ListNode {

        int val;
        ListNode next;

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }

        @Override
        public String toString() {
            ListNode node = this;
            StringBuilder s = new StringBuilder();
            while (node != null) {
                s.append(node.val).append(",");
                node = node.next;
            }
            return s.toString();
        }
    }

    public static ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode firstNode = head;
        ListNode secondNode = null;
        int k=0;
        while (k < n && firstNode != null) {
            firstNode = firstNode.next;
            k++;
        }

        if (k < n && firstNode == null) {
            // 如果n超过了 链表的最大长度，直接返回
            return head;
        } else if (k == n && firstNode == null) {
            // 链表长度正好为n
            return head.next;
        }

        secondNode = head;

        while (firstNode.next != null) {
            firstNode = firstNode.next;
            secondNode = secondNode.next;
        }

        // remove secondNode.next
        ListNode removing = secondNode.next;
        if (removing != null) {
            secondNode.next = removing.next;
        }
        return head;
    }

    public static void main(String[] args) {
        ListNode node = new ListNode(1, new ListNode(2, new ListNode(3, new ListNode(4, new ListNode( 5, null)))));
        System.out.println(removeNthFromEnd(node, 2));
    }
}
