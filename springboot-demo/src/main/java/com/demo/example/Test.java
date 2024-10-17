package com.demo.example;

import java.util.concurrent.Callable;

public class Test implements Runnable {
    public void run(){
        int i = 0;
        while(true){
            i++;
            try{
                Thread.sleep(1000);
                System.out.println("i:"+i);
            }catch(Exception e){

            }

            if(i == 8){
                break;
            }
            if(i == 10){
                //break;

            }
        }
    }
}