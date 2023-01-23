package carwashmachine;

import FileHandling.LogsFile;
import Status.SprinklersAndDryersStatus;
import enums.EnumSprinklersAndDryersStatus;
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
 * @author Rui Neto
 */
public class SprinklersAndDryers implements Runnable {

    private final Semaphore semSprinklersAndDryers;
    private final Semaphore semWashRollers;
    private final Semaphore semTreadmill;
    private final SprinklersAndDryersStatus sprinklersAndDryersStatus;
    private final JFrame window;
    private final JLabel label;
    private final long sprinkleDuration;
    private final long dryDuration;
    private final LogsFile logs;

    /**
     * The SprinklersAndDryers class constructor.
     *
     * @param semSprinklersAndDryers The Semaphore that controlls the Sprinklers
     * and Dryers
     * @param semWashRollers The Semaphore that controlls the wash rollers
     * @param semTreadmill The Semaphore that controlls the treadmill
     * @param sprinklersAndDryersStatus the object that stores the sprinklers
     * and dryers status
     * @param window the sprinklers and dryers window
     * @param label the label that shows the sprinklers and dryers status
     * @param sprinkleDuration the sprinkling working time
     * @param dryDuration the drying working time
     * @param logs the logs object
     */
    public SprinklersAndDryers(Semaphore semSprinklersAndDryers,
            Semaphore semWashRollers, Semaphore semTreadmill,
            SprinklersAndDryersStatus sprinklersAndDryersStatus, JFrame window,
            JLabel label, long sprinkleDuration, long dryDuration,
            LogsFile logs) {

        this.semWashRollers = semWashRollers;
        this.semSprinklersAndDryers = semSprinklersAndDryers;
        this.semTreadmill = semTreadmill;
        this.sprinklersAndDryersStatus = sprinklersAndDryersStatus;
        this.window = window;
        this.label = label;
        this.sprinkleDuration = sprinkleDuration;
        this.dryDuration = dryDuration;
        this.logs = logs;
    }

    private void showWindow() {
        this.window.setPreferredSize(new Dimension(400, 70));
        this.window.getContentPane().setLayout(new FlowLayout());
        this.window.add(this.label);
        this.window.setLocation(100, 380);
        this.window.pack();
        this.window.setResizable(false);
        this.window.setVisible(true);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private long getRandomNumber(long min, long max) {
        return (int) ((Math.random() * (max - min)) + min);
    }

    private void sprinkle() throws InterruptedException {
        this.semSprinklersAndDryers.acquire();
        this.logs.writeToLogsFile(LocalDateTime.now() + " Aspersores ligam");
        this.sprinklersAndDryersStatus.
                setSprinklersAndDryersStatus(
                        EnumSprinklersAndDryersStatus.SPRINKLING);

        this.label.setText(this.sprinklersAndDryersStatus
                .getSprinklersAndDryersStatus().toString());

        Thread.sleep(this.sprinkleDuration);
        this.logs.writeToLogsFile(LocalDateTime.now() + " Aspersores desligam");
        this.sprinklersAndDryersStatus.setSprinklersAndDryersStatus(
                EnumSprinklersAndDryersStatus.STOPPED);

        this.label.setText(this.sprinklersAndDryersStatus
                .getSprinklersAndDryersStatus().toString());

        this.semWashRollers.release();
    }

    private void dry() throws InterruptedException {
        this.semSprinklersAndDryers.acquire(2);
        logs.writeToLogsFile(LocalDateTime.now() + " Secadores ligam");
        this.sprinklersAndDryersStatus.setSprinklersAndDryersStatus(
                EnumSprinklersAndDryersStatus.DRYING);

        this.label.setText(this.sprinklersAndDryersStatus
                .getSprinklersAndDryersStatus().toString());

        Thread.sleep(this.dryDuration);
        this.logs.writeToLogsFile(LocalDateTime.now() + " Secadores desligam");
        this.sprinklersAndDryersStatus.setSprinklersAndDryersStatus(
                EnumSprinklersAndDryersStatus.STOPPED);

        this.label.setText(this.sprinklersAndDryersStatus
                .getSprinklersAndDryersStatus().toString());

        this.semTreadmill.release(2);
    }

    /**
     * This method is invocated when the Thread is started.
     */
    @Override
    public void run() {
        while (true) {
            this.label.setText(this.sprinklersAndDryersStatus
                    .getSprinklersAndDryersStatus().toString());

            this.showWindow();

            if (this.semSprinklersAndDryers.availablePermits() < 2) {
                try {
                    this.sprinkle();
                } catch (InterruptedException ex) {
                    Logger.getLogger(SprinklersAndDryers.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
            }

            try {
                this.dry();
            } catch (InterruptedException ex) {
                Logger.getLogger(SprinklersAndDryers.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
    }

}
