package com.demo.test.reflect;

public class ReflectTest13 {
    public static void main(String[] args) throws Exception {
        Class stringClass=Class.forName("java.lang.String");

        //获取String的父类
        Class superClass=stringClass.getSuperclass();
        System.out.println(superClass.getName());

        //获取String类所有实现的接口
        Class[] interfaces=stringClass.getInterfaces();
        for(Class in:interfaces){
            System.out.println(in.getName());
        }
    }
}


