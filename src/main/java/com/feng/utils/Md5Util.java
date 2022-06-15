package com.feng.utils;

import java.security.MessageDigest;

/**
 * md5加密工具类
 *
 * @author fhn
 * @create 2021/7/13
 * @software idea
 */
public class Md5Util {

    public static String stringMD5(String origin) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] bytes = origin.getBytes();
            md5.update(bytes);
            byte[] digest = md5.digest();
            return byteArrayToHex(digest);
        } catch (Exception e) {
            return "";
        }
    }

    private static String byteArrayToHex(byte[] array) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] resultCharArray = new char[array.length * 2];
        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去
        int index = 0;
        for (byte b : array) {
            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];
            resultCharArray[index++] = hexDigits[b & 0xf];
        }
        return new String(resultCharArray);
    }
}
