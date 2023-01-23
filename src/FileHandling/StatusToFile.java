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
public class StatusToFile {

    private final BufferedWriter writer;
    private final String path;

    /**
     * StatusToFile class constructor.
     *
     * @param path the file.txt to write the current system status
     */
    public StatusToFile(String path) {
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
     * Stores the module status into a file.txt.
     *
     * @param str the status to be stored
     */
    public void writeStatusToFile(String str) {

        try ( BufferedWriter writer = new BufferedWriter(new FileWriter(
                this.path, true))) {

            writer.append(str + "\n");
        } catch (IOException ex) {
            Logger.getLogger(LogsFile.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }
}
