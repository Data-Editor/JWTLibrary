package com.Niek125.jwtlibrary.SignatureReplicator.Key;

public class ExpiringKey implements IExpiringKey {
    private String key;
    private long expiryTime;

    public ExpiringKey(String key, long expiryTime) {
        this.key = key;
        this.expiryTime = expiryTime;
    }

    public String getKey() {
        return key;
    }

    public long getExpiryTime() {
        return expiryTime;
    }
}
