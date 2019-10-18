package com.Niek125.jwtlibrary.Token;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Token implements IToken {
    private String header;
    private String payload;
    private String signature;

    public Token(String[] tokenSplit){
        header = new String(Base64.getUrlDecoder().decode(tokenSplit[0].getBytes(StandardCharsets.ISO_8859_1)));
        payload = new String(Base64.getUrlDecoder().decode(tokenSplit[1].getBytes(StandardCharsets.ISO_8859_1)));
        signature = tokenSplit[2];
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
