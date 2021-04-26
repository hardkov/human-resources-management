package com.agh.hr.security;

import com.agh.hr.persistence.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;

import java.util.Arrays;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class JwtTokenProvider {

    private final static String AUTHORITIES_CLAIM = "authorities";

    private final static String USERNAME_CLAIM = "username";

    private final static String USER_ID_CLAIM = "user_id";

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.issuer}")
    private String jwtIssuer;

    private final Logger logger;

    @Autowired
    public JwtTokenProvider(Logger logger) {
        this.logger = logger;
    }

    public String generateAccessToken(User user) {
        val authorities = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim(USERNAME_CLAIM, user.getUsername())
                .claim(USER_ID_CLAIM, user.getId().toString())
                .claim(AUTHORITIES_CLAIM, authorities)
                .setIssuer(jwtIssuer)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000)) // 1 week
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public String getUserId(String token) {
        return getClaims(token).get(USER_ID_CLAIM, String.class);
    }

    public String getUsername(String token) {
        return getClaims(token).get(USERNAME_CLAIM, String.class);
    }

    public Date getExpirationDate(String token) {
        return getClaims(token).getExpiration();
    }

    public Set<String> getAuthorities(String token) {
        val authorities = getClaims(token).get(AUTHORITIES_CLAIM).toString().split(",");
        return Arrays.stream(authorities)
                .collect(Collectors.toSet());
    }

    public boolean validate(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);

            return true;
        } catch (SignatureException ex) {
            logger.error("Invalid JWT signature - {}", ex.getMessage());
        } catch (MalformedJwtException ex) {
            logger.error("Invalid JWT token - {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            logger.error("Expired JWT token - {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            logger.error("Unsupported JWT token - {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            logger.error("JWT claims string is empty - {}", ex.getMessage());
        }

        return false;
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
    }

}
