package org.example.user.service;


import org.example.config.JwtToken;
import org.example.config.JwtTokenProvider;
import org.example.config.UserDetailsServiceImpl;
import org.example.shared.GenerateCode;
import org.example.user.aggregate.DeviceInfo;
import org.example.user.aggregate.User;
import org.example.user.aggregate.UserInfo;
import org.example.user.command.CreateDeviceInfo;
import org.example.user.command.CreateUser;
import org.example.user.command.LoginUser;
import org.example.user.command.UpdateUserByIdAndPw;
import org.example.user.query.HasDeviceInfo;
import org.example.user.query.QueryDeviceInfo;
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
    private final EmailAuthenticationTokenService emailAuthenticationTokenService;
    private final DeviceInfoService deviceInfoService;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserStore userStore, UserDetailsServiceImpl userDetailsService, EmailAuthenticationTokenService emailAuthenticationTokenService, DeviceInfoService deviceInfoService, AuthenticationConfiguration authenticationConfiguration, JwtTokenProvider jwtTokenProvider) throws Exception {
        //
        this.userStore = userStore;
        this.userDetailsService = userDetailsService;
        this.emailAuthenticationTokenService = emailAuthenticationTokenService;
        this.deviceInfoService = deviceInfoService;
        this.authenticationManager = authenticationConfiguration.getAuthenticationManager();
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * 기본 회원가입 (비회원 가입 허용)
     * @param command {name}
     * @return connectId
     */
    public String createUser(CreateUser command) {
        //
        command.setUserCode(UUID.randomUUID().toString());
        command.setConnectId(generateConnectId());
        User user = new User(command);

        this.userStore.createUser(user);

        return user.getConnectId();
    }

    private String generateConnectId() {
        while (true) {
            String connectId = GenerateCode.generate(6);
            boolean hasConnectId = this.userStore.hasConnectId(connectId);
            if (!hasConnectId) return connectId;
        }
    }

    public User loadUserByConnectId(String connectId) {
        //
        return this.userStore.loadUserBuConnectId(connectId);
    }

    public JwtToken loginUser(LoginUser command) {
        //
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                command.getUserId(),
                command.getUserPw()
        );

        //FIXME 고도화 필요
        UserDetails userDetails = userDetailsService.loadUserByUsername(command.getUserId());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        User user = this.userStore.loadUserByUserId(command.getUserId());

        if(deviceInfoService.hasDeviceInfo(new HasDeviceInfo(command.getDeviceId(), user.getConnectId()))) {
            DeviceInfo deviceInfo = this.deviceInfoService.loadDeviceInfo(new QueryDeviceInfo(command.getDeviceId(), user.getConnectId()));
            this.deviceInfoService.updateDeviceInfo(deviceInfo);
        } else {
            this.deviceInfoService.createDeviceInfo(new CreateDeviceInfo(command.getDeviceId(), user.getConnectId()));
        }

        return jwtTokenProvider.generateToken(authentication, command.getDeviceId(), user.getConnectId(), new UserInfo(user));
    }

    public boolean hasUserId(String userId) {
        //
        return this.userStore.hasUserId(userId);
    }

    public void updateUser(User user) {
        //
        this.userStore.updateUser(user);
    }

    public void updateUser(UpdateUserByIdAndPw command) {
        User user = loadUserByConnectId(command.getConnectId());
        boolean isCertificationEmail = emailAuthenticationTokenService.isCertificationEmail(user.getConnectId(), command.getUserEmail(), true);

        if (!isCertificationEmail) throw new RuntimeException();

        user.setUserId(command.getUserId());
        user.setUserPw(passwordEncoder.encode(command.getUserPw()));
        user.setEmail(command.getUserEmail());
        updateUser(user);
    }

}
