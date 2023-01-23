package PopUps;

import CoinAcceptor.Coin;
import Resources.StackADT;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Victor
 */
public class Exchange implements Runnable {

    private final StackADT<Coin> exchange;
    private final JFrame window;

    /**
     * The Exchange class constructor.
     *
     * @param exchange the exchange coins
     */
    public Exchange(StackADT<Coin> exchange) {
        this.exchange = exchange;
        this.window = new JFrame("Exchange");
    }

    private String ExchangeInString() {
        int twoEuro = 0;
        int oneEuro = 0;
        int fiftyCents = 0;
        int twentyCents = 0;
        int size = this.exchange.size();

        for (int i = 0; i < size; i++) {
            Coin coin = this.exchange.pop();

            if (coin.getValue() == 2f) {
                twoEuro++;

            } else if (coin.getValue() == 1f) {
                oneEuro++;

            } else if (coin.getValue() == 0.5f) {
                fiftyCents++;

            } else if (coin.getValue() == 0.2f) {
                twentyCents++;
            }
        }
        return "|  2€:  " + twoEuro + "  | 1€:  " + oneEuro + "  | 0.5€:  "
                + fiftyCents + "  |  0.2€: " + twentyCents + "  |";
    }

    private void showWindow() {
        this.window.setPreferredSize(new Dimension(500, 100));
        this.window.getContentPane().setLayout(new FlowLayout());
        JLabel exchangeLabel = new JLabel();
        exchangeLabel.setText(ExchangeInString());
        this.window.add(exchangeLabel);
        this.window.pack();
        this.window.setLocation(518, 580);
        this.window.setResizable(false);
        this.window.setVisible(true);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * This method is invocated when the Thread is started.
     */
    @Override
    public void run() {
        this.showWindow();
        try {
            Thread.sleep(5000);
            this.window.dispose();
        } catch (InterruptedException ex) {
            Logger.getLogger(Exchange.class.getName()).log(Level.SEVERE, null,
                    ex);
        }
    }

}
