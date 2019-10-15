package com.Niek125.jwtlibrary;


import com.Niek125.jwtlibrary.key.JWTKey;
import com.jayway.jsonpath.JsonPath;

import java.security.MessageDigest;
import java.util.regex.Pattern;

public class TokenHandler implements ITokenHandler {
    private JWTKey key;
    private Token token;

    TokenHandler(JWTKey key) {
        this.key = key;
        token = null;
    }

    @Override
    public void setToken(String token) {
        this.token = new Token(token.split(Pattern.quote(".")));
        System.out.print(JsonPath.parse(this.token.getHeader()).read("$.alg").toString());
    }

    @Override
    public TokenValidationResponse validateToken() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(key.getKey(Long.parseLong(JsonPath.parse(this.token.getPayload()).read("$.exp").toString())).getBytes());
            System.out.print(new String(digest.digest((token.getHeader() + Pattern.quote(".") + token.getPayload()).getBytes())));
            if(new String(digest.digest((token.getHeader() + Pattern.quote(".") + token.getPayload()).getBytes())) != token.getSignature()){
                return TokenValidationResponse.FORGED;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return TokenValidationResponse.FORGED;
        }
        return TokenValidationResponse.GOOD;
    }

    @Override
    public UserPermissions getPayload() {
        return null;
    }
}
