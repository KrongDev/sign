package org.example.user.store;

import org.example.user.aggregate.AuthInfo;
import org.example.user.store.orm.AuthInfoJpo;
import org.example.user.store.orm.AuthInfoRepository;
import org.springframework.stereotype.Repository;

import java.util.NoSuchElementException;
import java.util.Optional;

@Repository
public class AuthInfoStore {
    private final AuthInfoRepository authInfoRepository;

    public AuthInfoStore(AuthInfoRepository authInfoRepository) {
        //
        this.authInfoRepository = authInfoRepository;
    }

    public void createAuthInfo(AuthInfo authInfo) {
        //
        AuthInfoJpo jpo = new AuthInfoJpo(authInfo);
        this.authInfoRepository.save(jpo);
    }

    public AuthInfo loadAuthInfo(String deviceId) {
        //
        Optional<AuthInfoJpo> jpo = this.authInfoRepository.findByDeviceId(deviceId);
        if(jpo.isEmpty()) throw new NoSuchElementException();
        return jpo.get().toDomain();
    }

    public void update(AuthInfo authInfo) {
        //
        this.authInfoRepository.save(new AuthInfoJpo(authInfo));
    }
}
