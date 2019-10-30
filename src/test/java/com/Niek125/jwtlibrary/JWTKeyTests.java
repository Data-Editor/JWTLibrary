package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.SignatureReplicator.Key.ExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.IConfigKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.IExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.JWTKey;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class JWTKeyTests {
    @Test
    public void InstanceTest(){
        IConfigKey k1 = JWTKey.getInstance();
        IConfigKey k2 = JWTKey.getInstance();
        Assert.assertEquals(k1, k2);
    }

    @Test
    public void KeyTest() throws NoSuchFieldException, IllegalAccessException {
        JWTKey k = JWTKey.getInstance();
        List<IExpiringKey> keys = new ArrayList<>();
        keys.add(new ExpiringKey("failed", System.currentTimeMillis() - 100));
        keys.add(new ExpiringKey("test", System.currentTimeMillis() + 1000));
        Field instance = JWTKey.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(k, null);
        k.initialize("akey", keys);
        Assert.assertEquals("akeytest", k.getKey(System.currentTimeMillis()));
    }

    @Test
    public void RemoveExpiredKeysTest() throws InterruptedException, NoSuchFieldException, IllegalAccessException {
        JWTKey k = JWTKey.getInstance();
        List<IExpiringKey> keys = new ArrayList<>();
        keys.add(new ExpiringKey("failed", System.currentTimeMillis() + 100));
        keys.add(new ExpiringKey("test", System.currentTimeMillis() + 1000));
        Field instance = JWTKey.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(k, null);
        k.initialize("akey", keys);
        Assert.assertEquals("akeyfailed", k.getKey(System.currentTimeMillis()));
        Thread.sleep(200);
        k.removeExpiredKeys();
        Assert.assertEquals("akeytest", k.getKey(System.currentTimeMillis()));
    }

    @Test
    public void DoubleInitializingTest() throws NoSuchFieldException, IllegalAccessException, InterruptedException {
        JWTKey k = JWTKey.getInstance();
        List<IExpiringKey> keys = new ArrayList<>();
        keys.add(new ExpiringKey("failed", System.currentTimeMillis() - 100));
        keys.add(new ExpiringKey("test", System.currentTimeMillis() + 1000));
        Field instance = JWTKey.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(k, null);
        Field fKeys = JWTKey.class.getDeclaredField("expiringKeys");
        fKeys.setAccessible(true);
        fKeys.set(k, new ArrayList<>());
        Field fkey = JWTKey.class.getDeclaredField("nonExpiringKey");
        fkey.setAccessible(true);
        fkey.set(k, "defaultKey");
        k.initialize("akey", keys);
        k.initialize("failed", new ArrayList<>());
        Assert.assertEquals("akeytest", k.getKey(System.currentTimeMillis()));
    }
}
