package br.com.managerfinances.api.service;

import br.com.managerfinances.api.bean.User;
import br.com.managerfinances.api.dto.UserSigninDTO;
import br.com.managerfinances.api.repository.UserRepository;
import br.com.managerfinances.api.secutity.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    public Map<String, String> loginWithEmailPassword(UserSigninDTO signinRequest) throws AuthenticationException {
        try {

            Authentication authentication = authenticationManager.
                    authenticate(new UsernamePasswordAuthenticationToken(signinRequest.email(), signinRequest.password()));

            User user = userRepository.findByEmail(signinRequest.email()).orElseThrow(() -> new RuntimeException("User not found"));
            String token = jwtUtil.generateToken(user.getId().toString(), user.getName(), user.getEmail());
            return Map.of("token", token, "email", user.getEmail());
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid email or password") {
            };
        }
    }
}
