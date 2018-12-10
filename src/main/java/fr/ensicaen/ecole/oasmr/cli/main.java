package fr.ensicaen.ecole.oasmr.cli;

import fr.ensicaen.ecole.oasmr.lib.CommandEchoString;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNodes;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.Set;

public class main {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage <IPaddress> <port>");
            return;
        }
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        try {
            RequestManager r;
            r = new RequestManager(InetAddress.getByName(ip), port);
            Set<Node> nodes = (Set<Node>) r.sendRequest(new RequestGetNodes());
            System.out.println(r.sendRequest(new RequestExecuteCommand(nodes.iterator().next().getId(), new CommandEchoString("jeej"))));

        } catch (ExceptionPortInvalid exceptionPortInvalid) {
            System.out.println("Port invalid : " + port);
        } catch (ExceptionConnectionFailure e) {
            System.out.println("Server not found");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

}
