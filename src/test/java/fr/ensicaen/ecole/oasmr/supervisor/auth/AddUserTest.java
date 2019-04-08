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
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import org.junit.Before;
import org.junit.Test;

public class AddUserTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5852);
        new RequestAddUser("Oui", "aeaf").execute(s);
    }

    @Test
    public void addUser() throws Exception{
        RequestAddUser r = new RequestAddUser("JOOJ", "ahhh");
        r.execute(s);
        User u = new User("JOOJ", "ahhh");
        assert (s.getUserList().authenticate(u.getLogin(), u.getPassword()));
    }

    @Test(expected = ExceptionLoginAlreadyExisting.class)
    public void addAlreadyExistingUser() throws Exception{
        RequestAddUser r = new RequestAddUser("Oui", "aeaf");
        r.execute(s);
    }


}
