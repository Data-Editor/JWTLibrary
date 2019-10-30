package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.AuthObjectMaker.AuthObject;
import com.Niek125.jwtlibrary.AuthObjectMaker.AuthObjectMaker;
import com.Niek125.jwtlibrary.BlackList.ITokenBlackList;
import com.Niek125.jwtlibrary.BlackList.TokenBlackList;
import com.Niek125.jwtlibrary.BlackList.TokenExpiration;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.ExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.IExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.JWTKey;
import com.Niek125.jwtlibrary.SignatureReplicator.SignatureReplicator;
import com.Niek125.jwtlibrary.Token.IToken;
import com.Niek125.jwtlibrary.Token.Token;
import com.Niek125.jwtlibrary.TokenHandler.TokenHandler;
import com.Niek125.jwtlibrary.TokenHandler.TokenValidationResponse;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class TokenHandlerTests {
    private TokenHandler<AuthObject> getTokenHandler() {

        TokenBlackList blackList = TokenBlackList.getInstance();
        JWTKey key = JWTKey.getInstance();
        List<IExpiringKey> keys = new ArrayList<>();
        keys.add(new ExpiringKey("failed", (System.currentTimeMillis() - 100)));
        keys.add(new ExpiringKey("isnowlonger", (System.currentTimeMillis() + (1000 * 60 * 10))));
        key.initialize("testkey", keys);
        SignatureReplicator sigRep = new SignatureReplicator(key);
        AuthObjectMaker authMaker = new AuthObjectMaker();
        TokenHandler<AuthObject> handler = new TokenHandler(300, blackList, sigRep, authMaker);
        return handler;
    }

    private IToken getToken() throws NoSuchAlgorithmException {
        String header = Base64.getUrlEncoder().encodeToString(
                ("{\"typ\":\"JWT\"," +
                        "\"alg\":\"SHA\"}")
                        .getBytes(StandardCharsets.ISO_8859_1));
        String pay = Base64.getUrlEncoder().encodeToString(
                (("{\"uid\":\"auserid\"," +
                        "\"unm\":\"ausername\"," +
                        "\"pms\":[" +
                        "{\"rln\":\"GUEST\"," +
                        "\"pid\":\"aprojectid0\"}," +
                        "{\"rln\":\"GUEST\"," +
                        "\"pid\":\"aprojectid1\"}]," +
                        "\"iss\":\"http://localhost:8080\"," +
                        "\"pfp\":\"apfp\"," +
                        "\"exp\":" + (System.currentTimeMillis() + (1000 * 60 * 5)) + "," +
                        "\"iat\":1572287565261," +
                        "\"jti\":\"8ace9486-b2f2-40a9-8b9f-c1d8b55647ff\"}").getBytes(StandardCharsets.ISO_8859_1)));
        MessageDigest digest = MessageDigest.getInstance("SHA");
        digest.update("testkeyisnowlonger".getBytes(StandardCharsets.ISO_8859_1));
        String sig = Base64.getUrlEncoder().encodeToString(
                new String(
                        digest.digest(
                                (header + pay).getBytes(StandardCharsets.ISO_8859_1))
                ).getBytes(StandardCharsets.ISO_8859_1));
        return new Token(header + "." + pay + "." + sig);
    }

    @Test
    public void noJWT() {
        TokenHandler<AuthObject> handler = getTokenHandler();
        IToken token = new Token("eyJ0eXAiOiJPYXV0aDIiLCJhbGciOiJTSEEifQ==.eyJ1aWQiOiJhdXNlcmlkIiwidW5tIjoiYXVzZXJuYW1lIiwicG1zIjpbeyJybG4iOiJHVUVTVCIsInBpZCI6ImFwcm9qZWN0aWQwIn0seyJybG4iOiJHVUVTVCIsInBpZCI6ImFwcm9qZWN0aWQxIn1dLCJpc3MiOiJcXFFodHRwOi8vbG9jYWxob3N0OjgwODBcXEUiLCJwZnAiOiJhcGZwIiwiZXhwIjoxNTcyMjkxMTY1MjYxLCJpYXQiOjE1NzIyODc1NjUyNjEsImp0aSI6IjhhY2U5NDg2LWIyZjItNDBhOS04YjlmLWMxZDhiNTU2NDdmZiJ9.Pz8_bz8_Pz94Chw_aD84RWRObz4=");
        Assert.assertEquals(TokenValidationResponse.NO_JWT, handler.validateToken(token));
    }

    @Test
    public void expired() {
        TokenHandler<AuthObject> handler = getTokenHandler();
        IToken token = new Token("eyJ0eXAiOiJKV1QiLCJhbGciOiJTSEEifQ==.eyJ1aWQiOiJhdXNlcmlkIiwidW5tIjoiYXVzZXJuYW1lIiwicG1zIjpbeyJybG4iOiJHVUVTVCIsInBpZCI6ImFwcm9qZWN0aWQwIn0seyJybG4iOiJHVUVTVCIsInBpZCI6ImFwcm9qZWN0aWQxIn1dLCJpc3MiOiJcXFFodHRwOi8vbG9jYWxob3N0OjgwODBcXEUiLCJwZnAiOiJhcGZwIiwiZXhwIjoxNTcyMjkxMTY1MjYxLCJpYXQiOjE1NzIyODc1NjUyNjEsImp0aSI6IjhhY2U5NDg2LWIyZjItNDBhOS04YjlmLWMxZDhiNTU2NDdmZiJ9.Pz8_bz8_Pz94Chw_aD84RWRObz4=");
        Assert.assertEquals(TokenValidationResponse.EXPIRED, handler.validateToken(token));
    }

    @Test
    public void blacklisted() throws NoSuchAlgorithmException {
        TokenHandler<AuthObject> handler = getTokenHandler();
        IToken token = getToken();
        Assert.assertEquals(TokenValidationResponse.GOOD, handler.validateToken(token));
        ITokenBlackList bl = TokenBlackList.getInstance();
        bl.addToBlackList(new TokenExpiration("8ace9486-b2f2-40a9-8b9f-c1d8b55647ff", 0));
        Assert.assertEquals(TokenValidationResponse.BLACKLISTED, handler.validateToken(token));
        bl.removeExpired();
    }

    @Test
    public void forged() throws NoSuchAlgorithmException, NoSuchFieldException, IllegalAccessException {
        TokenHandler<AuthObject> handler = getTokenHandler();
        IToken token = getToken();
        Assert.assertEquals(TokenValidationResponse.GOOD, handler.validateToken(token));
        Field signature = Token.class.getDeclaredField("signature");
        signature.setAccessible(true);
        signature.set(token, signature.get(token) + "a");
        Assert.assertEquals(TokenValidationResponse.FORGED, handler.validateToken(token));
    }
}
