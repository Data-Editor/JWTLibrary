package com.Niek125.jwtlibrary.SignatureReplicator.Key;

public interface IConfigKey {
    String getKey(long expiryTime);

    void addKey(IExpiringKey key);

    void removeExpiredKeys();
}
