package dev.megashopper.common.dtos;

import dev.megashopper.common.utils.exceptions.MissingAuthTokenException;
import dev.megashopper.common.utils.exceptions.TokenParseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;


import java.util.Date;


@AllArgsConstructor
@Component
public class TokenService {
    private final JwtConfig jwtConfig;

    public String generateToken(Principal subject) {
        long now = System.currentTimeMillis();

        return Jwts.builder()
                   .setId(subject.getAuthUserId())
                   .setIssuer("megashopper")
                   .claim("email", subject.getAuthEmail())
                   .setIssuedAt(new Date(now))
                   .setExpiration(new Date(now + jwtConfig.getExpiration()))
                   .signWith(jwtConfig.getSigAlg(), jwtConfig.getSigningKey()).compact();
    }
    public Principal extractTokenDetails(String token) {

        if (token == null || token.isEmpty()) {
            throw new MissingAuthTokenException();
        }

        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(jwtConfig.getSigningKey())
                    .parseClaimsJws(token)
                    .getBody();

            return new Principal(claims.getId(), claims.get("email", String.class));
        } catch (ExpiredJwtException e) {
            throw new TokenParseException("The provided token is expired", e);
        } catch (Exception e) {
            throw new TokenParseException(e);
        }
    }
}
