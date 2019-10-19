package com.Niek125.jwtlibrary.BlackList;

public interface ITokenBlackList {
    void removeExpired();

    void addToBlackList(ITokenExpiration t);

    boolean isBlacklisted(String jti);
}
