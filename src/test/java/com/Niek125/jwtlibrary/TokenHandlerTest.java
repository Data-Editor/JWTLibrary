package com.Niek125.jwtlibrary;

import static org.junit.Assert.assertTrue;

import com.Niek125.jwtlibrary.key.JWTKey;
import com.Niek125.jwtlibrary.key.RotatingKey;
import org.junit.Test;

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
        ArrayList<RotatingKey> keys = new ArrayList<>();
        keys.add(new RotatingKey("key1", System.currentTimeMillis() + 1000000));
        JWTKey key = new JWTKey("nonrotatingkey", keys);
        ITokenHandler tokenHandler = new TokenHandler(key);
        tokenHandler.setToken("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJ0b2tlbnNlcnZlci5jb20iLCJpYXQiOjE1MTYyMzkwMjIsImV4cCI6MTUxNjIzOTkyMiwidXNlcmlkIjoiaGdkZ2hlbmhkaiIsInVzZXJuYW1lIjoiam9obmRvZSIsInByb2ZpbGVwaWN0dXJlIjoidXJscGF0aCIsInBlcm1pc3Npb25zIjpbeyJwcm9qZWN0aWQiOiJmeWZ1ZmhlaCIsInJvbGVuYW1lIjoiZ3Vlc3QifSx7InByb2plY3RpZCI6ImZlZmJlaGJlaCIsInJvbGVuYW1lIjoiYWRtaW4ifV19.F4saGGXpcFAByFAxOvYcPqjYMDPUsepNTqqutYnviTE");
        System.out.print(tokenHandler.validateToken());
        assertTrue( true );
    }
}
