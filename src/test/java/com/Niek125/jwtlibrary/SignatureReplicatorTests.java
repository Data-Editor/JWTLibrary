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
    public void ForgeTest() {
        String time = "1572287565261";
        JWTKey k = JWTKey.getInstance();
        List<IExpiringKey> keys = new ArrayList<>();
        keys.add(new ExpiringKey("failed", Long.parseLong(time) - 100));
        keys.add(new ExpiringKey("isnowlonger", Long.parseLong(time) + 1000));
        k.initialize("testkey", keys);
        ISignatureReplicator r = new SignatureReplicator(k);
        IToken t = new Token("eyJ0eXAiOiJKV1QiLCJhbGciOiJTSEEifQ==.eyJ1aWQiOiJhdXNlcmlkIiwidW5tIjoiYXVzZXJuYW1lIiwicG1zIjpbeyJybG4iOiJHVUVTVCIsInBpZCI6ImFwcm9qZWN0aWQwIn0seyJybG4iOiJHVUVTVCIsInBpZCI6ImFwcm9qZWN0aWQxIn1dLCJpc3MiOiJcXFFodHRwOi8vbG9jYWxob3N0OjgwODBcXEUiLCJwZnAiOiJhcGZwIiwiZXhwIjoxNTcyMjkxMTY1MjYxLCJpYXQiOjE1NzIyODc1NjUyNjEsImp0aSI6IjhhY2U5NDg2LWIyZjItNDBhOS04YjlmLWMxZDhiNTU2NDdmZiJ9.Pz8_bz8_Pz94Chw_aD84RWRObz4=");
        Assert.assertFalse(r.isForged(t, "SHA", Long.parseLong(time)));
    }
}
