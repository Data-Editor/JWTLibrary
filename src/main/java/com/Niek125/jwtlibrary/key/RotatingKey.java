package com.Niek125.jwtlibrary.key;

public class RotatingKey {
    private String key;
    private long expiryTime;

    public RotatingKey(String key, long expiryTime){
        this.key = key;
        this.expiryTime = expiryTime;
    }

    public String getKey(){
        return key;
    }

    public long getExpiryTime() {
        return expiryTime;
    }
}
