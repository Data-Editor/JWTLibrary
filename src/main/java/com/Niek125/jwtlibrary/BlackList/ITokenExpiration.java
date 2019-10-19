package com.Niek125.jwtlibrary.BlackList;

public interface ITokenExpiration {
    String getJti();

    long getExp();
}
