package com.Niek125.jwtlibrary.key;

public class ChangingKey implements IChangingKey{
    private String key;
    private long expiryTime;

    public ChangingKey(String key, long expiryTime){
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
