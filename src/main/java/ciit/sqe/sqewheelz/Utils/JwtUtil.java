package ciit.sqe.sqewheelz.Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Date;

@Component
public class JwtUtil {

    private static final String SECRET_KEY = "stringSecretKey123";

    //Generate token
    public String generateToken(String email, String role) {
        return Jwts.builder()
                .setSubject(email)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    //Validate the token
    public Boolean verifyToken(String token, String email) {
        return (extractEmail(token).equals(email) && !isTokenExpired(token));
    }

    //Extract email from token
    public String extractEmail(String token) {
        return extractAllClaims(token).getSubject();
    }

    //Extract role from token
    public String extractRole(String token) {
        return (String) extractAllClaims(token).get("role");
    }

    //Check if token is expired
    private Boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }

    //Extract all claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }


}