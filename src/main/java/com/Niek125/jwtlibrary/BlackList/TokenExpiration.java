package com.Niek125.jwtlibrary.BlackList;

public class TokenExpiration implements ITokenExpiration {
    private String jti;
    private long exp;

    public TokenExpiration(String jti, long exp){
        this.jti = jti;
        this.exp = exp;
    }

    @Override
    public String getJti() {
        return  jti;
    }

    @Override
    public long getExp() {
        return exp;
    }
}
