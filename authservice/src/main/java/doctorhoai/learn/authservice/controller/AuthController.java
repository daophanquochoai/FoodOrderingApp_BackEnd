package doctorhoai.learn.authservice.controller;

import doctorhoai.learn.authservice.business.userservice.model.UpdatePassword;
import doctorhoai.learn.authservice.business.userservice.service.employee.EmployeeFeign;
import doctorhoai.learn.authservice.business.userservice.service.user.UserFeign;
import doctorhoai.learn.authservice.controller.message.EMessageResponse;
import doctorhoai.learn.authservice.dto.RequestAuth;
import doctorhoai.learn.authservice.dto.ResponseAuth;
import doctorhoai.learn.authservice.jwt.service.jwt.JwtService;
import doctorhoai.learn.authservice.jwt.service.token.TokenService;
import doctorhoai.learn.authservice.jwt.utils.JwtUtil;
import doctorhoai.learn.authservice.service.mail.MailService;
import doctorhoai.learn.basedomain.exception.BadException;
import doctorhoai.learn.basedomain.response.ResponseObject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;
    private final UserFeign userFeign;
    private final EmployeeFeign employeeFeign;
    private final JwtUtil jwtUtil;
    private final MailService mailService;

    @PostMapping("/login")
    public ResponseEntity<ResponseAuth> login(@RequestBody @Valid RequestAuth auth){
        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
            if( authenticate.isAuthenticated()){
                UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
                if( userDetails.getAuthorities().stream().findFirst().isEmpty()){
                    throw new BadException("Bad credentials");
                }else{
                    if( userDetails.getAuthorities().stream().findFirst().get().getAuthority().equals("ROLE_USER")){
                        userFeign.updateLatestUpdateTime(userDetails.getUsername());
                    }else{
                        employeeFeign.getLateLoginEmployee(userDetails.getUsername());
                    }
                }
                String accessToken = jwtService.generateToken(userDetails);
                String refreshToken = jwtService.generateRefreshToken(userDetails);
                tokenService.saveToken(accessToken, userDetails.getUsername());
                tokenService.saveToken(refreshToken, userDetails.getUsername());
                return ResponseEntity.ok(
                        ResponseAuth.builder()
                                .access_token(jwtService.generateToken((userDetails)))
                                .refresh_token(jwtService.generateRefreshToken(userDetails))
                                .data(userDetails)
                                .build()
                );
            }
            throw new BadException("Bad credentials");
        }catch(Exception e){
            throw new BadException("Bad credentials");
        }
    }

    @PostMapping("/refreshToken/{token}/{refreshToken}")
    public ResponseEntity<ResponseAuth> refreshToken(@PathVariable String refreshToken, @PathVariable String token){
        String username = jwtService.extractUsername(refreshToken);
        final UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if( !jwtService.validateToken(refreshToken, userDetails) ){
            throw new BadCredentialsException("Bad credentials");
        }
        String accessToken = jwtService.generateToken(userDetails);
        tokenService.saveToken(accessToken, userDetails.getUsername());
        tokenService.deleteToken(token);
        return ResponseEntity.ok(
                ResponseAuth.builder()
                        .access_token(jwtService.generateToken((userDetails)))
                        .refresh_token(jwtService.generateRefreshToken(userDetails))
                        .data(userDetails)
                        .build()
        );
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(
            @RequestParam String accessToken,
            @RequestParam String refreshToken
    ){
        tokenService.deleteToken(accessToken);
        tokenService.deleteToken(refreshToken);
        return ResponseEntity.ok(EMessageResponse.LOGOUT_SUCCESSFUL.getMessage());
    }

    @GetMapping("/forget/{email}")
    public ResponseEntity<Void> forget(@PathVariable String email, HttpServletRequest request){
        try{
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            String token = jwtUtil.generateToken(userDetails, TimeUnit.MINUTES.toMillis(30));
            tokenService.saveToken(token, userDetails.getUsername());
            String origin = request.getHeader("Origin");
            mailService.sendMail(email, "Change Password - GRILLFOOD", token + "&email=" + email, origin);
            return ResponseEntity.ok(null);
        }catch (Exception ex){
            throw new BadException("Account not found?");
        }
    }

    @PutMapping("/update/{email}")
    public ResponseEntity<Void> updatePassword(
            @PathVariable String email,
            @RequestBody UpdatePassword updatePassword
            ){
        try{
            userFeign.getForgetPassword(updatePassword, email);
                return ResponseEntity.ok(null);
        }catch(Exception ex){
            throw new BadException("Update was error");
        }
    }

}
