package com.demo.test.reflect;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectTest08 {
    public static void main(String[] args) {
        try {
            //获取类
            Class userServiceClass=Class.forName("com.demo.test.reflect.service.UserService");

            //获取所有的Method(包括私有的！）
            Method[] methods=userServiceClass.getDeclaredMethods();
            //System.out.println(methods.length);

            //遍历Method
            for(Method method:methods){
                //获取修饰符列表
                System.out.println(Modifier.toString(method.getModifiers()));
                //获取方法的返回值类型
                System.out.println(method.getReturnType().getSimpleName());
                //获取方法名
                System.out.println(method.getName());
                //参数列表
                Class[] parameterTypes=method.getParameterTypes();
                for(Class parameterType:parameterTypes){
                    System.out.println(parameterType.getSimpleName());
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


