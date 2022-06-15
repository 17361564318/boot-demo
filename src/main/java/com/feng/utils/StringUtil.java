package com.feng.utils;

/**
 * 字符串工具类
 *
 * @author fhn
 * @create 2021/7/13
 * @software idea
 */
public class StringUtil {

    /**
     * 判断字符串是否是null或者是空字符串
     *
     * @param string 传入的字符串
     * @return boolean类型结果
     */
    public static boolean isNullOrBlank(final Object string) {
        return string == null || ("".equals(string));
    }

}
