package com.demo.util;

/**
 * <p> @Title SaltUtils
 * <p> @Description 盐值工具类
 *
 * @author ACGkaka
 * @date 2023/10/15 16:07
 */
public class SaltUtils {
    
    /**
     * 生成salt的静态方法
     */
    public static String getSalt(int n) {
        char[] chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()".toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < n; i++) {
            // 生成随机数
            char aChar = chars[(int) (Math.random() * chars.length)];
            sb.append(aChar);
        }
        return sb.toString();
    }
}
