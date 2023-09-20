package org.example.user.service;

import lombok.extern.slf4j.Slf4j;
import org.example.user.aggregate.AuthInfo;
import org.example.user.command.CreateAuthInfo;
import org.example.user.store.AuthInfoStore;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthInfoService {
    private final AuthInfoStore authInfoStore;

    public AuthInfoService(AuthInfoStore authInfoStore) {
        this.authInfoStore = authInfoStore;
    }
    public void createAuthInfo(CreateAuthInfo command) {
        AuthInfo authInfo = new AuthInfo(command);
        // token 생성
        this.authInfoStore.createAuthInfo(authInfo);
    }

    public AuthInfo loadAuthInfo(String userCode, String deviceId) {
        AuthInfo authInfo = this.authInfoStore.loadAuthInfo(deviceId);
        if (isExpiredRefreshToken(authInfo)) {
            tokenReissue(authInfo, userCode);
        }
        return authInfo;
    }

    public String getAccessToke(AuthInfo authInfo) {
        //
        return "accessToken";
    }

    public void tokenReissue(AuthInfo authInfo, String userCode) {
        //token 재발행 후 저장및 발행
    }

    private boolean isExpiredRefreshToken(AuthInfo authInfo) {
        long now = System.currentTimeMillis();
        return now > authInfo.getExpirationAt();
    }
}
