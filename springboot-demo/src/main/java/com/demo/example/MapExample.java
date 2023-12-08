package com.demo.example;

import java.util.LinkedList;
import java.util.List;

/**
 * <p> @Title MapExample
 * <p> @Description 手撕 HashMap
 *
 * @author ACGkaka
 * @date 2023/4/21 17:54
 */
public class MapExample {

    public static void main(String[] args) {
        List<String> list = new LinkedList<>();
        list.add(null);
        list.add(null);
        list.add("3");
        System.out.println(list);
        System.out.println(list.get(0));
        System.out.println(list.remove(1));
        System.out.println(list.size());
    }
}
