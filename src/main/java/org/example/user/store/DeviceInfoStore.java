package org.example.user.store;

import org.example.user.aggregate.DeviceInfo;
import org.example.user.store.orm.DeviceInfoJpo;
import org.example.user.store.orm.DeviceInfoRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class DeviceInfoStore {
    //
    private final DeviceInfoRepository deviceInfoRepository;

    public DeviceInfoStore(DeviceInfoRepository deviceInfoRepository) {
        //
        this.deviceInfoRepository = deviceInfoRepository;
    }

    public void createDeviceInfo(DeviceInfo deviceInfo) {
        //
        this.deviceInfoRepository.save(new DeviceInfoJpo(deviceInfo));
    }

    public DeviceInfo loadDeviceInfo(DeviceInfoJpo.DeviceInfoKey key) {
        //
        Optional<DeviceInfoJpo> jpo = this.deviceInfoRepository.findById(key);
        if(jpo.isEmpty()) throw new NoSuchElementException();
        return jpo.get().toDomain();
    }

    public boolean hasDeviceInfo(DeviceInfoJpo.DeviceInfoKey key) {
        //
        return this.deviceInfoRepository.existsById(key);
    }

    public void updateDeviceInfo(DeviceInfo deviceInfo) {
        //
        this.deviceInfoRepository.save(new DeviceInfoJpo(deviceInfo));
    }
}
