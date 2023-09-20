package org.example.user.store.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shared.DomainEntity;
import org.example.user.aggregate.EmailAuthenticationToken;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_email_authentication_token")
public class EmailAuthenticationTokenJpo implements DomainEntity<EmailAuthenticationToken> {
    @Id
    private String connectId;
    private String email;
    private String token;
    private long expirationAt;

    private boolean certification;

    public EmailAuthenticationTokenJpo(EmailAuthenticationToken emailAuthenticationToken) {
        this.connectId = emailAuthenticationToken.getConnectId();
        this.email = emailAuthenticationToken.getEmail();
        this.token = emailAuthenticationToken.getToken();
        this.expirationAt = emailAuthenticationToken.getExpirationAt();
        this.certification = emailAuthenticationToken.isCertification();
    }

    @Override
    public EmailAuthenticationToken toDomain() {
        return new EmailAuthenticationToken(
                this.connectId,
                this.email,
                this.token,
                this.expirationAt,
                this.certification
        );
    }
}
