package org.example.user.service;

import org.example.user.aggregate.DeviceInfo;
import org.example.user.command.CreateDeviceInfo;
import org.example.user.query.HasDeviceInfo;
import org.example.user.query.QueryDeviceInfo;
import org.example.user.store.DeviceInfoStore;
import org.example.user.store.orm.DeviceInfoJpo;
import org.springframework.stereotype.Service;

@Service
public class DeviceInfoService {
    //
    private final DeviceInfoStore deviceInfoStore;

    public DeviceInfoService(DeviceInfoStore deviceInfoStore) {
        //
        this.deviceInfoStore = deviceInfoStore;
    }

    public void createDeviceInfo(CreateDeviceInfo command) {
        //
        DeviceInfo deviceInfo = new DeviceInfo(command);
        this.deviceInfoStore.createDeviceInfo(deviceInfo);
        //FIXME JWT 반환
    }

    public DeviceInfo loadDeviceInfo(QueryDeviceInfo query) {
        //
        return this.deviceInfoStore.loadDeviceInfo(new DeviceInfoJpo.DeviceInfoKey(query.getDeviceId(), query.getConnectId()));
    }

    public boolean hasDeviceInfo(HasDeviceInfo query) {
        //
        return this.deviceInfoStore.hasDeviceInfo(new DeviceInfoJpo.DeviceInfoKey(query.getDeviceId(), query.getConnectId()));
    }

    public void updateDeviceInfo(DeviceInfo deviceInfo) {
        //
        this.deviceInfoStore.updateDeviceInfo(deviceInfo);
    }
}
