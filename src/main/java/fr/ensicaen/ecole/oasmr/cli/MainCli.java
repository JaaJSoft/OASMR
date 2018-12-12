package fr.ensicaen.ecole.oasmr.cli;

import fr.ensicaen.ecole.oasmr.lib.example.CommandEchoString;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionConnectionFailure;
import fr.ensicaen.ecole.oasmr.lib.network.exception.ExceptionPortInvalid;
import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestExecuteCommand;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNodes;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;

import java.net.InetAddress;
import java.util.Set;

class MainCli {

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage <IPAddress> <port>");
            return;
        }
        String ip = args[0];
        int port = Integer.parseInt(args[1]);
        try {
            RequestManager r;
            r = new RequestManager(InetAddress.getByName(ip), port);

            @SuppressWarnings("unchecked")
            Set<Node> nodes = (Set<Node>) r.sendRequest(new RequestGetNodes());

            for (Node n : nodes) {
                System.out.println(n);
            }
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
