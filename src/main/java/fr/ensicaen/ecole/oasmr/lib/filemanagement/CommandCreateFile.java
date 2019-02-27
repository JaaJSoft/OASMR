package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.File;
import java.io.Serializable;

/**
 * Creates a non directory file.
 */
public class CommandCreateFile extends Command {

    /**
     * The file path name to create.
     */
    private String filePathName;

    /**
     *
     * @param filePathName The file path name to create.
     */
    public CommandCreateFile(String filePathName) {
        this.filePathName = filePathName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File file = new File(filePathName);
        return file.createNewFile();
    }

    @Override
    public String toString() {
        return null;
    }
}
