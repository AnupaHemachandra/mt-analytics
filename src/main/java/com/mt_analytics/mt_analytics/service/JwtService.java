package com.mt_analytics.mt_analytics.service;

import com.mt_analytics.mt_analytics.entity.Authentication;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {
  // Replace this with a secure key in a real application, ideally fetched from environment
  // variables
  public static final String SECRET =
      "AbcdEfGhIjKlMnOpQrStUvWxYz1234567890ASDHNSHHahhdnshsjsjsjhdhjherhnsnshdmdhhksjnhdgdhjnbs";
  public static final long ACCESS_TOKEN_EXPIRATION = 600000;
  public static final long REFRESH_TOKEN_EXPIRATION = 600000 * 24;

  // Generate token with given user name
  public String generateAccessToken(Authentication authUser) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", authUser.getId());
    claims.put("user", authUser.getUsername());
    claims.put("type", authUser.getLoginType());
    return createToken(claims, authUser.getUsername(), ACCESS_TOKEN_EXPIRATION);
  }

  public String generateRefreshToken(Authentication authUser) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", authUser.getId());
    claims.put("user", authUser.getUsername());
    claims.put("type", authUser.getLoginType());
    return createToken(claims, authUser.getUsername(), REFRESH_TOKEN_EXPIRATION);
  }

  // Create a JWT token with specified claims and subject (user name)
  private String createToken(Map<String, Object> claims, String userName, long expiration) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userName)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(
            new Date(System.currentTimeMillis() + 1000 * expiration)) // Token valid for 30 minutes
        .signWith(getSignKey(), SignatureAlgorithm.HS512)
        .compact();
  }

  // Get the signing key for JWT token
  private SecretKey getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  // Extract the username from the token
  public String extractUsername(String token) {
    return extractClaim(token, Claims::getSubject);
  }

  // Extract the expiration date from the token
  public Date extractExpiration(String token) {
    return extractClaim(token, Claims::getExpiration);
  }

  // Extract a claim from the token
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  // Extract all claims from the token
  private Claims extractAllClaims(String token) {
    try {

        return Jwts.parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    } catch (Exception ex) {
      System.out.println(ex.getMessage());
      throw new RuntimeException("Error");
    }
  }

  // Check if the token is expired
  private Boolean isTokenExpired(String token) {
    return extractExpiration(token).before(new Date());
  }

  // Validate the token against user details and expiration
  public Boolean validateToken(String token, UserDetails userDetails) {
    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
