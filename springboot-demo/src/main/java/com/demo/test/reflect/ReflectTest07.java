package com.demo.test.reflect;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

//完成任意一个类属性的反编译
public class ReflectTest07 {
    public static void main(String[] args) {
        try {
            //创建这个是为了拼接字符串
            StringBuilder s=new StringBuilder();

            Class dateClass=Class.forName("java.util.Date");
            s.append(Modifier.toString(dateClass.getModifiers())+" class "+dateClass.getSimpleName()+" {\n");

            Field[] fields=dateClass.getDeclaredFields();
            for(Field field:fields){
                s.append("\t");
                s.append(Modifier.toString(field.getModifiers()));
                s.append(" ");
                s.append(field.getType().getSimpleName());
                s.append(" ");
                s.append(field.getName());
                s.append(";\n");
            }

            s.append("}");
            System.out.println(s);

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}


