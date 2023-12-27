package com.demo.test.reflect;

import java.lang.reflect.Constructor;

//使用反射机制调用构造方法
public class ReflectTest11 {
    public static void main(String[] args) throws Exception {
        //使用反射机制创建对象
        Class vipClass=Class.forName("com.demo.test.reflect.service.Vip");

        //调用无参数构造方法
        Object obj=vipClass.newInstance();
        System.out.println(obj);

        //调用有参数构造方法
        //第一步：先获取这个有参数构造方法
        Constructor con=vipClass.getDeclaredConstructor(int.class,String.class,String.class,char.class);
        //第二步：调用构造方法new对象
        Object newObj=con.newInstance(110,"Tom","2020-1-1",'男');
        System.out.println(newObj);

        //获取无参数构造方法
        Constructor con2=vipClass.getDeclaredConstructor();
        Object obj2=con2.newInstance();
        System.out.println(obj2);
    }
}


