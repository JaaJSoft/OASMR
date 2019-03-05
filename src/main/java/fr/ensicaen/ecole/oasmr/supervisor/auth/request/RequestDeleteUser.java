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

package fr.ensicaen.ecole.oasmr.supervisor.auth.request;

import fr.ensicaen.ecole.oasmr.supervisor.Supervisor;
import fr.ensicaen.ecole.oasmr.supervisor.auth.User;
import fr.ensicaen.ecole.oasmr.supervisor.auth.exception.ExceptionUserUnknown;
import fr.ensicaen.ecole.oasmr.supervisor.request.Request;

import java.io.Serializable;

public class RequestDeleteUser extends Request {
    private final String login;

    public RequestDeleteUser(String login, String password) {
        this.login = login;
    }

    @Override
    public Serializable execute(Supervisor supervisor) throws Exception {
        supervisor.getUserList().deleteUser(supervisor.getUserList().getUser(login));
        return 0;
    }

    @Override
    public String toString() {
        return login + " RequestDeleteUser";
    }
}
