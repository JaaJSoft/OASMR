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

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Execute a command at a fixed rate
 */
public class Heart {
    private ScheduledFuture<?> scheduleAtFixedRateHeartBeat;
    private int heartBeatPeriodInSeconds;
    private final Command heartbeat;
    private final Object[] params;

    public Heart(Command r, int heartBeatPeriodInSeconds, Object... params) {
        this.heartBeatPeriodInSeconds = heartBeatPeriodInSeconds;
        heartbeat = r;
        this.params = params;
    }

    public void start() {
        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        scheduleAtFixedRateHeartBeat = executor.scheduleAtFixedRate(() -> {
            try {
                heartbeat.executeCommand(params);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, 10, heartBeatPeriodInSeconds, SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
    }

    public void stop() {
        scheduleAtFixedRateHeartBeat.cancel(true);
    }
}
