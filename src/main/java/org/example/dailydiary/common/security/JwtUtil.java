package org.example.dailydiary.common.security;

import java.util.Date;

import org.example.dailydiary.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

	@Value("${jwt.secret.key}")
	private String secretKey;

	private static final long ACCESS_EXPIRATION_TIME = 1000 * 60 * 30;
	private static final long REFRESH_EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;

	public String generateAccessToken(Long userId, UserRole role) {
		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("role", role)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + ACCESS_EXPIRATION_TIME))
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
			.compact();
	}

	public String generateRefreshToken(Long userId) {
		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("type", "refresh")
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRATION_TIME))
			.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()), SignatureAlgorithm.HS256)
			.compact();
	}

	public Claims validToken(String token) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.build()
				.parseClaimsJws(token)
				.getBody();
		} catch (JwtException e) {
			return null;
		}
	}
}
