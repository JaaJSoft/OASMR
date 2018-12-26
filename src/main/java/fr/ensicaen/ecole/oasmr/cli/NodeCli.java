package fr.ensicaen.ecole.oasmr.cli;

import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNode;
import picocli.CommandLine;

import java.net.InetAddress;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "node")
public class NodeCli implements Callable {

    @CommandLine.ParentCommand
    MainCli main;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    boolean help = false;

    @CommandLine.Option(names = {"-i", "--id"}, required = true, description = "node id")
    int idNode;

    @Override
    public Object call() throws Exception {
        main.call();

        if (help) {
            CommandLine.usage(this, System.err);
            return null;
        }

        Node n = (Node) main.r.sendRequest(new RequestGetNode(idNode));
        System.out.println(n);
        return n;
    }
}
