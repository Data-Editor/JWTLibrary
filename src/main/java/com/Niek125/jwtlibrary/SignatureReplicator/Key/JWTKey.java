package com.Niek125.jwtlibrary.SignatureReplicator.Key;

import java.util.ArrayList;
import java.util.List;

public class JWTKey implements IConfigKey {
    private static JWTKey instance;
    private String nonExpiringKey;
    private List<IExpiringKey> expiringKeys;

    private JWTKey(String nonRotatingKey, List<IExpiringKey> rotatingKeys) {
        this.nonExpiringKey = nonRotatingKey;
        this.expiringKeys = rotatingKeys;
    }

    public void initialize(String nonExpiringKey, List<IExpiringKey> expiringKeys) {
        if(this.nonExpiringKey.equals("defaultKey") && this.expiringKeys.size() == 0){
            this.nonExpiringKey = nonExpiringKey;
            this.expiringKeys = expiringKeys;
        }
    }

    public static JWTKey getInstance() {
        if (instance == null) {
            instance = new JWTKey("defaultKey", new ArrayList<>());
        }
        return instance;
    }

    @Override
    public String getKey(long expiryTime) {
        int i;
        for (i = 0; i < expiringKeys.size(); i++) {
            if(expiryTime < expiringKeys.get(i).getExpiryTime()){
                break;
            }
        }
        return nonExpiringKey + expiringKeys.get(i).getKey();
    }

    @Override
    public void addKey(IExpiringKey key) {
        expiringKeys.remove(0);
        expiringKeys.add(key);
    }

    @Override
    public void removeExpiredKeys() {
        for (int i = 0; i < expiringKeys.size(); i++) {
            if (expiringKeys.get(i).getExpiryTime() < System.currentTimeMillis()) {
                expiringKeys.remove(i);
            }
        }
    }
}
