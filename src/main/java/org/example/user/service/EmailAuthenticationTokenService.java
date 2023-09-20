package org.example.user.service;

import lombok.extern.slf4j.Slf4j;
import org.example.pigeon.email.EmailSender;
import org.example.shared.GenerateCode;
import org.example.user.aggregate.EmailAuthenticationToken;
import org.example.user.command.CreateEmailAuthenticationToken;
import org.example.user.store.EmailAuthenticationTokenStore;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;

import static org.example.pigeon.email.vo.EmailSendSubject.VERIFICATION_EMAIL;

@Slf4j
@Service
public class EmailAuthenticationTokenService {
    private final EmailAuthenticationTokenStore emailAuthenticationTokenStore;
    private final EmailSender emailSender;

    public EmailAuthenticationTokenService(EmailAuthenticationTokenStore emailAuthenticationTokenStore, EmailSender emailSender) {
        this.emailAuthenticationTokenStore = emailAuthenticationTokenStore;
        this.emailSender = emailSender;
    }

    public long createEmailAuthenticationToken(CreateEmailAuthenticationToken command) {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, 3);
        command.setExpirationAt(calendar.getTimeInMillis());

        String token = GenerateCode.generate(6);
        command.setToken(token);

        EmailAuthenticationToken emailAuthenticationToken = new EmailAuthenticationToken(command);
        this.emailAuthenticationTokenStore.createEmailAuthenticationToken(emailAuthenticationToken);

        emailSender.send(command.getUserEmail(), VERIFICATION_EMAIL, String.format("인증 번호 : %s", token));
        return emailAuthenticationToken.getExpirationAt();
    }

    public boolean checkEmailAuthenticationToken(String connectId, String token) {
        EmailAuthenticationToken emailAuthenticationToken = this.emailAuthenticationTokenStore.loadEmailAuthenticationToken(connectId);
        if (isExpirationAt(emailAuthenticationToken.getExpirationAt())){
            return false;
        }

        if(!isToken(emailAuthenticationToken.getToken(), token)) {
            return false;
        }

        emailAuthenticationToken.setCertification(true);
        this.emailAuthenticationTokenStore.update(emailAuthenticationToken);
        return true;
    }

    private boolean isExpirationAt(long expirationAt) {
        //
        long now = System.currentTimeMillis();
        return now > expirationAt;
    }

    private boolean isToken(String storeToken, String token) {
        //
        return storeToken.equals(token);
    }

    public List<EmailAuthenticationToken> expiringTokens(long expirationAt) {
        //
        return this.emailAuthenticationTokenStore.expiringTokens(expirationAt);
    }

    public boolean isCertificationEmail(String connectId, String email, boolean certification) {
        //
        return this.emailAuthenticationTokenStore.isCertificationEmail(connectId, email, certification);
    }

    public void deleteTokens(List<String> tokenIdes) {
        //
        this.emailAuthenticationTokenStore.deleteTokens(tokenIdes);
    }
}
