package br.com.managerfinances.api.service;

import br.com.managerfinances.api.bean.User;
import br.com.managerfinances.api.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(String email, String name, Map<String, String> userDetails) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setId(UUID.randomUUID());
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setFirstName(userDetails.get("firstName"));
            newUser.setLastName(userDetails.get("lastName"));
            return userRepository.save(newUser);
        });
    }

    public User registerUserWithEmailPassword(String email, String name, String password) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setId(UUID.randomUUID());
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setPassword(bCryptPasswordEncoder.encode(password));
            return userRepository.save(newUser);
        });
    }

}

