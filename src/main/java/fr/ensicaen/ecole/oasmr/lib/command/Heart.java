package fr.ensicaen.ecole.oasmr.lib.command;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

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
                heartbeat.execute(params);
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
