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

package dev.jaaj.oasmr.lib.command;

import dev.jaaj.oasmr.lib.command.state.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public abstract class Command implements Serializable {
    private CommandState state = new CommandStateWaiting();
    private LocalDateTime beginDateTime;
    private LocalDateTime endDateTime;

    public Serializable executeCommand(Object... params) throws Exception {
        beginDateTime = LocalDateTime.now();
        state = new CommandStateRunning();
        try {
            Serializable response = execute(params);
            state = new CommandStateDone(response);
            endDateTime = LocalDateTime.now();
            return response;
        } catch (Exception t) {
            state = new CommandStateError(t);
            endDateTime = LocalDateTime.now();
            throw t;
        }
    }

    public LocalDateTime getBeginDateTime() {
        return beginDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    protected abstract Serializable execute(Object... params) throws Exception;

    public abstract String toString();

    public CommandState getState() {
        return state;
    }
}
