package sleddens.niek.jwtlibrary;

public interface ITokenHandler {
    void setToken(String token);
    TokenValidationResponse validateToken();
    UserPermissions getPayload();
}
