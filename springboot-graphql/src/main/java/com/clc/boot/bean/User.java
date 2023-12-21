package com.clc.boot.bean;

import lombok.Data;

/**
 * ClassName: User<br/>
 * Description: <br/>
 * date: 2019/6/28 10:38 AM<br/>
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Data
public class User {
    private int age;
    private long id;
    private String name;
    private Card card;

    public User(int age, long id, String name, Card card) {
        this.age = age;
        this.id = id;
        this.name = name;
        this.card = card;
    }

    public User(int age, long id, String name) {
        this.age = age;
        this.id = id;
        this.name = name;
    }
}

