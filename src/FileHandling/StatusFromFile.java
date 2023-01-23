package FileHandling;

import enums.EnumCarTreadmillStatus;
import enums.EnumSprinklersAndDryersStatus;
import enums.EnumWashRollersStatus;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rui Neto
 */
public class StatusFromFile {

    Scanner myReader;
    private EnumCarTreadmillStatus carTreadmillStatus;
    private EnumSprinklersAndDryersStatus sprinklersAndDryersStatus;
    private EnumWashRollersStatus washRollerStatus;

    /**
     * StatusFromFile class constructor.
     *
     * @param path the file.txt path to read the status
     */
    public StatusFromFile(String path) {

        File myFile = new File(path);
        Scanner myReader;
        try {
            myReader = new Scanner(myFile);

            String str = myReader.nextLine();
            this.carTreadmillStatus = EnumCarTreadmillStatus.valueOf(str);

            str = myReader.nextLine();
            this.washRollerStatus = EnumWashRollersStatus.valueOf(str);

            str = myReader.nextLine();
            this.sprinklersAndDryersStatus = EnumSprinklersAndDryersStatus
                    .valueOf(str);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadConfigFromFile.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /**
     * Getter for the previous treadmill status stored in the file.
     *
     * @return the previous treadmill status
     */
    public EnumCarTreadmillStatus getCarTreadmillStatus() {
        return this.carTreadmillStatus;
    }

    /**
     * Getter for the previous sprinklers and dryers status stored in the file.
     *
     * @return the previous sprinklers and dryers status
     */
    public EnumSprinklersAndDryersStatus getSprinklersAndDryersStatus() {
        return this.sprinklersAndDryersStatus;
    }

    /**
     * Getter for the previous wash rollers status stored in the file.
     *
     * @return the previous wash rollers status
     */
    public EnumWashRollersStatus getWashRollersStatus() {
        return this.washRollerStatus;
    }

}
