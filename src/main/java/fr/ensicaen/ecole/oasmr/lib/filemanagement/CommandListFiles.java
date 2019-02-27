package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.File;
import java.io.Serializable;

/**
 * Lists all the files of a directory.
 * @TODO Add a filter
 */
public class CommandListFiles extends Command {

    /**
     * The directory path name to list.
     */
    private String directoryPathName;

    /**
     *
     * @param directoryPathName The directory path name to list.
     */
    public CommandListFiles(String directoryPathName) {
        this.directoryPathName = directoryPathName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File file = new File(directoryPathName);

        return file.list();
    }

    @Override
    public String toString() {
        return null;
    }
}
