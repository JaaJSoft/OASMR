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

package dev.jaaj.oasmr.supervisor.auth;

import dev.jaaj.oasmr.supervisor.Supervisor;
import dev.jaaj.oasmr.supervisor.auth.request.RequestAddUser;
import dev.jaaj.oasmr.supervisor.auth.request.RequestAuthentication;
import org.junit.Before;
import org.junit.Test;

public class AuthenticationTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5852);
        new RequestAddUser("Jooj", "ah").execute(s);
        new RequestAddUser("jefa", "ah").execute(s);

    }

    @Test
    public void authenticateExistingUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Jooj", "ah");
        r.execute(s);
        assert (s.getUserList().isAuthenticate("Jooj"));
    }

    @Test
    public void authenticateUnknowLoginUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Joefaj", "ah");
        r.execute(s);
        assert (!s.getUserList().isAuthenticate("Joefaj"));
    }

    @Test
    public void authenticateWrongPasswordUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Jooj", "aha");
        r.execute(s);
        assert (!s.getUserList().isAuthenticate("Jooj"));
    }
    @Test
    public void authenticateUnknowUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "Joefaj", "aheaf");
        r.execute(s);
        assert (!s.getUserList().isAuthenticate("Joefaj"));
    }

    @Test
    public void dontAuthenticateWrongUser() throws Exception{
        RequestAuthentication r = new RequestAuthentication( "jefa", "ah");
        r.execute(s);
        assert (!s.getUserList().isAuthenticate("Jooj"));
    }
}
