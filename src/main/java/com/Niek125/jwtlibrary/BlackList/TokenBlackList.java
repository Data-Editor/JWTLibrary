package com.Niek125.jwtlibrary.BlackList;

import java.util.ArrayList;
import java.util.List;

public class TokenBlackList implements ITokenBlackList {
    private static TokenBlackList instance;
    private long maxDelay;
    private List<ITokenExpiration> jtis;

    private TokenBlackList(long maxDelay) {
        jtis = new ArrayList<>();
        this.maxDelay = maxDelay;
    }

    public void initialize(long maxDelay, List<ITokenExpiration> expiredTokens) {
        if(jtis.size() == 0){
            this.maxDelay = maxDelay;
            jtis = expiredTokens;
        }
    }

    public static TokenBlackList getInstance() {
        if (instance == null) {
            instance = new TokenBlackList(0);
        }
        return instance;
    }

    @Override
    public void removeExpired() {
        for (int i = 0; i < jtis.size(); i++) {
            if (jtis.get(i).getExp() - maxDelay < System.currentTimeMillis()) {
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
