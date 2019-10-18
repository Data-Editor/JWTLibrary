package com.Niek125.jwtlibrary.BlackList;

public interface ITokenBlackList {
    void removeExpired();
    void addToBlackList(String jti, long exp);
    boolean isBlacklisted(String jti);
}
