package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;

import java.net.InetAddress;
import java.util.HashSet;

public class RequestManagerFlyweightFactory {
    private HashSet<RequestManager> requestManagers;
    private static RequestManagerFlyweightFactory ourInstance = new RequestManagerFlyweightFactory();

    public static RequestManagerFlyweightFactory getInstance() {
        return ourInstance;
    }

    private RequestManagerFlyweightFactory() {
        requestManagers = new HashSet<>();
    }

    public RequestManager getRequestManager(InetAddress address, int port) throws ExceptionPortInvalid {
        RequestManager r = new RequestManager(address, port);
        for (RequestManager requestManager : requestManagers) {
            if (requestManager.equals(r)) {
                return requestManager;
            }
        }
        requestManagers.add(r);
        return r;
    }

    public int getNbRequestManager() {
        return requestManagers.size();
    }

    public void clear() {
        requestManagers.clear();
    }
}
