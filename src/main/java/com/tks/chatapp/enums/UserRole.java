package com.tks.chatapp.enums;


public enum UserRole {
    ADMIN(0),
    USER(1);

    private final Integer value;

    UserRole(Integer value) {
        this.value = value;
    }
}
