package com.Niek125.jwtlibrary.BlackList;

import java.util.ArrayList;
import java.util.List;

public class TokenBlackList implements ITokenBlackList {
    private static TokenBlackList instance;
    private List<ITokenExpiration> jtis;

    private TokenBlackList(List<ITokenExpiration> expiredTokens) {
        jtis = new ArrayList<>();
    }

    public void initialize(List<ITokenExpiration> expiredTokens) {
        if (instance == null) {
            instance = new TokenBlackList(expiredTokens);
        }
    }

    public static TokenBlackList getInstance() {
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
        return jtis.contains(jti);
    }
}
