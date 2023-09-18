package org.example.user.service;


import org.example.config.JwtToken;
import org.example.config.JwtTokenProvider;
import org.example.config.UserDetailsServiceImpl;
import org.example.user.aggregate.User;
import org.example.user.command.CreateUser;
import org.example.user.store.UserStore;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    //
    private final UserStore userStore;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserStore userStore, UserDetailsServiceImpl userDetailsService, AuthenticationConfiguration authenticationConfiguration, JwtTokenProvider jwtTokenProvider) throws Exception {
        //
        this.userStore = userStore;
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public String createUser(CreateUser command) {
        //
        command.setId(UUID.randomUUID().toString());
        command.setPw(passwordEncoder.encode(command.getPw()));
        User user = new User(command);

        this.userStore.createUser(user);

        return user.getId();
    }

    public JwtToken loadUser(String id, String pw) {
        //
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                id,
                pw
        );

        //FIXME 고도화 필요
        UserDetails userDetails = userDetailsService.loadUserByUsername(id);
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        User user = this.userStore.loadUser(id);
        return jwtTokenProvider.generateToken(authentication, id, user);
    }

}
