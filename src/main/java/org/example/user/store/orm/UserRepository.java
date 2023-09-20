package org.example.user.store.orm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserJpo, String> {
    //
    Optional<UserJpo> findByUserIdAndUserPw(String userId, String userPw);
    Optional<UserJpo> findByConnectId(String connectId);
    Optional<UserJpo> findByUserId(String user_id);

    boolean existsByConnectId(String connectId);
    boolean existsByUserId(String userId);
}
