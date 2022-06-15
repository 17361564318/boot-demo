package com.feng.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * json web token 的工具类
 * jwt由header头部、payload载荷、signature签名三部分組成
 *
 * @author fhn
 * @create 2021/7/26
 * @software idea
 */
public class JWTUtil {

    /**
     * 请求头里面token的key值
     */
    public static final String TOKEN_HEADER = "token";
    /**
     * 密钥key
     */
    private static final String SECRET = "jwtSECRET";
    /**
     * jwt发行人
     */
    private static final String ISS = "fenghn";
    /**
     * 过期时间1800秒
     */
    private static final long EXPIRATION = 60 * 60 * 1000;

    /**
     * 生成token
     *
     * @param username 用户名
     * @return 返回token字符串
     */
    public static String createToken(String username) {
        return Jwts.builder().signWith(SignatureAlgorithm.HS512, SECRET) // 加密算法
                .setIssuer(ISS) // 发行人
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .compact();
    }

    /**
     * 判断token是否合法，主要包括是否过期或者用户名是否为空的判断
     *
     * @param token 用戶携带的token
     * @return 正确性
     */
    public static boolean isValid(final String token, final String username) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
        Date expiration = claims.getExpiration();
        String subject = claims.getSubject();
        return username.equals(subject) && !expiration.before(new Date());
    }
}
