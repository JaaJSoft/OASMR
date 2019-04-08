package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommandListRoots extends Command {
    @Override
    protected Serializable execute(Object... params) throws Exception {
        File[] listRoots = File.listRoots();
        List<String> listPath = new ArrayList<>();
        for (File f : listRoots) {
            listPath.add(f.getAbsolutePath());
        }
        listPath.sort(String::compareTo);
        return listPath.toArray(new String[listPath.size()]);
    }

    @Override
    public String toString() {
        return "list roots";
    }
}
