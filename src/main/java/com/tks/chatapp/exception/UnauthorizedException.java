package com.tks.chatapp.exception;

public class UnauthorizedException extends IllegalArgumentException{
    public UnauthorizedException(String message){
        super(message);
    }
}
