package org.example.pigeon.email.vo;

import lombok.Getter;

@Getter
public enum EmailSendSubject {
    VERIFICATION_EMAIL("BUNCH서비스 인증 이메일입니다.");

    private final String key;

    EmailSendSubject(String key) {
        //
        this.key = key;
    }
}
