package org.example.config.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {

    @Bean
    public String secretKey(@Value("${jwt.secret.key}") String secretKey) {
        return secretKey;
    }

    @Bean
    public JwtBuilder jwtBuilder(String secretKey, SignatureAlgorithm signatureAlgorithm) {
        return Jwts.builder().signWith(signatureAlgorithm, secretKey);
    }

    @Bean
    public JwtParser jwtParser(String secretKey) {
        return Jwts.parser().setSigningKey(secretKey);
    }

    @Bean
    public SignatureAlgorithm signatureAlgorithm() {
        return SignatureAlgorithm.HS256;
    }
}
