package org.example.user.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.user.command.CreateAuthInfo;

@Getter
@Setter
@AllArgsConstructor
public class AuthInfo {
    private String deviceId;
    private String connectId;
    private String refreshToken;
    private long createAt;
    private long expirationAt;

    public AuthInfo() {
        //
        this.createAt = System.currentTimeMillis();
    }

    public AuthInfo(CreateAuthInfo command) {
        this();
        this.deviceId = command.getDeviceId();
        this.connectId = command.getConnectId();
        this.refreshToken = command.getRefreshToken();
        this.expirationAt = command.getExpireToken();
    }
}
