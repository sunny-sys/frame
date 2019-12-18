package io.smallbird.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.common.collect.Maps;

public class JwtHelper {

    private static final String SECRET = "yf_secret";

    private static final String ISSUER = "yf_gzh";

    //获取token的方法
    public static String genToken(Map<String, String> claims) {
        try {
            //使用该加密算法
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            //Builder是JWTCreator的静态内部类
            //{静态内部类只能访问外部类的静态变量和静态方法，Outer.Inner inner = new Outer.Inner()}
            JWTCreator.Builder builder = JWT.create()
                    .withIssuer(ISSUER) //设置发布者
                    .withExpiresAt(DateUtils.addDays(new Date(), 7));   //过期一天
            claims.forEach(builder::withClaim);  //将传入的claims设置到builder里面
            //claims.forEach( builder::withClaim);
            return builder.sign(algorithm).toString();  //使用上面的加密算法进行签名，返回String，就是token
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    //验证token方法
    public static Map<String, String> verifyToken(String token) {
        Algorithm algorithm = null;
        try {
            algorithm = Algorithm.HMAC256(SECRET);
        } catch (IllegalArgumentException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        JWTVerifier verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        DecodedJWT jwt = verifier.verify(token);
        Map<String, Claim> map = jwt.getClaims();
        Map<String, String> resultMap = Maps.newHashMap();
        map.forEach((k, v) -> resultMap.put(k, v.asString()));
        return resultMap;
    }

    public static void main(String[] args) {
        Map<String, String> claims = Maps.newHashMap();
        claims.put("phone", "18705549077");
        String token = genToken(claims);
        System.out.println(token);
    }

}