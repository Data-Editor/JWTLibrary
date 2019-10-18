package com.Niek125.jwtlibrary;


import com.Niek125.jwtlibrary.AuthObjectMaker.IAuthObjectMaker;
import com.Niek125.jwtlibrary.BlackList.ITokenBlackList;
import com.Niek125.jwtlibrary.SignatureReplicator.ISignatureReplicator;
import com.Niek125.jwtlibrary.Token.IToken;
import com.jayway.jsonpath.JsonPath;

public class TokenHandler<T> implements ITokenHandler<T> {
    private ITokenBlackList blackList;
    private ISignatureReplicator sigRep;
    private IAuthObjectMaker<T> authMaker;

    TokenHandler(ITokenBlackList blacklist, ISignatureReplicator sigRep, IAuthObjectMaker<T> authMaker) {
        this.blackList = blacklist;
        this.sigRep = sigRep;
        this.authMaker = authMaker;
    }

    @Override
    public TokenValidationResponse validateToken(IToken token) {
        try {
            if (!JsonPath.parse(token.getHeader()).read("$.typ").equals("JWT")) {
                return TokenValidationResponse.NO_JWT;//not a jwt
            }
            if (blackList.isBlacklisted(JsonPath.parse(token.getPayload()).read("$.jti"))) {
                return TokenValidationResponse.BLACKLISTED;//blacklisted
            }
            if (Long.parseLong(JsonPath.parse(token.getPayload()).read("$.exp").toString()) + 300 < System.currentTimeMillis()) {
                return TokenValidationResponse.EXPIRED;//expired
            }
            if (sigRep.isForged(token, JsonPath.parse(token.getHeader()).read("$.alg"), JsonPath.parse(token.getPayload()).read("$.exp"))) {
                return TokenValidationResponse.FORGED;//forged
            }
            authMaker.makeAuthObject(token.getPayload());
        } catch (Exception e) {//not the right format
            return TokenValidationResponse.FORGED;
        }
        return TokenValidationResponse.GOOD;
    }

    @Override
    public T getAuthObject() {
        return authMaker.getAuthObject();
    }
}
