package com.feng.utils;

/**
 * @author fhn
 * @create 2021/9/23
 * @software idea
 */
public class IpLongUtils {
    /**
     * 将ip标准格式转换为long类型
     *
     * @param ipString 原始ip
     * @return long类型的ip
     */
    public static long ip2Long(String ipString) {
        String[] ip = ipString.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }

    /**
     * 将long类型的ip转换为标准格式的ip
     *
     * @param ipLong long型ip
     * @return 标准格式ip
     */
    public static String long2Ip(long ipLong) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(ipLong >>> 24).append(".");
        buffer.append((ipLong >>> 16) & 0xFF).append(".");
        buffer.append((ipLong >>> 8) & 0xFF).append(".");
        buffer.append(ipLong & 0xFF);
        return buffer.toString();
    }
}
