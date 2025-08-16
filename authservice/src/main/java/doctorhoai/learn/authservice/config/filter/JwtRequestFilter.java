package doctorhoai.learn.authservice.config.filter;


import doctorhoai.learn.authservice.exception.UnAuthorizedException;
import doctorhoai.learn.authservice.jwt.service.jwt.JwtService;
import doctorhoai.learn.authservice.jwt.service.token.TokenService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    @Lazy
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final TokenService tokenService;

    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();

    private Bucket getBucketForIp(String ip) {
        return buckets.computeIfAbsent(ip, k ->
                Bucket4j.builder()
                        .addLimit(Bandwidth.classic(100000, Refill.intervally(100000, Duration.ofMinutes(1)))) //TODO : Chinh la
                        .build());
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String ip = request.getRemoteAddr(); // Lấy địa chỉ IP của client
        Bucket bucket = getBucketForIp(ip);

        if (!bucket.tryConsume(1)) {
            response.setStatus(429);
            response.getWriter().write("Too many requests - Rate limit exceeded");
            return; // Không tiếp tục xử lý request nếu bị chặn
        }
        log.info(request.getServletPath());
        if (request.getServletPath().contains("/api/authenticate") || request.getServletPath().contains("actuator")) {
            log.info("No secutiry path");
            filterChain.doFilter(request, response);
            return;
        }
        log.info("**JwtRequestFilter, one per request, validating and extracting token**");
        final var authorizationHeader = request.getHeader("Authorization");
        String username = null;
        String jwt = null;

        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtService.extractUsername(jwt);
        }
        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null ) {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            try{
                if(!tokenService.findToken(jwt)){
                    throw new UnAuthorizedException("Bad credentials");
                }
                if( this.jwtService.validateToken(jwt, userDetails) ) {
                    final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                            =new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }else {
                    throw new UnAuthorizedException("Bad credentials");
                }
            }catch (Exception e) {
                throw new UnAuthorizedException("Bad credentials");
            }
        }
        filterChain.doFilter(request, response);
        log.info("**JwtRequestFilter, one per request, validating and extracting token**");
    }
}

