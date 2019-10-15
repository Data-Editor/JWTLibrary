package com.Niek125.jwtlibrary.key;

import java.util.List;

public class JWTKey {
    private String nonRotatingKey;
    private List<RotatingKey> rotatingKeys;

    public JWTKey(String nonRotatingKey, List<RotatingKey> rotatingKeys){
        this.nonRotatingKey = nonRotatingKey;
        this.rotatingKeys = rotatingKeys;
    }

    public String getKey(long expiryTime){
        int i = 0;
        try {
            while (expiryTime < rotatingKeys.get(i).getExpiryTime()){
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return nonRotatingKey + rotatingKeys.get(i - 1).getKey();
    }

    public void addKey(RotatingKey key){
        rotatingKeys.remove(0);
        rotatingKeys.add(key);
    }
}
