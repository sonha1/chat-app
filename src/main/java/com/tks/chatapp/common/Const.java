package com.tks.chatapp.common;

public class Const {

    public static final String regexEmail = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
    public static final String regexPhone = "^[0-9]{10,11}$";
    public static class ERROR_MESSAGE {
        public static final String TOKEN_INVALID = "Token invalid";
        public static final String INPUT_INVALID = "Input invalid";
        public static final String ACCOUNT_DISABLE = "Account is disabled";
        public static final String INVALID_CREDENTIALS = "invalid credentials";
        public static final String USER_OR_PASS_INCORRECT = "username or password incorrect";
        public static final String EMAIL_INCORRECT = "email incorrect";
        public static final String PHONE_NUMBER_INCORRECT = "phone number incorrect";
    }
}
