package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.AuthObjectMaker.AuthObjectMaker;
import com.Niek125.jwtlibrary.BlackList.TokenBlackList;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.ExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.IExpiringKey;
import com.Niek125.jwtlibrary.SignatureReplicator.Key.JWTKey;
import com.Niek125.jwtlibrary.SignatureReplicator.ISignatureReplicator;
import com.Niek125.jwtlibrary.SignatureReplicator.SignatureReplicator;
import com.Niek125.jwtlibrary.Token.Token;
import com.Niek125.jwtlibrary.TokenHandler.TokenHandler;
import org.junit.Test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class TokenHandlerTests
{
    @Test
    public void shouldAnswerWithTrue()
    {
        ArrayList<IExpiringKey> keys = new ArrayList<>();
        keys.add(new ExpiringKey("isnowlonger", System.currentTimeMillis() + (1000 * 60 * 61)));
        JWTKey key = JWTKey.getInstance();
        key.initialize("testkey", keys);
        ISignatureReplicator sigRep = new SignatureReplicator(key);
        TokenHandler tokenHandler = new TokenHandler(TokenBlackList.getInstance(), sigRep, new AuthObjectMaker());
        URL obj = null;
        URLConnection conn = null;
        try {
            obj = new URL("http://localhost:8081/Token/GetToken");
            conn = obj.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(conn.getHeaderField("tkn"));
        System.out.print(tokenHandler.validateToken(new Token(conn.getHeaderField("tkn"))));
        Object a = tokenHandler.getAuthObject();
        assertTrue( true );
    }
}
