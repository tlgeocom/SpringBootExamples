package com.example.demo.dao;

import com.example.demo.entity.SysUserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p> @Title SysUserRoleMapper
 * <p> @Description 系统用户角色Mapper
 *
 * @author ACGkaka
 * @date 2019/11/27 14:28
 */
@Mapper
public interface SysUserRoleMapper {

    /**
     * 根据用户ID查找用户角色
     *
     * @param userId 用户ID
     * @return 用户角色
     */
    @Select("select * from sys_user_role where user_id = #{userId}")
    List<SysUserRole> findByUserId(Long userId);
}
