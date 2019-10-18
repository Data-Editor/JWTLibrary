package com.Niek125.jwtlibrary;

import static org.junit.Assert.assertTrue;

import com.Niek125.jwtlibrary.BlackList.TokenBlackList;
import com.Niek125.jwtlibrary.SignatureReplicator.ISignatureReplicator;
import com.Niek125.jwtlibrary.SignatureReplicator.SignatureReplicator;
import com.Niek125.jwtlibrary.Token.Token;
import com.Niek125.jwtlibrary.key.IChangingKey;
import com.Niek125.jwtlibrary.key.JWTKey;
import com.Niek125.jwtlibrary.key.ChangingKey;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

/**
 * Unit test for simple App.
 */
public class TokenHandlerTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        ArrayList<IChangingKey> keys = new ArrayList<>();
        keys.add(new ChangingKey("isnowlonger", System.currentTimeMillis() + (1000 * 60 * 61)));
        JWTKey.initialize("testkey", keys);
        JWTKey key = JWTKey.getInstance();
        ISignatureReplicator sigRep = new SignatureReplicator(key);
        ITokenHandler tokenHandler = new TokenHandler(TokenBlackList.getInstance(), sigRep);
        URL obj = null;
        URLConnection conn = null;
        try {
            obj = new URL("http://localhost:8080/Token/GetToken");
            conn = obj.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print(tokenHandler.validateToken(new Token(conn.getHeaderField("tkn"))));
        assertTrue( true );
    }
}
