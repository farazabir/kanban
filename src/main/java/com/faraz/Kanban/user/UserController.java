package com.faraz.Kanban.user;

import com.faraz.Kanban.config.JWTUtil;
import com.faraz.Kanban.email.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="api/v1/user")
public class UserController {
    private final UserService userService;
    private final JWTUtil jwtUtil;
    @Autowired
    public UserController(UserService userService,JWTUtil jwtUtil)  {
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }


    @PostMapping
    public ResponseEntity<Object> registerUser(@RequestBody User user){
     try{
         User savedUser = userService.registerNewUser(user);
         return new ResponseEntity<>(savedUser,HttpStatus.CREATED);
     }catch (Exception e){
         return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
     }
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user) throws Exception {

        boolean isAuthenticated = userService.authenticatedUser(user.getEmail(),user.getPassword());
        if(isAuthenticated){
            User authenticatedUser = userService.findUserByEmail(user.getEmail());
            String token = jwtUtil.generateToken(authenticatedUser.getId(),authenticatedUser.getEmail());
            return ResponseEntity.ok().body(token);
        }else{
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<Object> userProfile(){
     String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
     User user = userService.findUserByEmail(email);

     return ResponseEntity.ok().body(user);
    }

}
