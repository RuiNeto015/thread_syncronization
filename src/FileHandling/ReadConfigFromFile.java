package FileHandling;

import java.io.File;
import java.io.FileNotFoundException;
import static java.lang.Float.parseFloat;
import static java.lang.Long.parseLong;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Rui Neto
 */
public class ReadConfigFromFile {

    Scanner myReader;
    private float custoLavagem;
    private long tempoMovimentoTapete, tempoMovimentoRolos,
            tempoMovimentoAspersores, tempoSecagem;

    /**
     * Constructor for the ReadConfigFromFile class.
     *
     * @param path the file.txt path
     */
    public ReadConfigFromFile(String path) {

        File myFile = new File(path);
        Scanner myReader;
        try {
            myReader = new Scanner(myFile);
            try {
                myReader.nextLine();
                this.custoLavagem =  parseFloat(myReader.nextLine());
                myReader.nextLine();
                this.tempoMovimentoTapete = parseLong(myReader.nextLine());
                myReader.nextLine();
                this.tempoMovimentoRolos = parseLong(myReader.nextLine());
                myReader.nextLine();
                this.tempoMovimentoAspersores = parseLong(myReader.nextLine());
                myReader.nextLine();
                this.tempoSecagem = parseLong(myReader.nextLine());
            } catch (NumberFormatException n) {
                System.out.println("Verificar inputs do ficheiro!");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ReadConfigFromFile.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }

    /**
     * Getter for the washing price from the file.
     *
     * @return the washing price.
     */
    public float getCustoLavagem() {
        return this.custoLavagem;
    }

    /**
     * Getter for the treadmill working time from the file.
     *
     * @return the treadmill working time
     */
    public long getTempoMovimentoTapete() {
        return this.tempoMovimentoTapete;
    }

    /**
     * Getter for the Wash Rollers working time from the file.
     *
     * @return Wash Rollers working time
     */
    public long getTempoMovimentoRolos() {
        return this.tempoMovimentoRolos;
    }

    /**
     * Getter for the Sprinklers working time from the file.
     *
     * @return the Sprinklers working time
     */
    public long getTempoMovimentoAspersores() {
        return this.tempoMovimentoAspersores;
    }

    /**
     * Getter for the Dryers working time from the file.
     *
     * @return the Dryers working time
     */
    public long getTempoSecagem() {
        return this.tempoSecagem;
    }
}
