package com.tks.chatapp.util;

public class DataUtils {
    public static Boolean isNullOrEmpty(String str) {
        int strLen;
        if (str == null || (strLen = str.length()) == 0) {
            return true;
        }

        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        
        return true;
    }
}
