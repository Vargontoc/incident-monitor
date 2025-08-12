package es.agonzalez.incident.monitor.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.agonzalez.incident.monitor.auth.RegisterRequest;
import es.agonzalez.incident.monitor.auth.TokenPair;
import es.agonzalez.incident.monitor.models.User;
import es.agonzalez.incident.monitor.repositories.UserRepository;

@Service
public class AuthService {
    
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.username())) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (userRepository.existsByEmail(request.email())) {
            throw new IllegalArgumentException("Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.password());
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(encodedPassword);
        user.setFirstName("tester");
        user.setLastName("tester");



        userRepository.save(user);
    }   

    public TokenPair login(String username, String password) {
        User user = userRepository.findByUsername(username)
            .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        String accessToken = jwtService.generateAccessToken(user.getId().toString());
        String refreshToken = jwtService.generateRefreshToken(user.getId().toString());
        return new TokenPair(accessToken, refreshToken);
    }
}
