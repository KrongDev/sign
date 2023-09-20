package org.example.user.schedule;

import lombok.extern.slf4j.Slf4j;
import org.example.user.aggregate.EmailAuthenticationToken;
import org.example.user.service.EmailAuthenticationTokenService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class EmailScheduler {
    //
    private final EmailAuthenticationTokenService emailAuthenticationTokenService;

    public EmailScheduler(EmailAuthenticationTokenService emailAuthenticationTokenService) {
        //
        this.emailAuthenticationTokenService = emailAuthenticationTokenService;
    }

    @Scheduled(fixedDelay = 60000)
    public void removeExpiringToken() {
        log.info(">>> removeExpiringToken check start");
        long now = System.currentTimeMillis();
        List<EmailAuthenticationToken> tokens = this.emailAuthenticationTokenService.expiringTokens(now);
        log.info(">>> expiringTokens length : " + tokens.size());
        this.emailAuthenticationTokenService.deleteTokens(tokens.stream().map(EmailAuthenticationToken::getConnectId).collect(Collectors.toList()));
        log.info(">>> removeExpiringToken check end");
    }
}
