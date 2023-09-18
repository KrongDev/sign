package org.example.user.store.orm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserJpo, String> {
    //
    Optional<UserJpo> findByIdAndPw(String id, String pw);
}
