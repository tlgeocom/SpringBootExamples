package com.example.demo.entity;

import lombok.Data;

/**
 * <p> @Title SysUserRole
 * <p> @Description 用户角色关系表
 *
 * @author ACGkaka
 * @date 2019/11/27 10:25
 */
@Data
public class SysUserRole {

    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;
}
