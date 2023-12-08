package com.example.demo.dao;

import com.example.demo.entity.SysRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * <p> @Title SysUserRoleMapper
 * <p> @Description 系统角色Mapper
 *
 * @author ACGkaka
 * @date 2019/11/27 14:28
 */
@Mapper
public interface SysRoleMapper {

    /**
     * 根据角色ID查找角色
     *
     * @param id 角色ID
     * @return 角色
     */
    @Select("select * from sys_role where id = #{id}")
    SysRole findById(Long id);
}
