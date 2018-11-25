package fr.ensicaen.ecole.oasmr.lib.command;

import fr.ensicaen.ecole.oasmr.supervisor.Node;

import java.io.Serializable;
import java.util.concurrent.*;

public abstract class Command implements Serializable, Runnable {

    public Serializable executeInThread(Node n) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Callable<Serializable> callable = () -> execute(n);

        Future<Serializable> future = executor.submit(callable);
        executor.shutdown();
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return e;
        }
    }

    /**
     * @warning no parameters :(
     */
    @Override
    public void run() {
        execute();//TODO PARAMETERS
    }

    public abstract Serializable execute(Object... params);

    public abstract String toString();
}
