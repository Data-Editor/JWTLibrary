package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.Token.IToken;
import com.Niek125.jwtlibrary.Token.Token;
import org.junit.Assert;
import org.junit.Test;

public class TokenTests {
    @Test
    public void InvalidLengthShorterTest(){
        boolean thrownException = false;
        try {
            IToken t = new Token("1.2");
        }catch (IllegalArgumentException e){
            thrownException = true;
        }
        Assert.assertTrue(thrownException);
    }

    @Test
    public void InvalidLengthLongerTest(){
        boolean thrownException = false;
        try {
            IToken t = new Token("1.2.3.4");
        }catch (IllegalArgumentException e){
            thrownException = true;
        }
        Assert.assertTrue(thrownException);
    }

    @Test
    public void ValidPropertiesTest(){
    }
}
