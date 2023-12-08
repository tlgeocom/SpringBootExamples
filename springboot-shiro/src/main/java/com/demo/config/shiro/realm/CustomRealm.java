package com.demo.config.shiro.realm;

import com.demo.module.entity.User;
import com.demo.module.service.UserService;
import com.demo.util.SpringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

/**
 * <p> @Title CustomRealm
 * <p> @Description 自定义Realm
 *
 * @author ACGkaka
 * @date 2023/10/15 12:42
 */
public class CustomRealm extends AuthorizingRealm {

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        // 获取用户名
        String principal = (String) authenticationToken.getPrincipal();
        // 根据用户名查询数据库
        UserService userService = SpringUtils.getBean(UserService.class);
        User user = userService.findByUsername(principal);
        if (user != null) {
            return new SimpleAuthenticationInfo(user.getUsername(), user.getPassword(),
                    ByteSource.Util.bytes(user.getSalt()), this.getName());
        }
        return null;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 获取用户名
        String principal = (String) principalCollection.getPrimaryPrincipal();
        if ("admin".equals(principal)) {
            // 管理员拥有所有权限
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            info.addRole("admin");
            info.addStringPermission("admin:*");
            info.addRole("user");
            info.addStringPermission("user:find:*");
            return info;
        }
        return null;
    }
}
