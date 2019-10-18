package com.Niek125.jwtlibrary.BlackList;

import java.util.ArrayList;
import java.util.List;

public class TokenBlackList implements ITokenBlackList {
    private static TokenBlackList instance;
    private List<String> jtis;
    private List<Long> exps;

    private  TokenBlackList(){
        jtis = new ArrayList<>();
        exps = new ArrayList<>();
    }

    public static ITokenBlackList getInstance(){
        if(instance == null){
            instance = new TokenBlackList();
        }
        return instance;
    }

    @Override
    public void removeExpired() {
        for(int i = 0; i < exps.size(); i++){
            if(exps.get(i) < System.currentTimeMillis()){
                jtis.remove(i);
                exps.remove(i);
            }
        }
    }

    @Override
    public void addToBlackList(String jti, long exp) {
        jtis.add(jti);
        exps.add(exp);
    }

    @Override
    public boolean isBlacklisted(String jti) {
        return jtis.contains(jti);
    }
}
