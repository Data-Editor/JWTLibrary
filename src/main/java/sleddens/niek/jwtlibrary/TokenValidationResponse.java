package sleddens.niek.jwtlibrary;

public enum TokenValidationResponse {
    GOOD,
    EXPIRED,
    BLACKLISTED,
    FORGED
}
