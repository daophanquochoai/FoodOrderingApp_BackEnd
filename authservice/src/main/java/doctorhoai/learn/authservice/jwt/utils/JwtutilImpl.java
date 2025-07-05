package doctorhoai.learn.authservice.jwt.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import doctorhoai.learn.authservice.feign.userservice.EmployeeFeign;
import doctorhoai.learn.authservice.feign.userservice.UserFeign;
import doctorhoai.learn.authservice.feign.userservice.model.EmployeeDto;
import doctorhoai.learn.authservice.feign.userservice.model.Filter.FilterUser;
import doctorhoai.learn.authservice.feign.userservice.model.UserDto;
import doctorhoai.learn.basedomain.response.ResponseObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtutilImpl implements JwtUtil{
    private static final String SECRET = "HocVienCongNgheBuuChinhVienThongCoSoHoChiMinh";
    private final EmployeeFeign employmentFeign;
    private final UserFeign userFeign;

    @Override
    public String extractUsername(String token) {
        return this.extractClaims(token, Claims::getSubject);
    }

    @Override
    public Date extractExpiration(String token) {
        return this.extractClaims(token, Claims::getExpiration);
    }

    @Override
    public <T> T extractClaims(String token, Function<Claims, T> claimFunction) {
        final Claims claim = this.extractAllClaims(token);
        return  claimFunction.apply(claim);
    }

    public Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
    }

    @Override
    public String generateToken(UserDetails userDetails, Long time) {
        final Map<String, Object> claims = new HashMap<>();
        List<String> authorities = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
        ResponseEntity<ResponseObject> data;
        ObjectMapper objectMapper = new ObjectMapper()
                .registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
        if( !authorities.get(0).equals("USER")){
            data = employmentFeign.getEmployeeWithFilter(null, userDetails.getUsername(),null,null,null);
            if( data.getStatusCode() != HttpStatusCode.valueOf(200)){
                log.error("Can't get info account");
                throw new RuntimeException("Can't get info account");
            }
            EmployeeDto employeeDto = objectMapper.convertValue(Objects.requireNonNull(data.getBody()).getData(),EmployeeDto.class);
            claims.put("name", employeeDto.getName());
            claims.put("email", employeeDto.getEmail());
            claims.put("id", employeeDto.getId());
            claims.put("cccd", employeeDto.getCccd());
        }else{
            data = userFeign.getUserWithFilter(null, userDetails.getUsername(),null,null,null);
            if( data.getStatusCode() != HttpStatusCode.valueOf(200)){
                log.error("Can't get info account");
                throw new RuntimeException("Can't get info account");
            }
            UserDto userDto = objectMapper.convertValue(Objects.requireNonNull(data.getBody()).getData(),UserDto.class);
            claims.put("name", userDto.getName());
            claims.put("email", userDto.getEmail());
            claims.put("phone", userDto.getPhoneNumber());
            claims.put("id", userDto.getId());
        }
        claims.put("roles", authorities);

        return this.createToken(claims, userDetails.getUsername(), time);
    }

    @Override
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = this.extractUsername(token);
        return (
                username.equals(userDetails.getUsername())
        );
    }

    public String createToken(final Map<String, Object> claims, final String subject, Long time) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + time))
                .signWith(SignatureAlgorithm.HS256, SECRET)
                .compact();
    }
    public static String getSecret(){
        return SECRET;
    }
}
