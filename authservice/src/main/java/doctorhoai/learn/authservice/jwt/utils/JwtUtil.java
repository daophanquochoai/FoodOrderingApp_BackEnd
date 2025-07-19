package doctorhoai.learn.authservice.jwt.utils;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.function.Function;

public interface JwtUtil {
    String extractUsername( final String token );
    Date extractExpiration(final String token );
    <T> T extractClaims( final String token, final Function<Claims, T> claims );
    String generateToken( final UserDetails userDetails, Long time);
    Boolean validateToken( final String token, final UserDetails userDetails );
}
