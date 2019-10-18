package com.Niek125.jwtlibrary.key;

import java.util.List;

public class JWTKey implements IConfigKey {
    private static JWTKey instance;
    private String nonRotatingKey;
    private List<IChangingKey> rotatingKeys;

    private JWTKey(String nonRotatingKey, List<IChangingKey> rotatingKeys){
        this.nonRotatingKey = nonRotatingKey;
        this.rotatingKeys = rotatingKeys;
    }

    public static void initialize(String nonRotatingKey, List<IChangingKey> rotatingKeys){
        if(instance == null){
            instance = new JWTKey(nonRotatingKey, rotatingKeys);
        }
    }

    public static JWTKey getInstance(){
        return instance;
    }

    @Override
    public String getKey(long expiryTime){

        int i = 0;
        try {
            while (expiryTime < rotatingKeys.get(i).getExpiryTime()){
                i++;
            }
        }catch (IndexOutOfBoundsException e){//this is not an error :) it's meant to be
        }
        return nonRotatingKey + rotatingKeys.get(i - 1).getKey();
    }

    @Override
    public void addKey(IChangingKey key){
        rotatingKeys.remove(0);
        rotatingKeys.add(key);
    }
}
