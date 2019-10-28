package com.Niek125.jwtlibrary.BlackList;

import java.util.ArrayList;
import java.util.List;

public class TokenBlackList implements ITokenBlackList {
    private static TokenBlackList instance;
    private List<ITokenExpiration> jtis;

    private TokenBlackList() {
        jtis = new ArrayList<>();
    }

    public void initialize(List<ITokenExpiration> expiredTokens) {
        if(jtis.size() == 0){
            jtis = expiredTokens;
        }
    }

    public static TokenBlackList getInstance() {
        if (instance == null) {
            instance = new TokenBlackList();
        }
        return instance;
    }

    @Override
    public void removeExpired() {
        for (int i = 0; i < jtis.size(); i++) {
            if (jtis.get(i).getExp() < System.currentTimeMillis()) {
                jtis.remove(i);
            }
        }
    }

    @Override
    public void addToBlackList(ITokenExpiration t) {
        jtis.add(t);
    }

    @Override
    public boolean isBlacklisted(String jti) {
        return jtis.stream().filter( t -> t.getJti().equals(jti)).findFirst().isPresent();
    }
}
