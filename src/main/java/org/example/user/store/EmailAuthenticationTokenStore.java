package org.example.user.store;

import org.example.user.aggregate.EmailAuthenticationToken;
import org.example.user.store.orm.EmailAuthenticationTokenJpo;
import org.example.user.store.orm.EmailAuthenticationTokenRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class EmailAuthenticationTokenStore {
    private final EmailAuthenticationTokenRepository emailAuthenticationTokenRepository;

    public EmailAuthenticationTokenStore(EmailAuthenticationTokenRepository emailAuthenticationTokenRepository) {
        this.emailAuthenticationTokenRepository = emailAuthenticationTokenRepository;
    }

    public void createEmailAuthenticationToken(EmailAuthenticationToken emailAuthenticationToken) {
        //
        this.emailAuthenticationTokenRepository.save(new EmailAuthenticationTokenJpo(emailAuthenticationToken));
    }

    public EmailAuthenticationToken loadEmailAuthenticationToken(String connectId) {
        //
        Optional<EmailAuthenticationTokenJpo> req = this.emailAuthenticationTokenRepository.findById(connectId);
        if(req.isEmpty()) throw new NoSuchElementException();
        return req.get().toDomain();
    }

    public List<EmailAuthenticationToken> expiringTokens(long expirationAt) {
        //
        List<EmailAuthenticationTokenJpo> jpos = this.emailAuthenticationTokenRepository.findAllByExpirationAtLessThanEqual(expirationAt);
        return jpos.stream().map(EmailAuthenticationTokenJpo::toDomain).collect(Collectors.toList());
    }

    public boolean isCertificationEmail(String connectId, String email, boolean certification) {
        //
        return this.emailAuthenticationTokenRepository.existsByConnectIdAndEmailAndCertification(connectId, email, certification);
    }

    public void update(EmailAuthenticationToken emailAuthenticationToken) {
        //
        this.emailAuthenticationTokenRepository.save(new EmailAuthenticationTokenJpo(emailAuthenticationToken));
    }

    public void deleteTokens(List<String> tokenIds) {
        //
        this.emailAuthenticationTokenRepository.deleteAllById(tokenIds);
    }
}
