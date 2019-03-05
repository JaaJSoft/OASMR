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

import fr.ensicaen.ecole.oasmr.lib.crypto.HashUtil;

public class User {
    private String login;
    private String password;
    private boolean authenticated;
    private boolean isAdmin;

    public User(String login, String password){
        this.login = login;
        this.password = HashUtil.get_SHA_SecurePassword(password, "SHA-256");
        this.authenticated = false;
        this.isAdmin = false;
    }

    public User(User u){
        this.login=u.login;
        this.password=u.password;
        this.authenticated=u.authenticated;
        this.isAdmin=u.isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword(){
        return password;
    }

    public void setLogin(String login){
        this.login=login;
    }

    public void setPassword(String password){
        this.password = HashUtil.get_SHA_SecurePassword(password, "SHA-256");
    }

    public boolean getAuthentication(){ return authenticated; }

    public void authenticate(){ authenticated = true; }

    public void disconnect(){ authenticated = false; }

    public boolean equals(User u) {
        if (u != null && u.getClass() == User.class) {
            return login.equals(u.getLogin()) && password.equals(u.getPassword());
        }
        return false;
    }
}
