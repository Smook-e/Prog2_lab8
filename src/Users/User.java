package Users;

public class User {
    private String userID;
    private String password;
    private String userName;
    private String role;
    private String email;

    public User(){};
    public User(String userID, String password, String userName, String role, String email) {
        this.userID = userID;
        this.password = password;
        this.userName = userName;
        this.role = role;
        this.email = email;
    }
    @Override
    public String toString(){
        return userID + "," + userName + "," + password + "," + email + "," + role;
    }
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }




}
