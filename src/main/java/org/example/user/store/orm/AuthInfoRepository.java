package org.example.user.store.orm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthInfoRepository extends JpaRepository<AuthInfoJpo, String> {
    Optional<AuthInfoJpo> findByDeviceId(String deviceId);
}
