package com.bongda.controller;



import com.bongda.model.User;
import com.bongda.respository.UserRepository;
import com.bongda.service.UserService;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/shopbongda")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/auth/register")
    public User register(@RequestParam String username, @RequestParam String password, @RequestParam String fullName,@RequestParam String email) {
        return userService.registerUser(username, password, fullName,email);
    }


    @PostMapping("/auth/login")
    public User login(@RequestParam String username, @RequestParam String password) {
        return userService.loginUser(username, password);
    }
    @PutMapping("/admin/update/{accountId}")
    public User updateUser(@PathVariable Long accountId, @RequestParam String fullName, 
                           @RequestParam String status, @RequestParam String accountType,@RequestParam String email) {
        return userService.updateUser(accountId, fullName, status, accountType,email);
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
    @GetMapping("/list")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
