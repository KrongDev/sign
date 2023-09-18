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

    public void createUser(User user, String pw) {
        //
        this.userRepository.save(new UserJpo(user, pw));
    }

    public User loadUser(String id, String pw) {
        //
        Optional<UserJpo> jpo = this.userRepository.findByIdAndPw(id, pw);
        if(jpo.isEmpty()) throw new NoSuchElementException();
        return jpo.get().toDomain();
    }
}
