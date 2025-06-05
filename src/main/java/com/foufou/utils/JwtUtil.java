package com.foufou.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JwtUtil {
    /**
     * 生成jwt
     * 使用Hs256算法, 私匙使用固定秘钥
     *
     * @param secretKey jwt秘钥
     * @param ttlMillis jwt过期时间(毫秒)
     * @param claims    设置的信息
     * @return
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String, Object> claims) {

        // 0.9.1

//        // 指定签名的时候使用的签名算法，也就是header那部分
//        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
//
//        // 生成JWT的时间
//        long expMillis = System.currentTimeMillis() + ttlMillis;
//        Date exp = new Date(expMillis);
//
//        // 设置jwt的body
//        JwtBuilder builder = Jwts.builder()
//                // 如果有私有声明，一定要先设置这个自己创建的私有的声明，这个是给builder的claim赋值，一旦写在标准的声明赋值之后，就是覆盖了那些标准的声明的
//                .setClaims(claims)
//                // 设置签名使用的签名算法和签名使用的秘钥
//                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
//                // 设置过期时间
//                .setExpiration(exp);
//
//        return builder.compact();

        // 0.12.3
        // 1. 将字符串密钥转换为安全的 SecretKey（要求密钥长度 >= 32字节）
        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // 2. 计算过期时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        // 3. 构建 JWT
        return Jwts.builder()
                .claims(claims)       // 设置自定义声明（Claims）
                .expiration(exp)      // 设置过期时间
                .signWith(key)        // 使用安全的 SecretKey 签名
                .compact();
    }

    /**
     * Token解密
     *
     * @param secretKey jwt秘钥 此秘钥一定要保留好在服务端, 不能暴露出去, 否则sign就可以被伪造, 如果对接多个客户端建议改造成多个
     * @param token     加密后的token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token) {
//        // 得到DefaultJwtParser
//        Claims claims = Jwts.parser()
//                // 设置签名的秘钥
//                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
//                // 设置需要解析的jwt
//                .parseClaimsJws(token).getBody();

        SecretKey key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
        Jws<Claims> jws = Jwts.parser()
                .verifyWith(key)          // 设置验证密钥
                .build()                  // 构建解析器
                .parseSignedClaims(token); // 解析并验证签名

        // 3. 获取 Claims（载荷内容）
        return jws.getPayload();
    }

}
