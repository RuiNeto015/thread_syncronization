package carwashmachine;

import FileHandling.LogsFile;
import Status.WashRollersStatus;
import enums.EnumWashRollersStatus;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Victor
 */
public class WashRollers implements Runnable {

    private final Semaphore semWashRollers;
    private final Semaphore semSprinklersAndDryers;
    private final WashRollersStatus washRollerStatus;
    private final JFrame window;
    private final JLabel label;
    private final long duration;
    private final LogsFile logs;

    /**
     * WashRollers class constructor.
     *
     * @param semWashRollers the Semaphore that controlls the wash rollers
     * @param semSprinklersAndDryers the Semaphore that controlls the sprinklers
     * and dryers
     * @param washRollerStatus the object that stores the wash rollers status
     * @param window the wash rollers window 
     * @param label the label that shows the wash rollers status
     * @param duration the wash rollers working time
     * @param logs the logs object
     */
    public WashRollers(Semaphore semWashRollers,
            Semaphore semSprinklersAndDryers,
            WashRollersStatus washRollerStatus, JFrame window, JLabel label,
            long duration, LogsFile logs) {

        this.semWashRollers = semWashRollers;
        this.semSprinklersAndDryers = semSprinklersAndDryers;
        this.washRollerStatus = washRollerStatus;
        this.window = window;
        this.label = label;
        this.duration = duration;
        this.logs = logs;
    }

    private void showWindow() {
        this.window.setPreferredSize(new Dimension(400, 70));
        this.window.getContentPane().setLayout(new FlowLayout());
        this.window.add(this.label);
        this.window.setLocation(100, 450);
        this.window.pack();
        this.window.setResizable(false);
        this.window.setVisible(true);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private long getRandomNumber(long min, long max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void startWashRollers() throws InterruptedException {
        this.semWashRollers.acquire();
        this.logs.writeToLogsFile(LocalDateTime.now() + " Rolos iniciam");
        this.washRollerStatus.setWashRollersStatus(EnumWashRollersStatus.ACTIVE);
        this.label.setText(this.washRollerStatus.getWashRollersStatus()
                .toString());

        Thread.sleep(this.duration);
        this.logs.writeToLogsFile(LocalDateTime.now() + " Rolos param");
        this.washRollerStatus.setWashRollersStatus(
                EnumWashRollersStatus.STOPPED);

        this.label.setText(this.washRollerStatus.getWashRollersStatus()
                .toString());

        this.semSprinklersAndDryers.release(2);
    }

    /**
     * This method is invocated when the Thread is started.
     */
    @Override
    public void run() {
        while (true) {
            this.label.setText(this.washRollerStatus.getWashRollersStatus()
                    .toString());

            this.showWindow();

            try {
                this.startWashRollers();
            } catch (InterruptedException ex) {
                Logger.getLogger(WashRollers.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }

}
