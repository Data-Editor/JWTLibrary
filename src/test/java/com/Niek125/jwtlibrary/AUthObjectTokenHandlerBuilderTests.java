package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.AuthObjectMaker.AuthObject;
import com.Niek125.jwtlibrary.BlackList.ITokenExpiration;
import com.Niek125.jwtlibrary.BlackList.TokenBlackList;
import com.Niek125.jwtlibrary.BlackList.TokenExpiration;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.ExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.IExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.JWTKey;
import com.Niek125.jwtlibrary.SignatureReplicator.SignatureReplicator;
import com.Niek125.jwtlibrary.TokenHandler.TokenHandler;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class AUthObjectTokenHandlerBuilderTests {
    @Test
    public void buildTest() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        AuthObjectTokenHandlerBuilder builder = new AuthObjectTokenHandlerBuilder();
        List<ITokenExpiration> tokens = new ArrayList<>();
        tokens.add(new TokenExpiration("banned", 12345));
        List<IExpiringKey> keys = new ArrayList<>();
        keys.add(new ExpiringKey("failed", System.currentTimeMillis() - 100));
        keys.add(new ExpiringKey("isnowlonger", System.currentTimeMillis() + 1000));
        builder.configure(300, tokens, "testkey", keys, 1000);
        TokenHandler<AuthObject> handler = builder.getTokenHandler();
        Assert.assertNotNull(handler);
        Field fBlackList = TokenHandler.class.getDeclaredField("blackList");
        fBlackList.setAccessible(true);
        Object blackList = fBlackList.get(handler);
        Field fList = TokenBlackList.class.getDeclaredField("jtis");
        fList.setAccessible(true);
        Object list = fList.get(blackList);
        Thread.sleep(1000);
        Field fSigRep = TokenHandler.class.getDeclaredField("sigRep");
        fSigRep.setAccessible(true);
        Object sigRep = fSigRep.get(handler);
        Field fKey = SignatureReplicator.class.getDeclaredField("key");
        fKey.setAccessible(true);
        Object key = fKey.get(sigRep);
        Field fEKeys = JWTKey.class.getDeclaredField("expiringKeys");
        fEKeys.setAccessible(true);
        Object eKeys = fEKeys.get(key);
        Assert.assertEquals(0, ((List<TokenExpiration>)list).size());
        Assert.assertEquals(1, ((List<ExpiringKey>)eKeys).size());
    }
}
