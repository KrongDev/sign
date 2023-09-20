package org.example.user.aggregate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.user.command.CreateDeviceInfo;

import java.sql.Date;

@Getter
@Setter
@AllArgsConstructor
public class DeviceInfo {
    //
    private String deviceId;
    private String connectId;
    private String platformType;
    private String description;
    private String fcmToken;

    private Date connectAt;

    public DeviceInfo() {
        //
        this.connectAt = new Date(System.currentTimeMillis());
    }

    public DeviceInfo(CreateDeviceInfo command) {
        //
        this();
        this.deviceId = command.getDeviceId();
        this.connectId = command.getConnectId();
    }
}
