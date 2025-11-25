package com.pots.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.pots.common.constant.MessageConst;
import com.pots.common.constant.PropConst;
import com.pots.common.model.TokenStatus;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Slf4j
@Component
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtUtils {
    
    private String      secret;
    private long        expireTime;
    private Algorithm   algorithm;
    private JWTVerifier verifier;
    
    @PostConstruct
    public void init() {
        this.algorithm = Algorithm.HMAC256(secret);
        this.verifier = JWT.require(algorithm).build();
    }
    
    public String generateToken(Long userId,String userName) {
        return JWT.create()
                .withClaim(PropConst.REQUEST_ATTRIBUTE_ID,userId)
                .withClaim(PropConst.REQUEST_ATTRIBUTE_NAME,userName)
                .withExpiresAt(new Date(System.currentTimeMillis() + expireTime))
                .sign(algorithm);
    }
    
    // tokenの解析と検証
    public TokenStatus verifyToken(String token) {
        try {
            verifier.verify(token);
            return TokenStatus.VALID;
        } catch (TokenExpiredException e) {
            return TokenStatus.EXPIRED;
        } catch (JWTVerificationException e) {
            log.warn(MessageConst.INVALID_TOKEN);
            return TokenStatus.INVALID;
        }
    }
    
    // token から user idを取得
    public Long getUserIdFromToken(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(PropConst.REQUEST_ATTRIBUTE_ID).asLong();
        } catch (Exception e) {
            return null;
        }
    }
    
    // token から user idを取得
    public String getUserNameFromToken(String token) {
        try {
            DecodedJWT jwt = verifier.verify(token);
            return jwt.getClaim(PropConst.REQUEST_ATTRIBUTE_NAME).asString();
        } catch (Exception e) {
            return null;
        }
    }

    
}
