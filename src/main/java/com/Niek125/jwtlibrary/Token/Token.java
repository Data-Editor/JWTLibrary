package com.Niek125.jwtlibrary.Token;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.regex.Pattern;

public class Token implements IToken {
    private String header;
    private String payload;
    private String signature;

    public Token(String token) throws IllegalArgumentException {
        try {
            String[] tokenSplit = token.split(Pattern.quote("."));
            if (tokenSplit.length != 3) {
                throw new IllegalArgumentException();
            }
            header = new String(Base64.getUrlDecoder().decode(tokenSplit[0].getBytes(StandardCharsets.ISO_8859_1)));
            payload = new String(Base64.getUrlDecoder().decode(tokenSplit[1].getBytes(StandardCharsets.ISO_8859_1)));
            signature = tokenSplit[2];
        } catch (Exception e) {
            throw new IllegalArgumentException();
        }
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
