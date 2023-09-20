package org.example.user.store.orm;

import lombok.*;
import org.example.shared.DomainEntity;
import org.example.user.aggregate.DeviceInfo;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_device_info")
public class DeviceInfoJpo implements DomainEntity<DeviceInfo> {
    @Id
    @EmbeddedId
    private DeviceInfoKey key;

    private String platformType;
    @Lob
    private String description;
    private String fcmToken;
    @Column(columnDefinition = "DATE")
    private Date connectAt;

    public DeviceInfoJpo(DeviceInfo deviceInfo) {
        //
        this.key = new DeviceInfoKey(deviceInfo.getDeviceId(), deviceInfo.getConnectId());
        this.connectAt = deviceInfo.getConnectAt();
    }

    @Override
    public DeviceInfo toDomain() {
        return new DeviceInfo(
                this.key.deviceId,
                this.key.connectId,
                this.platformType,
                this.description,
                this.fcmToken,
                this.connectAt
        );
    }

    @Data
    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Embeddable
    public static class DeviceInfoKey implements Serializable {
        private String deviceId;
        private String connectId;
    }
}
