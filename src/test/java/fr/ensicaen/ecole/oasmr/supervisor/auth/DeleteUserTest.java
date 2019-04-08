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
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionUserUnknown;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestAddUser;
import fr.ensicaen.ecole.oasmr.supervisor.auth.request.RequestDeleteUser;
import org.junit.Before;
import org.junit.Test;

public class DeleteUserTest {

    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5852);
        new RequestAddUser("Jooj", "ah").execute(s);
    }

    @Test
    public void deleteUser() throws Exception{
        User u = new User("Jooj","ah");
        RequestDeleteUser r = new RequestDeleteUser( "Jooj");
        r.execute(s);
        assert (!s.getUserList().authenticate(u.getLogin(), u.getPassword()));
    }

    @Test(expected = ExceptionUserUnknown.class)
    public void deleteUnexistingUser() throws Exception{
        new RequestDeleteUser( "Jofzafeaoj").execute(s);
    }

}
