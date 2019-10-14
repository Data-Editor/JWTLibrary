package sleddens.niek.jwtlibrary;

import java.util.Dictionary;
import java.util.Enumeration;

public class UserPermissions {
    private String userID;
    private String userName;
    private Dictionary<String, Role> permissions;

    UserPermissions(String userID, String userName, Dictionary<String, Role> permissions) {
        this.userID = userID;
        this.userName = userName;
        this.permissions = permissions;
    }

    public String getUserID() {
        return userID;
    }

    public String getUserName(){
        return userName;
    }

    public Role getRole(String projectID){
        return permissions.get(projectID);
    }

    public Enumeration<String> getProjects(){
        return permissions.keys();
    }
}
