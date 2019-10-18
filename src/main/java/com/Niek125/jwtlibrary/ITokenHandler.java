package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.Token.IToken;

public interface ITokenHandler<T> {
    TokenValidationResponse validateToken(IToken token);
    T getAuthObject();
}
