package com.tks.chatapp.enums;

public enum UserGender {
    MALE(1),
    FEMALE(2),
    OTHER(3);
    private final Integer value;

    UserGender(Integer value) {
        this.value = value;
    }
}
