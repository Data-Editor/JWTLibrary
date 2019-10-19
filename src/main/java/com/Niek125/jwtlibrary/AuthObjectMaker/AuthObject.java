package com.Niek125.jwtlibrary.AuthObjectMaker;

import java.util.Dictionary;
import java.util.Enumeration;

public class AuthObject {
    private String userID;
    private String userName;
    private String profilePicture;
    private Dictionary<String, Role> permissions;

    AuthObject(String userID, String userName, String profilePicture, Dictionary<String, Role> permissions) {
        this.userID = userID;
        this.userName = userName;
        this.profilePicture = profilePicture;
        this.permissions = permissions;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName() {
        return userName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public Role getRole(String projectID) {
        return permissions.get(projectID);
    }

    public Enumeration<String> getProjects() {
        return permissions.keys();
    }
}
