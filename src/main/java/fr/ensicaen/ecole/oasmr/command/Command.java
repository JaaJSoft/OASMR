package fr.ensicaen.ecole.oasmr.command;

import fr.ensicaen.ecole.oasmr.supervisor.Node;

import java.io.Serializable;
import java.util.concurrent.*;

public abstract class Command implements Serializable {

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

    public abstract Serializable execute(Node n);

    public abstract String toString();
}
