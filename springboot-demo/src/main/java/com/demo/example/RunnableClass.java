package com.demo.example;

import java.util.concurrent.Callable;

public class RunnableClass implements Runnable {
    @Override
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