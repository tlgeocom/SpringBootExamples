package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * <p> @Title SysUser
 * <p> @Description 系统用户
 *
 * @author ACGkaka
 * @date 2019/11/23 10:49
 */
@Data
public class SysUser implements Serializable {

    /** 主键 */
    private Long id;

    /** 用户名. */
    private String username;

    /** 密码. */
    private String password;
}