package com.tks.chatapp.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
@AllArgsConstructor
public class ErrorDetails {
    private static final String DEFAULT_STATUS =  String.valueOf(HttpStatus.BAD_REQUEST.value());
    private static final String DEFAULT_MESSAGE = "Failed";

    private String responseCode;

    @Builder.Default
    private String responseMessage = DEFAULT_MESSAGE;

    private String responseData;
}

