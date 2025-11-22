package de.htw_berlin.studymatch.backend.security;


import de.htw_berlin.studymatch.backend.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Date;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtService {

//    private final JwtProperties jwtProperties;
//
//
//    public String generateToken(UserDetails userDetails){
//        return Jwts.builder()
//                .subject(userDetails.getUsername())
//                .claim("roles", userDetails.getAuthorities())
//                .issuedAt(new Date())
//                .expiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
//                .signWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret())))
//                .compact();
//    }
//
//    public String extractUsername(String token){
//        return extractClaim(token, Claims::getSubject);
//    }
//
//    public boolean isTokenValid(String token, UserDetails userDetails){
//        final String username =extractUsername(token);
//        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
//    }
//
//    private boolean isTokenExpired(String token) {
//        return extractExpiration(token).before(new Date());
//    }
//    private Date extractExpiration(String token) {
//        return extractClaim(token, Claims::getExpiration);
//    }
//
//    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//        final Claims claims = Jwts.parser()
//                .verifyWith(Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getSecret())))
//                .build()
//                .parseSignedClaims(token)
//                .getPayload();
//        return claimsResolver.apply(claims);
//    }
}