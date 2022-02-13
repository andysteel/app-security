package com.gmail.andersoninfonet.appsecurityclient.utils;

import java.util.UUID;

public class TokenUtil {
    
    private TokenUtil() {}

    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
