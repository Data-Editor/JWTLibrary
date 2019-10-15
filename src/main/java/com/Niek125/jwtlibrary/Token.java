package com.Niek125.jwtlibrary;

import java.util.Base64;

public class Token {
    private String header;
    private String payload;
    private String signature;

    Token(String[] tokenSplit){
        header = new String(Base64.getUrlDecoder().decode(tokenSplit[0]));
        payload = new String(Base64.getUrlDecoder().decode(tokenSplit[1]));
        signature = new String(Base64.getUrlDecoder().decode(tokenSplit[2]));
    }

    public String getHeader() {
        return header;
    }

    public String getPayload() {
        return payload;
    }

    public String getSignature() {
        return signature;
    }
}
