package org.example.user.store.orm;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmailAuthenticationTokenRepository extends JpaRepository<EmailAuthenticationTokenJpo, String> {
    //
    List<EmailAuthenticationTokenJpo> findAllByExpirationAtLessThanEqual(long expirationAt);

    boolean existsByConnectIdAndEmailAndCertification(String connectId, String email, boolean certification);
}
