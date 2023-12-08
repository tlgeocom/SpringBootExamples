package com.example.demo.service;

import com.example.demo.dao.SysUserRoleMapper;
import com.example.demo.entity.SysUserRole;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p> @Title SysUserRoleService
 * <p> @Description 系统用户角色Service
 *
 * @author ACGkaka
 * @date 2019/11/27 14:35
 */
@Service
public class SysUserRoleService {

    @Resource
    private SysUserRoleMapper userRoleMapper;

    public List<SysUserRole> listByUserId(Long userId) {
        return userRoleMapper.findByUserId(userId);
    }
}
