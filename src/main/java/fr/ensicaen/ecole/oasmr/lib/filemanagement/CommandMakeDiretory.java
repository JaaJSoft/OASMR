package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import com.sun.jmx.mbeanserver.Repository;
import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.File;
import java.io.Serializable;

/**
 * A command that creates a directory given a path name.
 */
public class CommandMakeDiretory extends Command {

    /**
     * The directory path name to be created.
     */
    private String directoryPathName;

    /**
     *
     * @param directoryPathName The directory path name to be created.
     */
    public CommandMakeDiretory(String directoryPathName) {
        this.directoryPathName = directoryPathName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File newDirectory = new File(directoryPathName);

        return newDirectory.mkdir();
    }

    @Override
    public String toString() {
        return null;
    }
}
