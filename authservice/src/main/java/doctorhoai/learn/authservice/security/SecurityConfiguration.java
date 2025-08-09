package doctorhoai.learn.authservice.security;

import doctorhoai.learn.authservice.config.filter.JwtRequestFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final JwtRequestFilter jwtRequestFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/document/**","/search/**", "/chat/**","/shipping_fee_config/**","/order/**","voucher/**","/payment/**","/source/**","/auth/**", "/healthcheck", "/user/add", "/food/**", "/size/**", "/category/**", "/upload","/cart/**", "/filter/**", "/food_size/**", "/ingredients/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/food-homepage/**", "/category-homepage/**").permitAll()
                        .requestMatchers("/ingredients_use/**", "/employee/**").hasAnyRole("ADMIN", "CHEF", "SHIPPER")
                        .requestMatchers("/food-homepage/**", "/category-homepage").hasAnyRole("ADMIN")
                        .requestMatchers("/point/**","/user/**", "/order/all", "/employee/**", "history_import_or_export/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling( exception -> exception
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                )
                .sessionManagement( sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
