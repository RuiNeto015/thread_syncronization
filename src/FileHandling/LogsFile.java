package FileHandling;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;  
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rui Neto
 */
public class LogsFile {

    private final BufferedWriter writer;
    private final String path;

    /**
     * Constructor for LogsFile class.
     *
     * @param path the file.txt path
     */
    public LogsFile(String path) {
        this.path = path;
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(path);
        } catch (IOException ex) {
            Logger.getLogger(LogsFile.class.getName()).log(Level.SEVERE, null,
                    ex);
        }

        this.writer = new BufferedWriter(fileWriter);
    }

    /**
     * Writes log to the file.
     *
     * @param str the string to be written in the file
     */
    public void writeToLogsFile(String str) {
        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(
                this.path, true))) {

            writer.append(str + "\n");
        } catch (IOException ex) {
            Logger.getLogger(LogsFile.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
}
