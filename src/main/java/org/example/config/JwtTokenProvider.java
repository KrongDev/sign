package org.example.config;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.example.user.aggregate.UserInfo;
import org.example.user.command.CreateAuthInfo;
import org.example.user.service.AuthInfoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;


@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${spring.jwt.token.access-expiration-time}")
    private long accessExpiredTime;
    @Value("${spring.jwt.token.refresh-expiration-time}")
    private long refreshExpiredTime;

    private final AuthInfoService authInfoService;

    public JwtTokenProvider(AuthInfoService authInfoService) {
        //
        this.authInfoService = authInfoService;
    }

    public JwtToken generateToken(Authentication authentication, String deviceId, String connectId, UserInfo user) {
//        String authorities = authentication.getAuthorities().stream()
//                .map(GrantedAuthority::getAuthority)
//                .collect(Collectors.joining(","));

        String accessToken = createAccessToken(authentication, user);
        String refreshToken = createRefreshToken(authentication, user, deviceId, connectId);

        return JwtToken.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * accessToken 토큰 생성
     */
    public String createAccessToken(Authentication authentication, UserInfo user) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + accessExpiredTime);

        return Jwts.builder()
                .setClaims(claims)
                .claim("user", user)
                .setIssuedAt(now)
                .setExpiration(expireDate)
//                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    /**
     * Refresh 토큰 생성
     */
    public String createRefreshToken(Authentication authentication, UserInfo user, String deviceId, String connectId) {
        Claims claims = Jwts.claims().setSubject(authentication.getName());
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + refreshExpiredTime);

        String refreshToken = Jwts.builder()
                .setClaims(claims)
                .claim("user", user)
                .setIssuedAt(now)
                .setExpiration(expireDate)
//                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // database에 저장
        authInfoService.createAuthInfo(new CreateAuthInfo(deviceId, connectId, refreshToken, expireDate.getTime())) ;
        return refreshToken;
    }

    public Authentication getAuthentication(String accessToken) {
        Claims claims = parseClaims(accessToken);
        if (claims.get("auth") == null) {
            throw new RuntimeException("권한 정보가 없는 토큰입니다.");
        }

        Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        UserDetails principal = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().build().parseClaimsJws(token).getBody();
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("Invalid JWT Token", e);
        } catch (ExpiredJwtException e) {
            log.info("Expired JWT Token", e);
        } catch (UnsupportedJwtException e) {
            log.info("Unsupported JWT Token", e);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims string is empty", e);
        }
        return false;
    }

    public String resolveToken(HttpServletRequest request) {
        //
        return request.getHeader("Authorization");
    }

    private Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }
}
