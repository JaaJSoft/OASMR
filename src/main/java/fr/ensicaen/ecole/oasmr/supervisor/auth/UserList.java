/*
 *  Copyright (c) 2019. CCC-Development-Team
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package fr.ensicaen.ecole.oasmr.supervisor.auth;

import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionUserUnknown;
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionLoginAlreadyExisting;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class UserList {
    private final List<User> userList;

    public UserList(){
        userList = new ArrayList<>();
    }

    public void addUser(User newUser) throws ExceptionLoginAlreadyExisting {
        for (User user : userList) {
            if (user.getLogin().equals(newUser.getLogin())){
                throw new ExceptionLoginAlreadyExisting(newUser.getLogin()+ ": login already used");
            }
        }
        userList.add(newUser);
    }

    public void modifyUserLogin(String login, String newLogin) throws ExceptionLoginAlreadyExisting, ExceptionUserUnknown {
        for (User user : userList) {
            if (user.getLogin().equals(newLogin)){
                throw new ExceptionLoginAlreadyExisting(newLogin + ": login already used");
            }
        }
        getUser(login).setLogin(newLogin);
    }

    public void modifyUserPassword(String login, String password, String newPassword) throws ExceptionLoginAlreadyExisting, ExceptionUserUnknown {
        User oldUser = new User(login, password);
        if (!authenticate(oldUser.getLogin(),oldUser.getPassword())){
            throw new ExceptionUserUnknown(oldUser.getLogin()+ ": incorrect password)");
        }
        getUser(login).setPassword(newPassword);
    }

    public void deleteUser(User user2delete) throws ExceptionUserUnknown {
        if (!authenticate(user2delete.getLogin(),user2delete.getPassword())){
            throw new ExceptionUserUnknown(user2delete.getLogin() + ": incorrect user (login or password)");
        }
        for (Iterator<User> it = userList.iterator(); it.hasNext();){
            User u = it.next();
            if (u.equals(user2delete)){
                it.remove();
                break;
            }
        }
    }

    public boolean authenticate(String login, String passwordHashed){
        for (User user : userList) {
            if (user.getLogin().equals(login)) {
                System.out.println("Login" + user.getLogin());
                System.out.println("Password Hashed" + user.getPassword());
                System.out.println("Try authenticate with hashed:" + passwordHashed);
                System.out.println("Authentication Result:" + user.getPassword().equals(passwordHashed));
                if (user.getPassword().equals(passwordHashed)){
                    user.authenticate();
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    public User getUser(String login) throws ExceptionUserUnknown {
        for (User user : userList) {
            if (user.getLogin().equals(login)){
                return user;
            }
        }
        throw new ExceptionUserUnknown(login + "not found");
    }

    public boolean isAuthenticate(String login){
        for (User user : userList) {
            if (user.getLogin().equals(login)){
                return user.getAuthentication();
            }
        }
        return false;
    }

    public List<String> getLoginList(){
        List<String> loginList = new ArrayList<>();
        for (User user : userList) {
            loginList.add(user.getLogin());
        }
        return loginList;
    }
}
