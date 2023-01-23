package CoinAcceptor;

import Resources.LinkedStack;
import Resources.StackADT;

/**
 *
 * @author Simão
 */
public class CoinAcceptor {

    private final StackADT<Coin> twoEuro;
    private final StackADT<Coin> oneEuro;
    private final StackADT<Coin> fiftyCent;
    private final StackADT<Coin> twentyCent;
    private float washPrice;
    private float balance;

    /**
     * Default constructor for the CoinAcceptor class.
     */
    public CoinAcceptor() {
        this.balance = 0;
        this.twoEuro = new LinkedStack<>();
        this.oneEuro = new LinkedStack<>();
        this.fiftyCent = new LinkedStack<>();
        this.twentyCent = new LinkedStack<>();
    }

    /**
     * Getter for the current balance.
     *
     * @return the current balance
     */
    public float getBalance() {
        return this.balance;
    }

    /**
     * Getter for the wash price.
     *
     * @return the wash price
     */
    public float getWashPrice() {
        return this.washPrice;
    }

    /**
     * Setter for the washPrice.
     *
     * @param washPrice the wash price
     */
    public void setWashPrice(float washPrice) {
        this.washPrice = washPrice;
    }

    private boolean coinIsValid(Coin coin) {
        return !(coin.getValue() != 2f && coin.getValue() != 1f
                && coin.getValue() != 0.5f && coin.getValue() != 0.2f);
    }

    private void addCoinToRespectiveStack(Coin coin) {
        if (coin.getValue() == 2f) {
            this.twoEuro.push(coin);

        } else if (coin.getValue() == 1f) {
            this.oneEuro.push(coin);

        } else if (coin.getValue() == 0.5f) {
            this.fiftyCent.push(coin);

        } else if (coin.getValue() == 0.2f) {
            this.twentyCent.push(coin);
        }
    }

    /**
     * Insert coins into the coinAcceptor.
     *
     * @param coin the coin to be inserted
     * @return the coin if not valid otherwise null
     */
    public Coin insertCoin(Coin coin) {
        if (!coinIsValid(coin)) {
            return coin;
        }

        this.balance += coin.getValue();
        this.addCoinToRespectiveStack(coin);

        return null;
    }

    private float twoEuroCoinForExchange(StackADT<Coin> exchangeCoins,
            float exchange) {

        int coin = (int) (exchange / 2f);

        if (this.twoEuro.size() >= coin) {
            exchange -= coin * 2f;

            for (int i = 0; i < coin; i++) {
                exchangeCoins.push(this.twoEuro.pop());
            }
        }
        return exchange;
    }

    private float oneEuroCoinForExchange(StackADT<Coin> exchangeCoins,
            float exchange) {

        int coin = (int) (exchange / 1f);

        if (this.oneEuro.size() >= coin) {
            exchange -= coin * 1f;

            for (int i = 0; i < coin; i++) {
                exchangeCoins.push(this.oneEuro.pop());
            }
        }
        return exchange;
    }

    private float fiftyCentCoinForExchange(StackADT<Coin> exchangeCoins,
            float exchange) {

        int coin = (int) (exchange / 0.5f);

        if (this.fiftyCent.size() >= coin) {
            exchange -= coin * 0.5f;

            for (int i = 0; i < coin; i++) {
                exchangeCoins.push(this.fiftyCent.pop());
            }
        }
        return exchange;
    }

    private float twentyCentCoinForExchange(StackADT<Coin> exchangeCoins,
            float exchange) {

        int coin = (int) (exchange / 0.2f);

        if (this.twentyCent.size() >= coin) {
            exchange -= coin * 0.2f;

            for (int i = 0; i < coin; i++) {
                exchangeCoins.push(this.twentyCent.pop());
            }
        }
        return exchange;
    }

    /**
     * Verifies if the current balance is enough to pay the car wash.
     *
     * @return true if the current balance is enough to pay the car wash
     * otherwise false
     */
    public boolean hasEnough() {
        return this.balance >= this.washPrice;
    }

    /**
     * Pays the car wash.
     *
     * @return the exchange
     */
    public StackADT<Coin> payCarWash() {
        StackADT<Coin> exchangeCoins = new LinkedStack<>();
        float exchange = this.balance - this.washPrice;

        exchange = twoEuroCoinForExchange(exchangeCoins, exchange);
        exchange = oneEuroCoinForExchange(exchangeCoins, exchange);
        exchange = fiftyCentCoinForExchange(exchangeCoins, exchange);
        exchange = twentyCentCoinForExchange(exchangeCoins, exchange);

        if (exchange != 0) {
            System.out.println("Não ha troco suficiente");
        }

        this.balance = 0f;
        return exchangeCoins;
    }

    /**
     * Returns the current amount inserted.
     *
     * @return the coins inserted
     */
    public StackADT<Coin> returnCurrentInsertion() {
        StackADT<Coin> currentInsertion = new LinkedStack<>();

        this.balance = twoEuroCoinForExchange(currentInsertion,
                this.balance);
        this.balance = oneEuroCoinForExchange(currentInsertion,
                this.balance);
        this.balance = fiftyCentCoinForExchange(currentInsertion,
                this.balance);
        this.balance = twentyCentCoinForExchange(currentInsertion,
                this.balance);

        this.balance = 0f;
        return currentInsertion;
    }

}
