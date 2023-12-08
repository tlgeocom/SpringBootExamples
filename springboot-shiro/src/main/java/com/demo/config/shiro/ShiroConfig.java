package com.demo.config.shiro;

import com.demo.config.shiro.realm.CustomRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * <p> @Title ShiroConfig
 * <p> @Description Shiro配置类
 *
 * @author ACGkaka
 * @date 2023/10/15 12:44
 */
@Configuration
public class ShiroConfig {

    /**
     * ShiroFilter过滤所有请求
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 给ShiroFilter配置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        // 配置系统公共资源、系统受限资源（公共资源必须在受限资源上面，不然会造成死循环）
        Map<String, String> map = new HashMap<>();
        // 系统公共资源
        map.put("/login", "anon");
        map.put("/register", "anon");
        map.put("/static/**", "anon");
        // 受限资源
        map.put("/**", "authc");

        // 设置认证界面路径
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);

        return shiroFilterFactoryBean;
    }

    /**
     * 创建安全管理器
     */
    @Bean
    public DefaultWebSecurityManager securityManager(Realm realm) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(realm);
        return securityManager;
    }

    /**
     * 创建自定义Realm
     */
    @Bean
    public Realm realm() {
        CustomRealm realm = new CustomRealm();
        // 设置使用哈希凭证匹配
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        // 设置使用MD5加密算法
        credentialsMatcher.setHashAlgorithmName("MD5");
        // 设置散列次数：加密次数
        credentialsMatcher.setHashIterations(1024);
        realm.setCredentialsMatcher(credentialsMatcher);
        return realm;
    }
}
