package com.Niek125.jwtlibrary.SignatureReplicator.Key;

public interface IExpiringKey {
    String getKey();

    long getExpiryTime();
}
