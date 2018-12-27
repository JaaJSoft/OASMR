package fr.ensicaen.ecole.oasmr.cli;

import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManager;
import fr.ensicaen.ecole.oasmr.supervisor.request.RequestManagerFlyweightFactory;
import picocli.CommandLine;

import java.net.InetAddress;
import java.util.concurrent.Callable;

@CommandLine.Command(name = "MainCli", subcommands = {ListCli.class, NodeCli.class})
public class MainCli implements Callable<Void> {

    @CommandLine.Option(names = {"-s", "--supervisor"}, required = true, description = "supervisor address")
    InetAddress supervisorAddress;

    @CommandLine.Option(names = {"-p", "--port"}, description = "supervisor port")
    int port = 40404;

    @CommandLine.Option(names = {"-h", "--help"}, usageHelp = true, description = "display a help message")
    boolean help = false;

    @CommandLine.Option(names = {"-u", "--user"}, description = "username")
    String username = "admin";

    RequestManager r;


    @Override
    public Void call() throws Exception {
        if (help) {
            CommandLine.usage(this, System.err);
            return null;
        }
        r = RequestManagerFlyweightFactory.getInstance().getRequestManager(supervisorAddress, port);
        return null;
    }

    public static void main(String[] args) throws Exception {
        CommandLine.call(new MainCli(), args);
    }
}
