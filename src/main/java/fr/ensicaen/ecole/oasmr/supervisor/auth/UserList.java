package fr.ensicaen.ecole.oasmr.supervisor.auth;

import fr.ensicaen.ecole.oasmr.supervisor.HashUtil;

import java.util.ArrayList;
import java.util.List;

public class UserList {
    private List<User> userList;

    public UserList(){
        userList = new ArrayList<>();
    }

    public void addUser(User newUser){
        userList.add(newUser);
    }

    public void modifyUser(User oldUser, User newUser){
        userList.remove(oldUser);
        userList.add(newUser);
    }

    public void deleteUser(User user2delete){
        userList.remove(user2delete);
    }

    public boolean authenticate(String login, String password){
        for (User user : userList) {
            if (user.getLogin().equals(login)) {
                return user.getPassword().equals(HashUtil.get_SHA_SecurePassword(password,"SHA-256"));
            }
        }
        return false;
    }
    //Il serait peut etre plus propre de passer un User en parametre...
}
