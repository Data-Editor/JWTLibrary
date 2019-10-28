package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.BlackList.ITokenBlackList;
import com.Niek125.jwtlibrary.BlackList.ITokenExpiration;
import com.Niek125.jwtlibrary.BlackList.TokenBlackList;
import com.Niek125.jwtlibrary.BlackList.TokenExpiration;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.JWTKey;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class TokenBlackListTests {

    @Test
    public void InstanceTest(){
        ITokenBlackList t1 = TokenBlackList.getInstance();
        ITokenBlackList t2 = TokenBlackList.getInstance();
        Assert.assertEquals(t1, t2);
    }

    @Test
    public void BlackListingTest() throws NoSuchFieldException, IllegalAccessException {
        TokenBlackList t = TokenBlackList.getInstance();
        List<ITokenExpiration> tokens = new ArrayList<>();
        tokens.add(new TokenExpiration("banned", 12345));
        Field instance = JWTKey.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(t, null);
        t.initialize(tokens);
        Assert.assertTrue(t.isBlacklisted("banned"));
        Assert.assertFalse(t.isBlacklisted("valid"));
        t.addToBlackList(new TokenExpiration("valid", 12345));
        Assert.assertTrue(t.isBlacklisted("valid"));
    }

    @Test
    public void RemoveExpiredTest(){
        TokenBlackList t = TokenBlackList.getInstance();
        t.addToBlackList(new TokenExpiration("removed", System.currentTimeMillis() - 100));
        t.addToBlackList(new TokenExpiration("stayed", System.currentTimeMillis() + 1000));
        Assert.assertTrue(t.isBlacklisted("removed"));
        Assert.assertTrue(t.isBlacklisted("stayed"));
        t.removeExpired();
        Assert.assertTrue(t.isBlacklisted("stayed"));
        Assert.assertFalse(t.isBlacklisted("removed"));
    }

    @Test
    public void DoubleInitializingTest() throws NoSuchFieldException, IllegalAccessException {
        TokenBlackList t = TokenBlackList.getInstance();
        List<ITokenExpiration> tokens = new ArrayList<>();
        tokens.add(new TokenExpiration("banned", 12345));
        Field instance = JWTKey.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(t, null);
        t.initialize(tokens);
        t.initialize(new ArrayList<>());
        Assert.assertTrue(t.isBlacklisted("banned"));
    }
}
