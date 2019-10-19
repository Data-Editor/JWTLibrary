package com.Niek125.jwtlibrary.Token;

public interface IToken {
    String getHeader();

    String getPayload();

    String getSignature();
}
