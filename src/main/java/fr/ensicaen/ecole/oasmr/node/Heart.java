package fr.ensicaen.ecole.oasmr.node;

import java.net.InetAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

import static java.util.concurrent.TimeUnit.SECONDS;

public class Heart {
    private ScheduledFuture<?> scheduleAtFixedRateHeartBeat;
    private int heartBeatPeriodInSeconds = 10;
    private Runnable heartbeat;

    public Heart(Runnable r, int heartBeatPeriodInSeconds) {
        this.heartBeatPeriodInSeconds = heartBeatPeriodInSeconds;
        heartbeat = r;
    }

    public Heart(Runnable heartbeat) {
        this.heartbeat = heartbeat;
    }

    public void start() {
        final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        scheduleAtFixedRateHeartBeat = executor.scheduleAtFixedRate(heartbeat, 10, heartBeatPeriodInSeconds, SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(executor::shutdown));
    }

    public void stop() {
        scheduleAtFixedRateHeartBeat.cancel(true);
    }
}
