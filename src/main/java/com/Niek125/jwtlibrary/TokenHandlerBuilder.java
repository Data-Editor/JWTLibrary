package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.AuthObjectMaker.IAuthObjectMaker;
import com.Niek125.jwtlibrary.BlackList.ITokenBlackList;
import com.Niek125.jwtlibrary.SignatureReplicator.ISignatureReplicator;
import com.Niek125.jwtlibrary.TokenHandler.TokenHandler;

public abstract class TokenHandlerBuilder<T> {
    TokenHandlerBuilder() {

    }

    public TokenHandler<T> getTokenHandler() {
        return new TokenHandler<>(
                getBlackList(),
                getSigRep(),
                getAuthMaker()
        );
    }

    abstract ITokenBlackList getBlackList();

    abstract ISignatureReplicator getSigRep();

    abstract IAuthObjectMaker<T> getAuthMaker();
}
