package com.tks.chatapp.enums;

public enum UserStatus {
    ACTIVE(0),
    DEACTIVE(1);

    private final Integer value;

    UserStatus(Integer value) {
        this.value = value;
    }
}
