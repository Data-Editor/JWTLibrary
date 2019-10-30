package com.Niek125.jwtlibrary.TokenHandler;


import com.Niek125.jwtlibrary.AuthObjectMaker.IAuthObjectMaker;
import com.Niek125.jwtlibrary.BlackList.ITokenBlackList;
import com.Niek125.jwtlibrary.SignatureReplicator.ISignatureReplicator;
import com.Niek125.jwtlibrary.Token.IToken;
import com.jayway.jsonpath.JsonPath;

public class TokenHandler<T> {
    private long maxDelay;
    private ITokenBlackList blackList;
    private ISignatureReplicator sigRep;
    private IAuthObjectMaker<T> authMaker;

    public TokenHandler(long maxDelay, ITokenBlackList blacklist, ISignatureReplicator sigRep, IAuthObjectMaker<T> authMaker) {
        this.maxDelay = maxDelay;
        this.blackList = blacklist;
        this.sigRep = sigRep;
        this.authMaker = authMaker;
    }

    public TokenValidationResponse validateToken(IToken token) {
        try {
            if (!JsonPath.parse(token.getHeader()).read("$.typ").equals("JWT")) {
                return TokenValidationResponse.NO_JWT;
            }
            System.out.println(TokenValidationResponse.NO_JWT);
            if (Long.parseLong(JsonPath.parse(token.getPayload()).read("$.exp").toString()) - maxDelay < System.currentTimeMillis()) {
                return TokenValidationResponse.EXPIRED;
            }
            System.out.println(TokenValidationResponse.EXPIRED);
            if (blackList.isBlacklisted(JsonPath.parse(token.getPayload()).read("$.jti"))) {
                return TokenValidationResponse.BLACKLISTED;
            }
            System.out.println(TokenValidationResponse.BLACKLISTED);
            if (sigRep.isForged(token, JsonPath.parse(token.getHeader()).read("$.alg"), JsonPath.parse(token.getPayload()).read("$.exp"))) {
                return TokenValidationResponse.FORGED;
            }
            System.out.println(TokenValidationResponse.FORGED);
            authMaker.makeAuthObject(token.getPayload());
        } catch (Exception e) {//not the right format
            System.out.println("exception");
            return TokenValidationResponse.FORGED;
        }
        return TokenValidationResponse.GOOD;
    }

    public T getAuthObject() {
        return authMaker.getAuthObject();
    }
}
