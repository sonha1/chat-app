package com.tks.chatapp.request.auth;

import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private String phone;
    private String fullName;
    private String address;
    private Integer gender;
    private Integer age;

    @DateTimeFormat(pattern = "dd/mm/yyyy")
    private Date birthday;
}
