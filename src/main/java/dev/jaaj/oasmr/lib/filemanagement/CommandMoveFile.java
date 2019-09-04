package dev.jaaj.oasmr.lib.filemanagement;

import dev.jaaj.oasmr.lib.command.Command;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CommandMoveFile extends Command {

    /**
     * The source file path name to move.
     */
    private final String sourceFilePathName;

    /**
     * The destination file path name to move.
     */
    private final String destinationFilePathName;

    /**
     * @param sourceFilePathName      The source file path name to move.
     * @param destinationFilePathName The destination file path name to move.
     */
    public CommandMoveFile(String sourceFilePathName, String destinationFilePathName) {
        this.sourceFilePathName = sourceFilePathName;
        this.destinationFilePathName = destinationFilePathName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {

        if(sourceFilePathName.equals(destinationFilePathName) || Files.exists(Paths.get(destinationFilePathName))){
            return false;
        }

        //TODO : Dir recursive
        Path result = Files.move(Paths.get(sourceFilePathName), Paths.get(destinationFilePathName));
        return Files.exists(result);
    }

    @Override
    public String toString() {
        return "move file " + sourceFilePathName + " -> " + destinationFilePathName;
    }
}