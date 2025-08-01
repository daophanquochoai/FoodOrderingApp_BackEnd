package doctorhoai.learn.authservice.controller;

import doctorhoai.learn.authservice.controller.message.EMessageResponse;
import doctorhoai.learn.authservice.dto.RequestAuth;
import doctorhoai.learn.authservice.dto.ResponseAuth;
import doctorhoai.learn.authservice.jwt.service.jwt.JwtService;
import doctorhoai.learn.authservice.jwt.service.token.TokenService;
import doctorhoai.learn.basedomain.exception.BadException;
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

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<ResponseAuth> login(@RequestBody @Valid RequestAuth auth){
        try{
            Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(auth.getUsername(), auth.getPassword()));
            if( authenticate.isAuthenticated()){
                UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
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
}
