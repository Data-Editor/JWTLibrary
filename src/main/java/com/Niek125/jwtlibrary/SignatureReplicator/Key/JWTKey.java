package com.Niek125.jwtlibrary.SignatureReplicator.Key;

import java.util.List;

public class JWTKey implements IConfigKey {
    private static JWTKey instance;
    private String nonRotatingKey;
    private List<IExpiringKey> rotatingKeys;

    private JWTKey(String nonRotatingKey, List<IExpiringKey> rotatingKeys) {
        this.nonRotatingKey = nonRotatingKey;
        this.rotatingKeys = rotatingKeys;
    }

    public void initialize(String nonExpiringKey, List<IExpiringKey> expiringKeys) {
        if (instance == null) {
            instance = new JWTKey(nonRotatingKey, rotatingKeys);
        }
    }

    public static JWTKey getInstance() {
        return instance;
    }

    @Override
    public String getKey(long expiryTime) {

        int i = 0;
        try {
            while (expiryTime < rotatingKeys.get(i).getExpiryTime()) {
                i++;
            }
        } catch (IndexOutOfBoundsException e) {//this is not an error :) it's meant to be means has seen all keys
        }
        return nonRotatingKey + rotatingKeys.get(i - 1).getKey();
    }

    @Override
    public void addKey(IExpiringKey key) {
        rotatingKeys.remove(0);
        rotatingKeys.add(key);
    }
}
