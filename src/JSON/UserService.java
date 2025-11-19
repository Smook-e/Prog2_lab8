package JSON;

import Users.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserService extends JsonDatabaseManager<User> {

    public UserService(String fileName) throws IOException {
        super(fileName, User.class);

    }


    public ArrayList<User> searchBYName(String name) {
        String lower = name.toLowerCase();
        List<User> found = db.stream()
                .filter(u -> u.getUserName().toLowerCase().contains(lower))
                .toList();

        if (found.isEmpty()) {
            return null;
        } else {
            return (ArrayList<User>) found;
        }
    }


    public User searchBYID(String ID) {
        for(User u : db) {
            if(u.getUserID().equals(ID)) {
                return u;
            }
        }
        return null;
    }
    public boolean deleteByID(String ID) {
        for(User u : db) {
            if(u.getUserID().equals(ID)) {
                db.remove(u);
                return true;
            }
        }
        return false;
    }
    public User getUserByID(String ID) {
        for(User u : db) {
            if(u.getUserID().equals(ID)) {
                return u;
            }
        }
        return null;

    }
    public User getUserByUsername(String username){

        for(User u : db) {
            if(u.getUserName().equalsIgnoreCase(username)) {
                return u;
            }
        }
        return null;
    }
    public boolean containsUsername(String username){
        return getUserByUsername(username) != null;
    }
    public boolean containsID(String ID){
        return getUserByID(ID) != null;
    }
    public boolean addUser(User user){
        if(containsID(user.getUserID())) {
            return false;
        }
        db.add(user);
        return true;
    }
}
