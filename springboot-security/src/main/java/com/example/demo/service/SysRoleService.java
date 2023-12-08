package com.example.demo.service;

import com.example.demo.dao.SysRoleMapper;
import com.example.demo.entity.SysRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> @Title SysRoleService
 * <p> @Description 系统角色Service
 *
 * @author ACGkaka
 * @date 2019/11/27 14:34
 */
@Service
public class SysRoleService {

    @Resource
    private SysRoleMapper roleMapper;

    public SysRole selectById(Long id){
        return roleMapper.findById(id);
    }
}
