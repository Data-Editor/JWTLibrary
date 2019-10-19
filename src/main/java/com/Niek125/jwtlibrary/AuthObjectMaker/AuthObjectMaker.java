package com.Niek125.jwtlibrary.AuthObjectMaker;

import com.jayway.jsonpath.JsonPath;

import java.util.Dictionary;
import java.util.Hashtable;

public class AuthObjectMaker implements IAuthObjectMaker<AuthObject> {
    private AuthObject authObject;

    @Override
    public AuthObject getAuthObject() {
        return authObject;
    }

    @Override
    public void makeAuthObject(String payload) {
        Dictionary<String, Role> permissions = new Hashtable<>();
        try {
            int i = 0;
            while (true) {
                permissions.put(JsonPath.parse(payload).read("$.pms[" + i + "].pid"), Role.valueOf(JsonPath.parse(payload).read("$.pms[" + i + "].rln")));
                i++;
            }
        } catch (Exception e) {//this exception is meant to be means there are no more permissions or found a one of both
        }
        authObject = new AuthObject(
                JsonPath.parse(payload).read("$.uid"),
                JsonPath.parse(payload).read("$.unm"),
                JsonPath.parse(payload).read("$.pfp"),
                permissions
        );
    }
}
