package com.demo.example;

import java.util.Random;
import java.util.concurrent.Callable;

public class CallableClass implements Callable<Integer> {
    @Override
    public Integer call(){
        int i = 0;
        while(true){
            i++;

            try{
                System.out.println("i:"+i);
                Thread.sleep(1000);
            }catch (Exception e){

            }
            if(i == 8){
                int random = new Random().nextInt(100); // 获取100以内的随机整数
                // 以下打印随机数日志，包括当前时间、当前线程、随机数值等信息
                System.out.println(Thread.currentThread().getName()+"任务生成的随机数="+random);
                return random;
            }
        }

    }
}
