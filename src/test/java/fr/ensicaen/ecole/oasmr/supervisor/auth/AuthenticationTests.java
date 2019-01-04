/*
 *  Copyright (c) 2018. CCC-Development-Team
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

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.*;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAuthentication;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestDeleteUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestModifyUser;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class AuthenticationTests {


    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221, 5852);
    }

    @Test
    public void addUser() throws Exception{
        RequestAddUser r = new RequestAddUser("JOOJ", "ahhh");
        r.execute(s);
        assert (s.getUserList().authenticate("JOOJ", "ahhh"));
    }

    @Test
    public void modifyUser() throws Exception{
        RequestModifyUser r = new RequestModifyUser("JOOJ", "ahhh", "Jooj", "ah");
        r.execute(s);
        assert (s.getUserList().authenticate("Jooj", "ah"));
    }

    @Test
    public void authenticateExistingUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Jooj", "ah");
        r.execute(s);
        assert (s.isAuthenticated());
    }
    @Test
    public void authenticateNoMoreExtistingUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "JOOJ", "ahhh");
        r.execute(s);
        assert (!s.isAuthenticated());
    }
    @Test
    public void authenticateUnknowLoginUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Joefaj", "ah");
        r.execute(s);
        assert (!s.isAuthenticated());
    }
    @Test
    public void authenticateWrongPasswordUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Jooj", "aha");
        r.execute(s);
        assert (!s.isAuthenticated());
    }
    @Test
    public void authenticateUnknowUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Joefaj", "aheaf");
        r.execute(s);
        assert (!s.isAuthenticated());
    }
    @Test
    public void deleteUser() throws Exception{
        RequestDeleteUser r = new RequestDeleteUser( "Jooj", "ah");
        r.execute(s);
        assert (!s.getUserList().authenticate("Jooj", "ah"));
    }
}
