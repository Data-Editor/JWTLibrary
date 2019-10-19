package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.AuthObjectMaker.AuthObject;
import com.Niek125.jwtlibrary.AuthObjectMaker.AuthObjectMaker;
import com.Niek125.jwtlibrary.AuthObjectMaker.IAuthObjectMaker;
import com.Niek125.jwtlibrary.BlackList.ITokenBlackList;
import com.Niek125.jwtlibrary.BlackList.ITokenExpiration;
import com.Niek125.jwtlibrary.BlackList.TokenBlackList;
import com.Niek125.jwtlibrary.SignatureReplicator.ISignatureReplicator;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.IConfigKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.IExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.JWTKey;
import com.Niek125.jwtlibrary.SignatureReplicator.SignatureReplicator;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class AuthObjectTokenHandlerBuilder extends TokenHandlerBuilder<AuthObject> {
    public void configure(List<ITokenExpiration> expiredTokens, String nonExpiringKey, List<IExpiringKey> expiringKeys) {
        JWTKey key = JWTKey.getInstance();
        key.initialize(nonExpiringKey, expiringKeys);
        TokenBlackList blackList = TokenBlackList.getInstance();
        blackList.initialize(expiredTokens);
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        Runnable task = () -> {
            blackList.removeExpired();
        };
    }

    @Override
    ISignatureReplicator getSigRep() {
        return new SignatureReplicator(JWTKey.getInstance());
    }

    @Override
    IAuthObjectMaker<AuthObject> getAuthMaker() {
        return new AuthObjectMaker();
    }

    @Override
    public ITokenBlackList getBlackList() {
        return TokenBlackList.getInstance();
    }

    public IConfigKey getKey() {
        return JWTKey.getInstance();
    }
}
