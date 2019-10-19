package com.Niek125.jwtlibrary.SignatureReplicator;

import com.Niek125.jwtlibrary.SignatureReplicator.Key.IConfigKey;
import com.Niek125.jwtlibrary.Token.IToken;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class SignatureReplicator implements ISignatureReplicator {
    private IConfigKey key;

    public SignatureReplicator(IConfigKey key){
        this.key = key;
    }

    @Override
    public boolean isForged(IToken token, String alg, long exp){
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(alg);
        } catch (NoSuchAlgorithmException e) {
            return true;
        }
        digest.update(key.getKey(exp).getBytes());
        String repSig = Base64.getUrlEncoder().encodeToString(
                new String(
                        digest.digest(
                                (token.getHeader() + token.getPayload()).getBytes(StandardCharsets.ISO_8859_1))
                ).getBytes(StandardCharsets.ISO_8859_1));
        return !repSig.equals(token.getSignature());
    }
}
