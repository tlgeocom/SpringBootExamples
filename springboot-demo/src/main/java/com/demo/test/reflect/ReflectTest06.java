package com.demo.test.reflect;

import java.lang.reflect.Field;

public class ReflectTest06 {
    public static void main(String[] args) {
        //使用反射机制，访问一个对象的属性
        try {
            Class studentClass=Class.forName("com.demo.test.reflect.bean.Student");
            Object obj=studentClass.newInstance();

            //获取no属性
            Field noField=studentClass.getDeclaredField("no");
            noField.set(obj,111);
            System.out.println(noField.get(obj));

            //可以访问私有的属性吗？ 可以
            Field nameField=studentClass.getDeclaredField("name");

            //打破封装（打破封装，可能会给不法分子机会）
            //这样设置完之后，在外部也是可以访问private
            nameField.setAccessible(true);

            nameField.set(obj,"zhangsan");
            System.out.println(nameField.get(obj));

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}


