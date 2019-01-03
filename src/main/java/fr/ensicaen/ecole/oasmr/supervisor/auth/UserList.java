package fr.ensicaen.ecole.oasmr.supervisor.auth;

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
        for(int i = 0; i<userList.size(); ++i){
            if (userList.get(i).getLogin().equals(login)){
                if (userList.get(i).getPassword().equals(password)){
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
}
