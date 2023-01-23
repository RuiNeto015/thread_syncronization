package Status;

/**
 *
 * @author Rui Neto
 */
public class InterfaceBuffer {
    String botao = new String();

    /**
     * Stores the button pressed.
     *
     * @param botao the button pressed
     */
    public void setBotao( String botao ) {
        this.botao = botao;
    }

    /**
     * Getter for the button pressed.
     *
     * @return the button pressed
     */
    public String getBotao() {
        return this.botao;
    }
}
