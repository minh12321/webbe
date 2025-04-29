package com.bongda.controller;

import com.bongda.config.JwtUtil;
import com.bongda.model.User;
import com.bongda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "https://ab-mocha.vercel.app")
@RequestMapping("/shopbongda")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestParam String username, @RequestParam String password, 
                                    @RequestParam String fullName, @RequestParam String email) {
        User user = userService.registerUser(username, password, fullName, email);
        String jwtToken = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(jwtToken, user));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestParam String username, @RequestParam String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));
        
        String jwtToken = jwtUtil.generateToken(username);
        User user = userService.loginUser(username, password);
        return ResponseEntity.ok(new AuthResponse(jwtToken, user));
    }

    @PutMapping("/admin/update/{accountId}")
    public User updateUser(@PathVariable Long accountId, @RequestParam String fullName, 
                         @RequestParam String status, @RequestParam String username, 
                         @RequestParam String accountType, @RequestParam String email, 
                         @RequestParam String diachi, @RequestParam(required = false) String matkhau) {
        return userService.updateUser(accountId, fullName, status, accountType, email, diachi, matkhau, username);
    }

    @DeleteMapping("/admin/delete/{accountId}")
    public String deleteUser(@PathVariable Long accountId) {
        userService.deleteUser(accountId);
        return "User deleted successfully";
    }

    @GetMapping("/admin/update/{accountId}")
    public User getAccountDetails(@PathVariable Long accountId) {
        return userService.getUserById(accountId);
    }

    @GetMapping("/admin/user/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    @GetMapping(value = "/list", produces = "application/json")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/login/oauth2/success")
    public ResponseEntity<?> handleOAuth2Login(OAuth2AuthenticationToken authentication) {
        OAuth2User oAuth2User = authentication.getPrincipal();
        String provider = authentication.getAuthorizedClientRegistrationId();
        String providerId = oAuth2User.getAttribute("sub");
        String fullName = oAuth2User.getAttribute("name");
        String email = oAuth2User.getAttribute("email");
        User user = userService.registerSocialUser(provider, providerId, fullName, email);
        
        String jwtToken = jwtUtil.generateToken(user.getUsername());
        return ResponseEntity.ok(new AuthResponse(jwtToken, user));
    }

    // Response class for authentication
    private static class AuthResponse {
        private final String jwt;
        private final User user;

        public AuthResponse(String jwt, User user) {
            this.jwt = jwt;
            this.user = user;
        }

        public String getJwt() {
            return jwt;
        }

        public User getUser() {
            return user;
        }
    }
}