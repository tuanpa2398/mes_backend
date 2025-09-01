package vn.mes.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import vn.mes.model.user.User;

import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {
	private final long EXPIRATION = 1000 * 60 * 60 * 24; // 1 day
	private final long EXPIRATION_REFRESH_TOKEN = 1000 * 60 * 60 * 24 * 30; // 30 days
	private final String SECRET = "cc7564b60a812477f6956491545502b3981f204bf36ae12cc839a1cfdafb9596";
	
	private final Key key = Keys.hmacShaKeyFor(SECRET.getBytes());
	
	private String CLAIM_USERNAME = "username";
	private String CLAIM_TYPE = "type";

    public String generateToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                   .setSubject(Long.toString(user.getId()))
                   .claim(CLAIM_USERNAME, user.getUsername())
                   .claim(CLAIM_TYPE, "access")
                   .setIssuedAt(now)
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                   .signWith(key, SignatureAlgorithm.HS256)
                   .compact();
    }
    
    // Tạo Refresh Token
    public String generateRefreshToken(User user) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(Long.toString(user.getId()))
                .claim(CLAIM_USERNAME, user.getUsername())
                .claim(CLAIM_TYPE, "refresh")
                .setIssuedAt(now)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_REFRESH_TOKEN))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }
    
    public Long extractUserId(String token) {
        var id = this.getClaims(token).getSubject();
        return Long.valueOf(id);
    }
    
    public String extractUsername(String token) {
    	Claims claims = this.getClaims(token);
        return claims.get(CLAIM_USERNAME).toString();
    }
    
    public void validateToken(String token) {
    	Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }
    
    // Kiểm tra loại token (access/refresh)
    public String extractTokenType(String token) {
        return getClaims(token).get(CLAIM_TYPE, String.class);
    }
    
    // Lấy toàn bộ claims
    private Claims getClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody();
    }
}
