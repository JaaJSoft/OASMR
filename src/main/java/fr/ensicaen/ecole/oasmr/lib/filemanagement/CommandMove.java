package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import com.sun.jna.platform.FileUtils;
import fr.ensicaen.ecole.oasmr.lib.command.Command;
import oshi.util.FileUtil;

import java.io.File;
import java.io.Serializable;

public class CommandMove extends Command {

    private String fileNamePathToBeMoved;

    private String destinationPath;

    public CommandMove(String fileNamePathToBeMoved, String destinationPath) {
        this.fileNamePathToBeMoved = fileNamePathToBeMoved;
        this.destinationPath = destinationPath;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        File file = new File(fileNamePathToBeMoved);

        return file.renameTo(new File(destinationPath));
    }

    @Override
    public String toString() {
        return null;
    }
}
