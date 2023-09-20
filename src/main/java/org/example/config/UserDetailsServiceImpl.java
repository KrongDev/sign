package org.example.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.example.user.aggregate.User;
import org.example.user.store.UserStore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Log4j2
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserStore userStore;

    /**
     * key = deviceId + userPw
     */
    @Override
    public UserDetails loadUserByUsername(String key) {


        User userInfo = userStore.loadUserByUserId(key);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        return new org
                .springframework
                .security
                .core
                .userdetails
                .User(userInfo.getUserId(), userInfo.getUserPw(), grantedAuthorities);
    }
}
