package com.fmsUser.JwtUtil;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.fmsUser.exception.JwtTokenMalformedException;
import com.fmsUser.exception.JwtTokenMissingException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

public class JwtUtil {
	static String jwtSecret = "itsaSecret46";

	public static Claims getClaims(final String token) {
		try {
			return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			throw new JwtException("Invalid token");
		}

	}

	public static String generateToken(String id) {

		Claims claims = Jwts.claims().setSubject(id);

		long nowMillis = System.currentTimeMillis();
		long expMillis = 60 * 60 * 1000;
		Date exp = new Date(System.currentTimeMillis() + expMillis);
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(nowMillis)).setExpiration(exp)
				.signWith(SignatureAlgorithm.HS512, jwtSecret).compact();
	}

	public static void validateToken(HttpServletRequest request)
			throws JwtTokenMalformedException, JwtTokenMissingException {
		String token = request.getHeader("Authorization");
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
		} catch (SignatureException ex) {
			throw new JwtTokenMalformedException("Invalid JWT signature");
		} catch (MalformedJwtException ex) {
			throw new JwtTokenMalformedException("Invalid JWT token");
		} catch (UnsupportedJwtException ex) {
			throw new JwtTokenMalformedException("Unsupported JWT token");
		} catch (IllegalArgumentException ex) {
			throw new JwtTokenMissingException("JWT claims string is empty.");
		}
	}
}
