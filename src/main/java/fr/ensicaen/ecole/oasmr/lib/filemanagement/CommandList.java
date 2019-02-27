package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.File;
import java.io.Serializable;

/**
 * Lists all the files of a directory.
 * @TODO Add a filter
 */
public class CommandList extends Command {

    /**
     * The directory path name to list.
     */
    private String directoryPathname;

    /**
     *
     * @param directoryPathname The directory path name to list.
     */
    public CommandList(String directoryPathname) {
        this.directoryPathname = directoryPathname;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File file = new File(directoryPathname);

        return file.list();
    }

    @Override
    public String toString() {
        return null;
    }
}
