package org.example.config.jwt;

import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtConfig {

    @Bean
    public String jwtSecret(@Value("${jwt.secret.key}") String jwtSecret) {
        return jwtSecret;
    }

    @Bean
    public JwtBuilder jwtBuilder(Key secretKey) {
        return Jwts.builder().signWith(secretKey);
    }

    @Bean
    public JwtParser jwtParser(Key secretKey) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build();
    }

    @Bean
    public SignatureAlgorithm signatureAlgorithm() {
        return SignatureAlgorithm.HS512;
    }

    @Bean
    public Key secretKey(String jwtSecret, SignatureAlgorithm signatureAlgorithm) {
        return new SecretKeySpec(jwtSecret.getBytes(StandardCharsets.UTF_8), signatureAlgorithm.getJcaName());
    }

    @Bean
    public NimbusJwtDecoder nimbusJwtDecoder(Key secretKey) {
        return NimbusJwtDecoder.withSecretKey((SecretKey) secretKey).macAlgorithm(MacAlgorithm.HS512).build();
    }
}