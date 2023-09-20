package org.example.user.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.user.command.CreateEmailAuthenticationToken;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmailAuthenticationToken {
    private String connectId;
    private String email;
    private String token;
    private long expirationAt;

    private boolean certification;

    public EmailAuthenticationToken(CreateEmailAuthenticationToken command) {
        this.connectId = command.getConnectId();
        this.email = command.getUserEmail();
        this.token = command.getToken();
        this.expirationAt = command.getExpirationAt();
        this.certification = false;
    }
}
