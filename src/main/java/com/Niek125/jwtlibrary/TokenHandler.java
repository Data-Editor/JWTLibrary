package com.Niek125.jwtlibrary;


import com.Niek125.jwtlibrary.Token.Token;
import com.Niek125.jwtlibrary.key.JWTKey;
import com.jayway.jsonpath.JsonPath;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
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
    }

    @Override
    public TokenValidationResponse validateToken() {
        try {
            //check for blacklist
            MessageDigest digest = MessageDigest.getInstance(JsonPath.parse(this.token.getHeader()).read("$.alg"));
            digest.update(key.getKey(Long.parseLong(JsonPath.parse(this.token.getPayload()).read("$.exp").toString())).getBytes());
            String repSig = Base64.getUrlEncoder().encodeToString(
                    new String(
                            digest.digest(
                                    (token.getHeader() + token.getPayload()).getBytes(StandardCharsets.ISO_8859_1))
                    ).getBytes(StandardCharsets.ISO_8859_1));
            if(!repSig.equals(token.getSignature()) || !JsonPath.parse(this.token.getHeader()).read("$.typ").equals("JWT")){
                return TokenValidationResponse.FORGED;
            }
            //generate user perms here in case of crash format is invalid
        } catch (Exception e) {//false algorithm, format, expired
            return TokenValidationResponse.FORGED;
        }
        return TokenValidationResponse.GOOD;
    }

    @Override
    public UserPermissions getPayload() {
        return null;
    }
}
