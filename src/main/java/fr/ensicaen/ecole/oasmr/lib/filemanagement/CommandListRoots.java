package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.File;
import java.io.Serializable;

public class CommandListRoots extends Command {
    @Override
    protected Serializable execute(Object... params) throws Exception {
        return File.listRoots();
    }

    @Override
    public String toString() {
        return "list roots";
    }
}
