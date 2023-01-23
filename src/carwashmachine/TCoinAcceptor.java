package carwashmachine;

import CoinAcceptor.Coin;
import CoinAcceptor.CoinAcceptor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Victor
 */
public class TCoinAcceptor implements ActionListener, Runnable {

    private final Semaphore semCoinAcceptor;
    private final CoinAcceptor coinAcceptor;
    private final JFrame window;
    private final JLabel balanceLabel;
    private final DecimalFormat df;

    /**
     * TCoinAcceptor class constructor.
     *
     * @param semCoinAcceptor the Semaphore that controlls the coin acceptor
     * @param coinAcceptor the object that is responsable for the coin
     * transactions during the execution
     * @param balanceLabel the label that shows the current balance
     * @param df the decimal formatter
     */
    public TCoinAcceptor(Semaphore semCoinAcceptor, CoinAcceptor coinAcceptor,
            JLabel balanceLabel, DecimalFormat df) {

        this.semCoinAcceptor = semCoinAcceptor;
        this.coinAcceptor = coinAcceptor;
        this.window = new JFrame("CoinAcceptor");
        this.balanceLabel = balanceLabel;
        this.df = df;

        //window SetUp
        this.window.setPreferredSize(new Dimension(70, 300));
        this.window.getContentPane().setLayout(new FlowLayout());

        JButton twoEuroButton = new JButton("2€");
        JButton oneEuroButton = new JButton("1€");
        JButton fiftyCentButton = new JButton("0.5€");
        JButton twentyCentButton = new JButton("0.2€");

        twoEuroButton.setSize(60, 30);
        twoEuroButton.setLocation(30, 10);
        oneEuroButton.setSize(60, 30);
        oneEuroButton.setLocation(30, 70);
        fiftyCentButton.setSize(60, 30);
        fiftyCentButton.setLocation(30, 130);
        twentyCentButton.setSize(60, 30);
        twentyCentButton.setLocation(30, 190);

        twoEuroButton.addActionListener((ActionListener) this);
        oneEuroButton.addActionListener((ActionListener) this);
        fiftyCentButton.addActionListener((ActionListener) this);
        twentyCentButton.addActionListener((ActionListener) this);

        this.window.add(twoEuroButton);
        this.window.add(oneEuroButton);
        this.window.add(fiftyCentButton);
        this.window.add(twentyCentButton);
    }

    private void showWindow() {
        this.window.setLayout(null);
        this.window.setLocation(1030, 262);
        this.window.pack();
        this.window.setResizable(false);
        this.window.setVisible(true);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Registers if button was pressed.
     *
     * @param ae the button that was pressed.
     */
    @Override
    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();

        if (action.equals("2€")) {
            this.coinAcceptor.insertCoin(new Coin(2f));
            this.balanceLabel.setText("Balance: " + String.valueOf(
                    df.format(this.coinAcceptor.getBalance())));

            this.window.dispose();

        } else if (action.equals("1€")) {
            this.coinAcceptor.insertCoin(new Coin(1f));
            this.balanceLabel.setText("Balance: " + String.valueOf(
                    df.format(this.coinAcceptor.getBalance())));

            this.window.dispose();

        } else if (action.equals("0.5€")) {
            this.coinAcceptor.insertCoin(new Coin(0.5f));
            this.balanceLabel.setText("Balance: " + String.valueOf(
                    df.format(this.coinAcceptor.getBalance())));

            this.window.dispose();

        } else if (action.equals("0.2€")) {
            this.coinAcceptor.insertCoin(new Coin(0.2f));
            this.balanceLabel.setText("Balance: " + String.valueOf(
                    df.format(this.coinAcceptor.getBalance())));

            this.window.dispose();
        }
    }

    /**
     * This method is invocated when the Thread is started.
     */
    @Override
    public void run() {
        while (true) {
            try {
                this.semCoinAcceptor.acquire();
                this.showWindow();
            } catch (InterruptedException ex) {
                Logger.getLogger(TCoinAcceptor.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
    }

}
