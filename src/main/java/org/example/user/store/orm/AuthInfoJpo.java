package org.example.user.store.orm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.shared.DomainEntity;
import org.example.user.aggregate.AuthInfo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_auth_info")
public class AuthInfoJpo implements DomainEntity<AuthInfo> {
    @Id
    private String deviceId;
    private String connectId;
    private String refreshToken;
    private long createAt;
    private long expirationAt;

    public AuthInfoJpo(AuthInfo authInfo) {
        this.deviceId = authInfo.getDeviceId();
        this.connectId = authInfo.getConnectId();
        this.refreshToken = authInfo.getRefreshToken();
        this.createAt = authInfo.getCreateAt();
        this.expirationAt = authInfo.getExpirationAt();
    }

    @Override
    public AuthInfo toDomain() {
        return new AuthInfo(
                this.deviceId,
                this.connectId,
                this.refreshToken,
                this.createAt,
                this.expirationAt
        );
    }
}
