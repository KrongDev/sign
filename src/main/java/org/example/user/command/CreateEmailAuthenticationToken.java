package org.example.user.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEmailAuthenticationToken {
    private String userEmail;
    private String connectId;
    private transient String token;
    private transient long expirationAt;
}
