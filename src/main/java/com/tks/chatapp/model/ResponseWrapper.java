package com.tks.chatapp.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseWrapper {
    private static final String DEFAULT_STATUS = String.valueOf(HttpStatus.OK.value());
    private static final String DEFAULT_MESSAGE = "success";

    private String responseCode;

    @Builder.Default
    private String responseMessage = DEFAULT_MESSAGE;

    private Object responseData;

    public ResponseWrapper(String responseMessage, Object data) {
        this.responseData = data;
        this.responseMessage = responseMessage;
    }

    public ResponseWrapper(Object data) {
        this.responseData = data;
    }

    public ResponseWrapper(String status, String message) {
        this.responseCode = status;
        this.responseMessage = message;
    }
}
