package com.tks.chatapp.exception;


public class AccountDisableException  extends IllegalArgumentException{
    public AccountDisableException(String message){
        super(message);
    }
}
