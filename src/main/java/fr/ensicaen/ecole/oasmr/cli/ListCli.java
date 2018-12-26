package fr.ensicaen.ecole.oasmr.cli;

import fr.ensicaen.ecole.oasmr.supervisor.node.Node;
import fr.ensicaen.ecole.oasmr.supervisor.node.request.RequestGetNodes;
import picocli.CommandLine;

import java.util.Set;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "list")
public class ListCli implements Callable {

    @CommandLine.ParentCommand
    MainCli supervisor;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    boolean help = false;

    @Override
    public Object call() throws Exception {
        supervisor.call();
        if (help) {
            CommandLine.usage(this, System.err);
            return null;
        }
        Set<Node> nodes = (Set<Node>) supervisor.r.sendRequest(new RequestGetNodes());
        for (Node n : nodes) {
            System.out.println(n.getId() + " -> " + n.getNodeAddress() + ":" + n.getPort());
        }
        return nodes;
    }
}
