package com.example.demo.entity;

import lombok.Data;

/**
 * <p> @Title SysRole
 * <p> @Description 用户角色
 *
 * @author ACGkaka
 * @date 2019/11/27 10:23
 */
@Data
public class SysRole {

    /** 主键 */
    private Long id;

    /** 角色名 */
    private String name;
}
