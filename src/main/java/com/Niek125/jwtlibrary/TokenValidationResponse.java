package com.Niek125.jwtlibrary;

public enum TokenValidationResponse {
    GOOD,
    BLACKLISTED,
    EXPIRED,
    FORGED,
    NO_JWT
}
