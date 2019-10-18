package com.Niek125.jwtlibrary.SignatureReplicator;

import com.Niek125.jwtlibrary.Token.IToken;

public interface ISignatureReplicator {
    boolean isForged(IToken token, String alg, long exp);
}
