package com.Niek125.jwtlibrary.TokenHandler;

public enum TokenValidationResponse {
    GOOD,
    BLACKLISTED,
    EXPIRED,
    FORGED,
    NO_JWT
}
