package br.com.managerfinances.api.controller;

import br.com.managerfinances.api.bean.User;
import br.com.managerfinances.api.dto.UserSigninDTO;
import br.com.managerfinances.api.dto.UserSignupDTO;
import br.com.managerfinances.api.repository.UserRepository;
import br.com.managerfinances.api.secutity.GoogleTokenVerifier;
import br.com.managerfinances.api.secutity.JwtUtil;
import br.com.managerfinances.api.service.AuthService;
import br.com.managerfinances.api.service.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("api/auth")
public class AuthController {


    private final GoogleTokenVerifier googleTokenVerifier;
    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthService authService;

    public AuthController(GoogleTokenVerifier googleTokenVerifier, JwtUtil jwtUtil, UserService userService, AuthenticationManager authenticationManager, UserRepository userRepository, AuthService authService) {
        this.googleTokenVerifier = googleTokenVerifier;
        this.jwtUtil = jwtUtil;
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/google")
    public ResponseEntity<Map<String, String>> loginWithGoogle(@RequestBody Map<String, String> body) {
        try {
            String idTokenString = body.get("idToken");
            GoogleIdToken.Payload payload = googleTokenVerifier.verify(idTokenString);
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            String token = jwtUtil.generateToken(payload.getSubject(), name, email);
            return ResponseEntity.ok(Map.of("token", token, "email", email));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }


    @PostMapping("/google/register")
    @ResponseBody
    public Map<String, String> registerWithGoogle(@RequestBody Map<String, Object> body) {
        try {
            String idTokenString = (String) body.get("idToken");
            Map<String, String> userDetails = (Map<String, String>) body.get("user");
            GoogleIdToken.Payload payload = googleTokenVerifier.verify(idTokenString);
            String email = payload.getEmail();
            String name = (String) payload.get("name");
            User user = userService.registerUser(email, name, userDetails);
            String token = jwtUtil.generateToken(user.getId().toString(), user.getName(), user.getEmail());
            return Map.of("token", token, "email", email);
        } catch (Exception e) {
            return Map.of("message", e.getMessage());
        }
    }

    @PostMapping("/sign-up")
public ResponseEntity<User> signUp(@RequestBody UserSignupDTO userSignupDTO) {
        var user = userService.registerUserWithEmailPassword(userSignupDTO);
        return ResponseEntity.created(null).body(user);
    }

    @PostMapping("login")
    public ResponseEntity<Map<String, String>> loginWithEmailPassword(@RequestBody UserSigninDTO signinRequest) {
        try {

            var authenticated = authService.loginWithEmailPassword(signinRequest);

            return ResponseEntity.ok(Map.of("token", authenticated.get("token"), "email", authenticated.get("email")));
        } catch (AuthenticationException e) {
            return ResponseEntity.badRequest().body(Map.of("message", "Usuario ou senha invalidos"));
        } catch(Exception e){
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}

