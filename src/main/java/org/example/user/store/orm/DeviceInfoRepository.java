package org.example.user.store.orm;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceInfoRepository extends JpaRepository<DeviceInfoJpo, DeviceInfoJpo.DeviceInfoKey> {
}
