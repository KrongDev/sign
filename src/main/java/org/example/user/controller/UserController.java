package org.example.user.controller;

import org.example.config.JwtToken;
import org.example.user.command.CreateUser;
import org.example.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    //
    private final UserService userService;

    public UserController(UserService userService) {
        //
        this.userService = userService;
    }

    @PostMapping
    public String createUser(@RequestBody CreateUser command) {
        //
        return this.userService.createUser(command);
    }

    @GetMapping
    public JwtToken loadUser(@RequestParam String id, @RequestParam String pw) {
        //
        return this.userService.loadUser(id, pw);
    }
}
