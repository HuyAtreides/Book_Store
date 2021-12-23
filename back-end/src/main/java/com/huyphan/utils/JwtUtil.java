package com.huyphan.utils;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.huyphan.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/** Provides methods for working with JSON Web Token. */
@Component
public class JwtUtil {

	/** JWT secret key. */
	@Value("${app.jwt.secret}")
	private String secret;

	/** JWT expiration date. */
	@Value("${app.jwt.expiration-date}")
	private int expirationDate;

	/** Create signing key from secret key string. */
	private Key getSigningKey() {
		byte[] keyBytes = secret.getBytes();
		return Keys.hmacShaKeyFor(keyBytes);
	}

	/**
	 * Generate JWT
	 * 
	 * @param user. User info.
	 */
	public String generateToken(User user) {
		String username = user.getUsername();
		Calendar calendar = Calendar.getInstance();
		Date issueDate = new Date();
		calendar.setTime(issueDate);
		calendar.add(Calendar.DATE, expirationDate);
		Date expiredDate = calendar.getTime();
		Key key = getSigningKey();
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(issueDate)
				.setExpiration(expiredDate)
				.signWith(key)
				.compact();
	}

	/** Validate token. */
	public boolean validateToken(String token) {
		try {
			parseToken(token);
			return true;
		} catch (JwtException jwtException) {
			return false;
		}
	}

	/** Get subject from token. */
	public String getTokenSubjet(String token) {
		Claims claims = parseToken(token);
		return claims.getSubject();
	}

	/**
	 * Parse token.
	 * 
	 * @param token. Token to parse.
	 */
	private Claims parseToken(String token) {
		try {
			Key signingKey = getSigningKey();
			return Jwts.parserBuilder()
					.setSigningKey(signingKey)
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (JwtException jwtException) {
			throw jwtException;
		}
	}

	/**
	 * Parse expired token. Accept expired jwt exception.
	 * 
	 * @param token. Token to parse.
	 */
	public Claims parseExpiredToken(String token) {
		try {
			return parseToken(token);
		} catch (ExpiredJwtException jwtException) {
			return jwtException.getClaims();
		}
	}
}
