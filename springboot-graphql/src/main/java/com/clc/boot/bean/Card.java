package com.clc.boot.bean;

import lombok.Data;

/**
 * ClassName: Card<br/>
 * Description: <br/>
 * date: 2019/6/28 3:25 PM<br/>
 *
 * @author chengluchao
 * @since JDK 1.8
 */
@Data
public class Card {
    private String cardNumber;
    private Long userId;

    public Card(String cardNumber, Long userId) {
        this.cardNumber = cardNumber;
        this.userId = userId;
    }
}
