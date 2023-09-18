package org.example.user.service;


import org.example.user.aggregate.User;
import org.example.user.command.CreateUser;
import org.example.user.store.UserStore;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {
    //
    private final UserStore userStore;

    public UserService(UserStore userStore) {
        //
        this.userStore = userStore;
    }

    public String createUser(CreateUser command) {
        //
        command.setId(UUID.randomUUID().toString());
        User user = new User(command);

        //TODO 비밀번호 암호화

        this.userStore.createUser(user, command.getPw());

        return user.getId();
    }

    public User loadUser(String id, String pw) {
        return this.userStore.loadUser(id, pw);
    }
}
