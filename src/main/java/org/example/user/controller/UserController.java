package org.example.user.controller;

import org.example.config.JwtToken;
import org.example.user.command.CreateUser;
import org.example.user.command.LoginUser;
import org.example.user.command.UpdateUserByIdAndPw;
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

    @PostMapping("/login")
    public JwtToken loginUser(@RequestBody LoginUser command) {
        //
        return this.userService.loginUser(command);
    }

    @GetMapping("/check-userId")
    public boolean hasUserId(@RequestParam String userId) {
        //
        return this.userService.hasUserId(userId);
    }

    @PutMapping
    public void updateUser(@RequestBody UpdateUserByIdAndPw command) {
        //
        this.userService.updateUser(command);
    }
}
