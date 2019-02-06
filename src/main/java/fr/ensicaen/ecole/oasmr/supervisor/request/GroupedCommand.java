package fr.ensicaen.ecole.oasmr.supervisor.request;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;

public class GroupedCommand extends Command {

    private final Command[] commands;

    public GroupedCommand(Command... commands) {
        this.commands = commands;
    }


    @Override
    public Serializable execute(Object... params) throws Exception {
        ArrayList<Serializable> requestArrayList = new ArrayList<>();
        for (Command r : commands) {
            requestArrayList.add(r.execute(params));//TODO do it in parallel
        }
        return requestArrayList.toArray(new Serializable[commands.length]);
    }

    @Override
    public String toString() {
        return "Multiple command : " + Arrays.toString(commands);
    }
}
