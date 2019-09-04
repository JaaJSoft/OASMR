/*
 * Copyright (c) 2019. JaaJ-dev
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.jaaj.oasmr.supervisor.auth;

import dev.jaaj.oasmr.supervisor.Supervisor;
import dev.jaaj.oasmr.supervisor.auth.request.RequestAddUser;
import dev.jaaj.oasmr.supervisor.auth.request.RequestGetLoginList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GetUsersListTest {
    private Supervisor s;

    @Before
    public void setUp() throws Exception {
        s = new Supervisor(5221);
        new RequestAddUser("JOOJ", "ahhh").execute(s);
        new RequestAddUser("JAAJ", "ahhha").execute(s);
    }

    @Test
    public void deleteUser() throws Exception{
        User u = new User("Jooj","ah");
        RequestGetLoginList r = new RequestGetLoginList();
        List<String> l1 = (List<String>) r.execute(s);
        List<String> l2 = new ArrayList<>();
        l2.add("JOOJ");
        l2.add("JAAJ");
        assert (l1.equals(l2));
    }


}
