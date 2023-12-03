package com.example.patientservice.security;

import com.example.patientservice.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JWTService {
    public static final String SECRET_KEY = "503E635266556A586E3272357738782F413F4428472B4B6250645367566B5991";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public Collection<? extends GrantedAuthority> extractAuthoritiesFromToken(String token) {
        Claims claims = extractAllClaims(token);

        List<String> roles = claims.get("roles", List.class);

        if (roles == null || roles.isEmpty()) {
            return Collections.emptyList();
        }

        return roles.stream().map(role -> new SimpleGrantedAuthority(role)).collect(Collectors.toList());
    }

    public boolean isTokenValid(String token) {
        final String userName = extractUsername(token);

        if (userName == null || isTokenExpired(token)) {
            throw new CustomException(new Date(), "Invalid  token", HttpStatus.UNAUTHORIZED);
        }
        return true;
    }

    private boolean isTokenExpired(String token) {
        if (extractExpiration(token).before(new Date())) {
            throw new CustomException(new Date(), "Token has expired", HttpStatus.UNAUTHORIZED);
        }
        return false;
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSignInKey()).build().parseClaimsJws(token).getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
