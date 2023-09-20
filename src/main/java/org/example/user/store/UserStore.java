package org.example.user.store;

import org.example.user.aggregate.User;
import org.example.user.store.orm.UserJpo;
import org.example.user.store.orm.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class UserStore {
    //
    private final UserRepository userRepository;

    public UserStore(UserRepository userRepository) {
        //
        this.userRepository = userRepository;
    }

    public void createUser(User user) {
        //
        this.userRepository.save(new UserJpo(user));
    }

    public User loadUserByUserId(String userId) {
        //
        Optional<UserJpo> jpo = this.userRepository.findByUserId(userId);
        if(jpo.isEmpty()) throw new NoSuchElementException();
        return jpo.get().toDomain();
    }

    public User loadUserBuConnectId(String connectId) {
        //
        Optional<UserJpo> jpo = this.userRepository.findByConnectId(connectId);
        if(jpo.isEmpty()) throw new NoSuchElementException();
        return jpo.get().toDomain();
    }

    public boolean hasConnectId(String connectId) {
        //
        return this.userRepository.existsByConnectId(connectId);
    }

    public boolean hasUserId(String userId) {
        //
        return this.userRepository.existsByUserId(userId);
    }

    public void updateUser(User user) {
        //
        this.userRepository.save(new UserJpo(user));
    }
}
