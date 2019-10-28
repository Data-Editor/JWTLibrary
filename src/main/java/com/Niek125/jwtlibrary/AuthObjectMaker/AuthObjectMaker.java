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
            JsonPath.parse(payload).read("$.pms.length()");
            for (int i = 0; i < (int)JsonPath.parse(payload).read("$.pms.length()"); i++) {
                permissions.put(JsonPath.parse(payload).read("$.pms[" + i + "].pid"), Role.valueOf(JsonPath.parse(payload).read("$.pms[" + i + "].rln")));
            }
        } catch (Exception e) {//missing either pid or rln
        }
        authObject = new AuthObject(
                JsonPath.parse(payload).read("$.uid"),
                JsonPath.parse(payload).read("$.unm"),
                JsonPath.parse(payload).read("$.pfp"),
                permissions
        );
    }
}
