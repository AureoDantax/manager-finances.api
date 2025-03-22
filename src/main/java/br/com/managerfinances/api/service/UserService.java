package br.com.managerfinances.api.service;

import br.com.managerfinances.api.dto.UserSignupDTO;
import br.com.managerfinances.api.bean.User;
import br.com.managerfinances.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    public User registerUser(String email, String name, Map<String, String> userDetails) {
        return userRepository.findByEmail(email).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setName(name);
            newUser.setFirstName(userDetails.get("firstName"));
            newUser.setLastName(userDetails.get("lastName"));
            return userRepository.save(newUser);
        });
    }

    public User registerUserWithEmailPassword(UserSignupDTO userSignupDTO) {
        return userRepository.findByEmail(userSignupDTO.email()).orElseGet(() -> {
            User newUser = new User();
            newUser.setEmail(userSignupDTO.email());
            newUser.setName(userSignupDTO.firstName()+" "+userSignupDTO.lastName());
            newUser.setPassword(passwordEncoder.encode(userSignupDTO.password()));
            return userRepository.save(newUser);
        });
    }

}

