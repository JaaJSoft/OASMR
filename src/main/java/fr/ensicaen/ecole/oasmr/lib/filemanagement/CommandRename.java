package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.File;
import java.io.Serializable;

/**
 * A command that renames a file
 */
public class CommandRename extends Command {

    /**
     * The file name absolute path to be renamed
     */
    private String fileNamePathToBeRenamed;

    /**
     * The new name of the file
     */
    private String newFileName;

    public CommandRename(String fileNamePathToBeRenamed, String newFileName) {
        this.fileNamePathToBeRenamed = fileNamePathToBeRenamed;
        this.newFileName = newFileName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File file = new File(fileNamePathToBeRenamed);

        return file.renameTo(new File(file.getAbsolutePath().replace(file.getName(), "").concat(newFileName)));
    }

    @Override
    public String toString() {
        return null;
    }
}
