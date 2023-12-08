package com.example.demo.dao;

import com.example.demo.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p> @Title SysUserMapper
 * <p> @Description 系统用户Mapper
 *
 * @author ACGkaka
 * @date 2019/11/27 10:28
 */
@Mapper
public interface SysUserMapper {

    /**
     * 根据用户名查找用户
     *
     * @param username 用户名
     * @return 用户
     */
    @Select("select * from sys_user where username = #{username}")
    SysUser findByName(String username);
}
