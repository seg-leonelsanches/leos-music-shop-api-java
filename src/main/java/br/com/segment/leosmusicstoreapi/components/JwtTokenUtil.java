package br.com.segment.leosmusicstoreapi.components;

import br.com.segment.leosmusicstoreapi.dtos.outputs.UserOutput;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtTokenUtil implements Serializable {

    @Serial
    private static final long serialVersionUID = -2550185165626007123L;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    @Value("${jwt.secret}")
    private String secret;

    //retrieve username from jwt token
    public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    //retrieve expiration date from jwt token
    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
    //for retrieving any information from token we will need the secret key
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    //check if the token has expired
    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    //generate token for user
    public String generateToken(UserOutput userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return doGenerateToken(claims, userDetails.getUsername());
    }

    /**
     * Effectively generates the JWT token.
     * @param claims key-value pairs that should be mentioned in the JWT Token payload.
     * @param subject The user to whom the generated JWT token belongs.
     * @return The JWT Token as a string.
     * @implNote while creating the token -
     *      1. Define  claims of the token, like Issuer, Expiration, Subject, and the ID
     *      2. Sign the JWT using the HS512 algorithm and secret key.
     *      3. According to JWS Compact Serialization(https://tools.ietf.org/html/draft-ietf-jose-json-web-signature-41#section-3.1)
     *          compaction of the JWT to a URL-safe string
     */
    private String doGenerateToken(Map<String, Object> claims, String subject) {

        return Jwts.builder()
                .setAudience("leos-music-shop-api")
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Token validation against the found user.
     * @param token The JWT Token
     * @param userDetails The found user by the JWT Token subject.
     * @return
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equalsIgnoreCase(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
