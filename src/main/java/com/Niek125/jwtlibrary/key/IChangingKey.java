package com.Niek125.jwtlibrary.key;

public interface IChangingKey {
    String getKey();
    long getExpiryTime();
}
