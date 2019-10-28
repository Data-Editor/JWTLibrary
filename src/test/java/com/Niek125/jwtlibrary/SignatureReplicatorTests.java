package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.SignatureReplicator.ISignatureReplicator;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.ExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.IExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.JWTKey;
import com.Niek125.jwtlibrary.SignatureReplicator.SignatureReplicator;
import com.Niek125.jwtlibrary.Token.IToken;
import com.Niek125.jwtlibrary.Token.Token;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class SignatureReplicatorTests {
    @Test
    public void ForgeTest(){
        JWTKey k = JWTKey.getInstance();
        List<IExpiringKey> keys = new ArrayList<>();
        keys.add(new ExpiringKey("failed", Long.parseLong("1571826207792") - 100));
        keys.add(new ExpiringKey("isnowlonger", Long.parseLong("1571826207792") + 1000));
        k.initialize("testkey", keys);
        ISignatureReplicator r = new SignatureReplicator(k);
        IToken t = new Token("eyJ0eXAiOiJKV1QiLCJhbGciOiJTSEEtMjI0In0=.eyJ1aWQiOiJhdXNlcmlkIiwidW5tIjoiYXVzZXJuYW1lIiwicG1zIjpbeyJybG4iOiJHVUVTVCIsInBpZCI6ImFwcm9qZWN0aWQwIn0seyJybG4iOiJHVUVTVCIsInBpZCI6ImFwcm9qZWN0aWQxIn1dLCJpc3MiOiJcXFFodHRwOi8vbG9jYWxob3N0OjgwODBcXEUiLCJwZnAiOiJhcGZwIiwiZXhwIjoxNTcxODI2MjA3NzkyLCJpYXQiOjE1NzE4MjI2MDc3OTIsImp0aSI6IjQ1ZjI4NzBkLTIwMmYtNDM3YS04YjFiLWZmMzcwN2MzNzE0MCJ9.P1JoKT8LQj8_fVE_Pz8_Pz9tDD8-P3E_Pw==");
        Assert.assertFalse(r.isForged(t, "SHA-224", Long.parseLong("1571826207792")));
    }
}
