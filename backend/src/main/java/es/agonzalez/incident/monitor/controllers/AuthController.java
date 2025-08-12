package es.agonzalez.incident.monitor.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

import es.agonzalez.incident.monitor.security.AuthService;
import es.agonzalez.incident.monitor.security.JwtService;
import es.agonzalez.incident.monitor.auth.RegisterRequest;
import es.agonzalez.incident.monitor.auth.LoginRequest;
import es.agonzalez.incident.monitor.auth.TokenPair;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController 
{
    @Autowired
    private AuthService authService;
    @Autowired
    private JwtService jwtService;

    @Profile("dev")
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterRequest request) 
    {
        authService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<TokenPair> login(@RequestBody LoginRequest request) 
    {
        TokenPair tokenPair = authService.login(request.username(), request.password());
        return ResponseEntity.ok(tokenPair);
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenPair> refresh(@RequestBody String refreshToken) 
    {
        // Implement refresh token logic if needed
        // For now, we can return a new token pair using the same username
        var claims = jwtService.parseToken(refreshToken).getBody();
        var access = jwtService.generateAccessToken(claims.getSubject());
        var refresh = new TokenPair(access, refreshToken);
        
        return ResponseEntity.ok(refresh);
    }
   
}
