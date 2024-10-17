package com.demo.example;

import java.util.concurrent.Callable;

public class TestThread {
    public static void main(String[] args) {
        new Thread(new Test()).start();
    }

}
