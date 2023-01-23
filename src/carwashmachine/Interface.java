package carwashmachine;

import CoinAcceptor.CoinAcceptor;
import Status.InterfaceBuffer;
import Status.SystemStatus;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.concurrent.Semaphore;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Simão
 */
public class Interface implements ActionListener, Runnable {

    private final Semaphore semButtonPressed;
    private final InterfaceBuffer buffer;
    private final JFrame window;
    private final CoinAcceptor coinAcceptor;
    private final SystemStatus systemStatus;
    private final JLabel balanceLabel;
    private final JLabel carsQueueLabel;
    private final JLabel systemStatusLabel;
    private final JLabel emergencyLabel;
    private final JLabel washPriceLabel;
    private final int numberOfQueuedCars;
    private final DecimalFormat df;

    /**
     * Constructor for the Interface class
     *
     * @param semButtonPressed the Semaphore that controlls the user actions
     * @param buffer the object that store the user actions
     * @param coinAcceptor the object that is responsable for the coin
     * transactions during the execution
     * @param systemStatus the object that store the system status
     * @param window the Interface window
     * @param balanceLabel the label that shows the current user's balance
     * @param carsQueueLabel the label that shows the number of cars in queue
     * @param systemStatusLabel the label that shows the system status
     * @param emergencyLabel the label that shows if the system is on emergency
     * @param numberOfQueuedCars the number of queued cars
     * @param price the washing price
     * @param df the decimal formatter
     */
    public Interface(Semaphore semButtonPressed, InterfaceBuffer buffer,
            CoinAcceptor coinAcceptor, SystemStatus systemStatus, JFrame window,
            JLabel balanceLabel, JLabel carsQueueLabel,
            JLabel systemStatusLabel, JLabel emergencyLabel,
            int numberOfQueuedCars, float price, DecimalFormat df) {

        this.semButtonPressed = semButtonPressed;
        this.buffer = buffer;
        this.coinAcceptor = coinAcceptor;
        this.systemStatus = systemStatus;
        this.balanceLabel = balanceLabel;
        this.carsQueueLabel = carsQueueLabel;
        this.emergencyLabel = emergencyLabel;
        this.systemStatusLabel = systemStatusLabel;
        this.washPriceLabel = new JLabel();
        this.window = window;
        this.coinAcceptor.setWashPrice(price);
        this.numberOfQueuedCars = numberOfQueuedCars;
        this.df = df;
    }

    private void showWindow() {
        this.window.setPreferredSize(new Dimension(500, 300));
        this.window.getContentPane().setLayout(new FlowLayout());
        this.window.setLayout(null);

        JButton buttonI = new JButton("I");
        JButton buttonInsertCoin = new JButton("Insert Coin");
        JButton buttonA = new JButton("A");
        JButton buttonF = new JButton("F");
        JButton buttonC = new JButton("C");
        JButton buttonE = new JButton("E");
        JButton buttonR = new JButton("R");

        // define listeners para botões
        buttonI.addActionListener((ActionListener) this);
        buttonInsertCoin.addActionListener((ActionListener) this);
        buttonA.addActionListener((ActionListener) this);
        buttonF.addActionListener((ActionListener) this);
        buttonC.addActionListener((ActionListener) this);
        buttonE.addActionListener((ActionListener) this);
        buttonR.addActionListener((ActionListener) this);

        // adiciona botões à janela
        this.window.add(buttonI);
        this.window.add(buttonInsertCoin);
        this.window.add(buttonA);
        this.window.add(buttonF);
        this.window.add(buttonC);
        this.window.add(buttonE);
        this.window.add(buttonR);

        //posição dos botões
        buttonI.setSize(50, 30);
        buttonI.setLocation(225, 60);
        buttonInsertCoin.setSize(100, 30);
        buttonInsertCoin.setLocation(200, 150);
        buttonC.setSize(50, 30);
        buttonC.setLocation(225, 190);
        buttonA.setSize(50, 30);
        buttonA.setLocation(10, 225);
        buttonF.setSize(50, 30);
        buttonF.setLocation(70, 225);
        buttonE.setSize(50, 30);
        buttonE.setLocation(365, 225);
        buttonR.setSize(50, 30);
        buttonR.setLocation(425, 225);

        //adiciona labels à janela
        this.window.add(this.balanceLabel);
        this.window.add(this.carsQueueLabel);
        this.window.add(this.systemStatusLabel);
        this.window.add(this.emergencyLabel);
        this.window.add(this.washPriceLabel);

        //posição das labels
        this.balanceLabel.setSize(100, 50);
        this.balanceLabel.setLocation(405, -6);
        this.systemStatusLabel.setSize(100, 50);
        this.systemStatusLabel.setLocation(225, -4);
        this.carsQueueLabel.setSize(100, 50);
        this.carsQueueLabel.setLocation(30, 40);
        this.emergencyLabel.setSize(100, 50);
        this.emergencyLabel.setLocation(30, 70);
        this.washPriceLabel.setSize(400, 50);
        this.washPriceLabel.setLocation(197, 100);

        this.window.pack();
        this.window.setLocationRelativeTo(null);
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

        if (action.equals("I")) {
            this.buffer.setBotao("I");
            semButtonPressed.release();

        } else if (action.equals("Insert Coin")) {
            this.buffer.setBotao("Insert Coin");
            semButtonPressed.release();

        } else if (action.equals("A")) {
            this.buffer.setBotao("A");
            semButtonPressed.release();

        } else if (action.equals("F")) {
            this.buffer.setBotao("F");
            semButtonPressed.release();

        } else if (action.equals("C")) {
            this.buffer.setBotao("C");
            semButtonPressed.release();

        } else if (action.equals("E")) {
            this.buffer.setBotao("E");
            semButtonPressed.release();

        } else if (action.equals("R")) {
            this.buffer.setBotao("R");
            semButtonPressed.release();
        }
    }

    /**
     * This method is invocated when the Thread is started.
     */
    @Override
    public void run() {
        balanceLabel.setText("Balance: " + String.valueOf(
                df.format(coinAcceptor.getBalance())));

        this.systemStatusLabel.setText(this.systemStatus.getSystemStatus()
                .toString());

        this.carsQueueLabel.setText("Cars in Queue: "
                + String.valueOf(this.numberOfQueuedCars));

        this.emergencyLabel.setText("Emergency: OFF");
        
        this.washPriceLabel.setText("Washing Price: " 
                + this.coinAcceptor.getWashPrice());

        this.showWindow();
    }

}
