package com.demo.module.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.demo.module.entity.User;
import com.demo.module.mapper.UserMapper;
import com.demo.module.service.UserService;
import com.demo.util.SaltUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ACGkaka
 * @since 2021-04-25
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void register(User user) {
        // 注册用户
        checkRegisterUser(user);

        // 1.生成随机盐
        String salt = SaltUtils.getSalt(8);
        // 2.将随机盐保存到数据库
        user.setSalt(salt);
        // 3.明文密码进行MD5 + salt + hash散列次数
        Md5Hash md5Hash = new Md5Hash(user.getPassword(), salt, 1024);
        user.setPassword(md5Hash.toHex());
        // 4.保存用户
        this.save(user);
    }

    @Override
    public User findByUsername(String principal) {
        // 根据用户名查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, principal);
        return this.getOne(queryWrapper);
    }


    // ------------------------------------------------------------------------------------------
    // 内部方法
    // ------------------------------------------------------------------------------------------

    /**
     * 校验注册用户
     * @param user 用户
     */
    private void checkRegisterUser(User user) {
        if (user == null) {
            throw new RuntimeException("用户信息不能为空");
        }
        if (user.getUsername() == null || "".equals(user.getUsername())) {
            throw new RuntimeException("用户名不能为空");
        }
        if (user.getPassword() == null || "".equals(user.getPassword())) {
            throw new RuntimeException("密码不能为空");
        }
        // 判断用户名是否已存在
        User existUser = this.getOne(new UpdateWrapper<User>().eq("username", user.getUsername()));
        if (existUser != null) {
            throw new RuntimeException("用户名已存在");
        }
    }
}
