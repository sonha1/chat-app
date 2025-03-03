package com.tks.chatapp.entity;

import com.tks.chatapp.enums.UserGender;
import com.tks.chatapp.enums.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class User extends BaseEntity {

    @Column(name = "username")
    private String username;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false)
    private String phone;

    @Column(name = "password", length = 512, nullable = false)
    private String password;

    @Column(name = "age")
    private Integer age;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private UserGender gender;

    @Column(name = "birthday")
    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date birthday;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private UserStatus  status;
}
