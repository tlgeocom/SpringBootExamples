package com.example.demo.service;

import com.example.demo.dao.SysUserMapper;
import com.example.demo.entity.SysUser;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p> @Title SysUserService
 * <p> @Description 系统用户Service
 *
 * @author ACGkaka
 * @date 2019/11/23 10:53
 */
@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;

    public SysUser selectByName(String username) {
        return sysUserMapper.findByName(username);
    }
}
