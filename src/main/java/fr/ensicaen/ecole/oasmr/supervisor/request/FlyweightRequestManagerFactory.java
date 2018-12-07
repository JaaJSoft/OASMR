package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.net.InetAddress;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class FlyweightRequestManagerFactory {
    private HashSet<RequestManager> requestManagers;
    private static FlyweightRequestManagerFactory ourInstance = new FlyweightRequestManagerFactory();

    public static FlyweightRequestManagerFactory getInstance() {
        return ourInstance;
    }

    private FlyweightRequestManagerFactory() {
        requestManagers = new HashSet<>();
    }

    public RequestManager getRequestManager(InetAddress address, int port) throws ExceptionPortInvalid {
        RequestManager r = new RequestManager(address, port);
        for (RequestManager requestManager : requestManagers) {
            if (requestManager.equals(r)) {
                return requestManager;
            }
        }
        return r;
    }
}
