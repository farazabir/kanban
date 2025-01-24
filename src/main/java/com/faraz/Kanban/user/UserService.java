package com.faraz.Kanban.user;

import com.faraz.Kanban.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder,EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    public User registerNewUser(User user) throws Exception{
        if(isAlreadyExists(user.getEmail())){
            throw new Exception("User with this email already exists");
        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        User savedUser = userRepository.save(user);
        emailService.sendEmail(savedUser.getEmail(),"Greet","Welcome to kanban");
        return savedUser;
    }

    public boolean authenticatedUser(String email ,String password ) {
        User user = userRepository.findByEmail(email);
        if (user==null){
            return false;
        }
        return passwordEncoder.matches(password,user.getPassword());
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public boolean isAlreadyExists(String email){
        return userRepository.findByEmail(email) != null ;
    }
}
