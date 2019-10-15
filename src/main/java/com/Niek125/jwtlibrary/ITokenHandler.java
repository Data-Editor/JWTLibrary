package com.Niek125.jwtlibrary;

public interface ITokenHandler {
    void setToken(String token);
    TokenValidationResponse validateToken();
    UserPermissions getPayload();
}
