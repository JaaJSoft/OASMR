package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.CommandEchoString;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestExecuteCommand;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Example {

    public static void main(String[] args) throws Exception {
        RequestManager r = RequestManagerFlyweightFactory.getInstance().getRequestManager(InetAddress.getByName("127.0.0.1"), 40404);
        String jeej = (String) r.sendRequest(new RequestHelloWorld());
        System.out.println(jeej);
        String test = (String) r.sendRequest(new RequestExecuteCommand(0, new CommandEchoString(jeej)));
        System.out.println(test);

    }
}
