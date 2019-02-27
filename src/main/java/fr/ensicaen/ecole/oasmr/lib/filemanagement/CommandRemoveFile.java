package fr.ensicaen.ecole.oasmr.lib.filemanagement;


import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.File;
import java.io.Serializable;

/**
 * A command that removes a file
 */
public class CommandRemoveFile extends Command {

    /**
     * The name of the path of the file to be removed.
     */
    private String fileNamePathToBeRemoved;

    /**
     *
     * @param fileNamePathToBeRemoved The name of the path of the file to be removed.
     */
    public CommandRemoveFile(String fileNamePathToBeRemoved) {
        this.fileNamePathToBeRemoved = fileNamePathToBeRemoved;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File file = new File(fileNamePathToBeRemoved);

        return file.delete();
    }

    @Override
    public String toString() {
        return null;
    }
}
