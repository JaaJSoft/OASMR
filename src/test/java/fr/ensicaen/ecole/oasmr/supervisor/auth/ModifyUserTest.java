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

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionLoginAlreadyExisting;
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionUserUnknown;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestModifyUserLogin;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestModifyUserPassword;
import org.junit.Before;
import org.junit.Test;

public class ModifyUserTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5852);
        new RequestAddUser("JOOJ", "ahhh").execute(s);
        new RequestAddUser("JAAJ", "ahhha").execute(s);
    }

    @Test
    public void modifyUserLogin() throws Exception{
        RequestModifyUserLogin r = new RequestModifyUserLogin("JOOJ", "Jooj");
        r.execute(s);
        User newUser = new User("Jooj", "ahhh");
        assert (s.getUserList().authenticate(newUser.getLogin(), newUser.getPassword()));
        assert (!s.getUserList().authenticate("JOOJ", newUser.getPassword()));
    }

    @Test
    public void modifyUserPassword() throws Exception{
        RequestModifyUserPassword r = new RequestModifyUserPassword("JOOJ", "ahhh", "ah");
        r.execute(s);
        User newUser = new User("JOOJ", "ah");
        assert (s.getUserList().authenticate(newUser.getLogin(),newUser.getPassword() ));

    }

    @Test(expected = ExceptionLoginAlreadyExisting.class)
    public void existingLogin() throws Exception{
        RequestModifyUserLogin r = new RequestModifyUserLogin("JOOJ", "JAAJ");
        r.execute(s);
    }

    @Test(expected = ExceptionUserUnknown.class)
    public void unexistingOldUser() throws Exception{
        RequestModifyUserLogin r = new RequestModifyUserLogin("J55432OJ", "ULA");
        r.execute(s);
    }
    
    @Test(expected = ExceptionUserUnknown.class)
    public void modifyUserPasswordWrongPassword() throws Exception{
        RequestModifyUserPassword r = new RequestModifyUserPassword("JOOJ", "f", "ah");
        r.execute(s);

    }
}
