package fr.ensicaen.ecole.oasmr.lib.filemanagement;

import fr.ensicaen.ecole.oasmr.lib.command.Command;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Serializable;

/**
 * A command that copy a file
 */
public class CommandCopyFile extends Command {

    /**
     *
     */
    private String sourceFilePathName;

    /**
     *
     */
    private String destinationFilePathName;

    /**
     *
     * @param sourceFilePathName
     * @param destinationFilePathName
     */
    public CommandCopyFile(String sourceFilePathName, String destinationFilePathName) {
        this.sourceFilePathName = sourceFilePathName;
        this.destinationFilePathName = destinationFilePathName;
    }

    @Override
    protected Serializable execute(Object... params) throws Exception {
        FileReader in = null;
        FileWriter out = null;

        try {
            in = new FileReader(sourceFilePathName);
            out = new FileWriter(destinationFilePathName);

            int c;
            while ((c = in.read()) != -1) {
                out.write(c);
            }
        }finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return null;
    }
}
