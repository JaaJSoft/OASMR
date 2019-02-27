package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.File;
import java.io.Serializable;

/**
 * A command that moves a file.
 */
public class CommandMoveFile extends Command {

    /**
     * The source file path name to move.
     */
    private String sourceFilePathName;

    /**
     * The destination path where to move the file.
     */
    private String destinationPathName;

    /**
     *
     * @param sourceFilePathName The source file path name to move.
     * @param destinationPathName The destination path where to move the file.
     */
    public CommandMoveFile(String sourceFilePathName, String destinationPathName) {
        this.sourceFilePathName = sourceFilePathName;
        this.destinationPathName = destinationPathName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File file = new File(sourceFilePathName);

        return file.renameTo(new File(destinationPathName + file.getName()));
    }

    @Override
    public String toString() {
        return null;
    }
}
