package org.example.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAuthInfo {
    private String deviceId;
    private String connectId;
    private String refreshToken;
    private long expireToken;
}
