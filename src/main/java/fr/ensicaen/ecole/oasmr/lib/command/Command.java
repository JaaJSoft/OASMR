package fr.ensicaen.ecole.oasmr.lib.command;

import java.io.Serializable;
import java.util.concurrent.*;

public abstract class Command implements Serializable {

    public Serializable executeInThread(Object... params) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Serializable> callable = () -> execute(params);

        Future<Serializable> future = executor.submit(callable);
        executor.shutdown();
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return e;
        }
    }

    public abstract Serializable execute(Object... params) throws Exception;

    public abstract String toString();
}