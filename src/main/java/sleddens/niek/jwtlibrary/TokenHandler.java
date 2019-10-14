package sleddens.niek.jwtlibrary;

public class TokenHandler implements ITokenHandler
{
    private String token;

    TokenHandler(){
        token = "";
    }

    @Override
    public void setToken(String token) {

    }

    @Override
    public TokenValidationResponse validateToken() {
        return null;
    }

    @Override
    public UserPermissions getPayload() {
        return null;
    }
}
