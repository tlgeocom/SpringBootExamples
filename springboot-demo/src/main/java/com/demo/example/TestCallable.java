package com.demo.example;

import java.util.Random;
import java.util.concurrent.Callable;

public class TestCallable {
    public static void main(String[] args) {

        Callable<Integer> callable = new CallableClass();
        try {
            System.out.println("callable:"+callable.call());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
