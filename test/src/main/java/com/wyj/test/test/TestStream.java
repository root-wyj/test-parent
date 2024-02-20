package com.wyj.test.test;

import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import lombok.Data;

/**
 * @author wuyingjie <13693653307@163.com>
 * Created on 2021-01-12
 */
public class TestStream {

    public static void main(String[] args) {
        sort();
        reduce();
        String rateStr = "{\"rate\":\"0.0011\"}";
        BigDecimal b1 = new BigDecimal("0.0011");
        System.out.println(b1);
        BigDecimal b2 = b1.setScale(6, RoundingMode.HALF_UP);
        System.out.println(b2);

        String dateTime = LocalDate.now().minusMonths(11).format(DateTimeFormatter.ofPattern("yyyy-MM"));
        System.out.println(dateTime);
        Foo f1 = new Foo("l", 1, 1),
                f2 = new Foo("l", 1, 2),
                f3 = new Foo("l", 1, 3),
                f4 = new Foo("c", 1, 2),
                f5 = new Foo("c", 1, 2),
                f6 = new Foo("q", 1, 2),
                f7 = new Foo("q", 1, 2);
        Map<String, Foo> collect = Stream.of(f1, f2, f3, f4, f5, f6, f7)
                .collect(Collectors.groupingBy(
                        Foo::getName,
                        Collector.of(() -> new Foo(null, 0, 0), (o, p) -> {
                            if (o.getName() == null) {
                                o.setName(p.getName());
                            }
                            o.setM(o.getM() + p.getM());
                            o.setN(o.getN() + p.getN());
                            o.setCount(o.getCount() == null ? 1 : o.getCount() + 1);
                        }, (o, p) -> o)
                ));
        System.out.println(collect);

        Map<String, Set<Integer>> collect1 = Stream.of(f1, f2, f3, f4, f5, f6, f7)
                .collect(Collectors.groupingBy(
                        Foo::getName,
                        Collectors.mapping(Foo::getN, Collectors.toSet())));
        System.out.println(collect1);
    }

    @Data
    static class Foo {
        // 分组属性
        private String name;
        // 需求：分组平均值
        private Integer m;
        // 需求：分组平均值
        private Integer n;
        // 非业务属性，计算平均值时使用
        private Integer count;

        public Foo(String name, Integer m, Integer n) {
            this.name = name;
            this.m = m;
            this.n = n;
        }
    }

    private static void sort() {
        List<Foo> list = new ArrayList<>();
        list.add(new Foo("first", 1, 4));
        list.add(new Foo("second", 2, null));
        list.add(new Foo("second", 1, null));
        list.add(new Foo("second", 2, 5));
//        list.sort(Comparator.comparing(Foo::getM));
//        list.sort(Comparator.nullsLast(Comparator.comparing(Foo::getN)));
        list = list.stream().sorted(Comparator.comparing(Foo::getM))
//                .sorted(Comparator.comparing((Foo item) -> Optional.of(item).map(Foo::getN).orElse(0)).reversed())
                .sorted(Comparator.comparing(
                        Foo::getN, Comparator.nullsLast((Comparator<Integer>)(Comparator.naturalOrder().reversed()))))
//                .sorted(Comparator.nullsLast(Comparator.comparing(Foo::getN)))
                .collect(Collectors.toList());
        System.out.println(list);
        System.out.println(list.stream()
                //先根据m倒序，如果m相同，n倒序。 null放在最后
                .sorted(Comparator.comparing(Foo::getM, Comparator.nullsLast((Comparator<Integer>)(Comparator.naturalOrder().reversed())))
                        .thenComparing(Foo::getN, Comparator.nullsLast((Comparator<Integer>)(Comparator.naturalOrder().reversed()))))
                .collect(Collectors.toList()));
    }

    private static void reduce() {
        List<Foo> list = new ArrayList<>();
        list.add(new Foo("first", 1, 4));
        list.add(new Foo("second", 2, 5));
        Set<Integer> set3 = list.stream().reduce(new HashSet<>(), (set, item) -> {
            set.add(item.getN());
            set.add(item.getM());
            return set;
        }, (set1, set2) -> {
            set1.addAll(set2);
            return set1;
        });
        System.out.println(set3);
    }

    public static void xixi() throws Exception {
        FileReader fr = new FileReader("aaa/1124/1.txt");
        FileWriter fw = new FileWriter("aaa/1124/3.txt");


        int a ;
        int read = fr.read();
        System.out.println(read);
        fw.write(read);
        fw.close();
        fr.close();
    }
}
