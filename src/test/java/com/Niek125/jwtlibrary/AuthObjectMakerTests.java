package com.Niek125.jwtlibrary;

import com.Niek125.jwtlibrary.AuthObjectMaker.AuthObject;
import com.Niek125.jwtlibrary.AuthObjectMaker.AuthObjectMaker;
import com.Niek125.jwtlibrary.AuthObjectMaker.IAuthObjectMaker;
import com.Niek125.jwtlibrary.AuthObjectMaker.Role;
import org.junit.Assert;
import org.junit.Test;

import java.util.Enumeration;

public class AuthObjectMakerTests {
    @Test
    public void MakeAuthObjectTest(){
        String pay = "{\"uid\":\"auserid\",\"unm\":\"ausername\",\"pms\":[{\"rln\":\"GUEST\",\"pid\":\"aprojectid0\"},{\"rln\":\"GUEST\",\"pid\":\"aprojectid1\"}],\"iss\":\"http://localhost:8080\",\"pfp\":\"apfp\",\"exp\":1572291833977,\"iat\":1572288233977,\"jti\":\"c0626b67-4f10-4659-bc88-d260108a665d\"}";
        IAuthObjectMaker<AuthObject> maker = new AuthObjectMaker();
        maker.makeAuthObject(pay);
        AuthObject auth = maker.getAuthObject();
        Assert.assertTrue(auth.getUserID().equals("auserid"));
        Assert.assertTrue(auth.getUserName().equals("ausername"));
        Assert.assertTrue(auth.getProfilePicture().equals("apfp"));
        String[] projects = {"aprojectid1", "aprojectid0"};
        Enumeration<String> projs = auth.getProjects();
        for (int i = 0; i < projects.length; i++) {
            Assert.assertTrue(projects[i].equals(projs.nextElement()));
        }
        Assert.assertEquals(Role.GUEST,auth.getRole("aprojectid0"));
        Assert.assertEquals(Role.GUEST, auth.getRole("aprojectid1"));
    }
}
