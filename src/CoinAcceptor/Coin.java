package CoinAcceptor;

/**
 *
 * @author Simão 
 */
public class Coin {

    private final float value;

    /**
     * Constructor for the Coin class.
     *
     * @param value the coin value
     */
    public Coin(float value) {
        this.value = value;
    }

    /**
     * Getter for the coin value.
     *
     * @return the coin value
     */
    public float getValue() {
        return this.value;

    }
}
