package com.Niek125.jwtlibrary.key;

public interface IConfigKey {
    String getKey(long expiryTime);
    void addKey(IChangingKey key);
}
