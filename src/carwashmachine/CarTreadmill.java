package carwashmachine;

import FileHandling.LogsFile;
import Status.CarTreadmillStatus;
import enums.EnumCarTreadmillStatus;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Simão
 */
public class CarTreadmill implements Runnable {

    private final Semaphore semTreadmill;
    private final Semaphore semSprinklersAndDryers;
    private final Semaphore carQueueFlag;
    private final CarTreadmillStatus carTreadmillStatus;
    private final JFrame window;
    private final JLabel label;
    private final long duration;
    private final LogsFile logs;

    /**
     * Constructor for the class CarTreadmill.
     *
     * @param semTreadmill the Semaphore that controlls the Treadmill
     * @param semSprinklersAndDryers the Semaphore that controlls the
     * SprinklersAndDryers
     * @param carQueueFlag the Semaphore that controlls the queued cars
     * @param carTreadmillStatus the Object that stores the Treadmill status
     * @param window the Treadmill window
     * @param label the Treadmill status label
     * @param duration the Treadmill working time
     * @param logs the logs object
     */
    public CarTreadmill(Semaphore semTreadmill,
            Semaphore semSprinklersAndDryers, Semaphore carQueueFlag,
            CarTreadmillStatus carTreadmillStatus, JFrame window, JLabel label,
            long duration, LogsFile logs) {

        this.semTreadmill = semTreadmill;
        this.semSprinklersAndDryers = semSprinklersAndDryers;
        this.carQueueFlag = carQueueFlag;
        this.carTreadmillStatus = carTreadmillStatus;
        this.window = window;
        this.label = label;
        this.duration = duration;
        this.logs = logs;
    }

    private void showWindow() {
        this.window.setPreferredSize(new Dimension(400, 70));
        this.window.getContentPane().setLayout(new FlowLayout());
        this.window.add(this.label);
        this.window.setLocation(100, 310);
        this.window.pack();
        this.window.setResizable(false);
        this.window.setVisible(true);
        this.window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void moveForward() throws InterruptedException {
        this.semTreadmill.acquire();
        this.logs.writeToLogsFile(LocalDateTime.now() + " Inicio de lavagem");
        this.logs.writeToLogsFile(LocalDateTime.now()
                + " Tapete anda para a frente");

        this.carTreadmillStatus.setCarTreadmillStatus(
                EnumCarTreadmillStatus.MOV_FOWARD);

        this.label.setText(this.carTreadmillStatus.getCarTreadmillStatus()
                .toString());

        Thread.sleep(this.duration);
        this.logs.writeToLogsFile(LocalDateTime.now() + " Tapete para");
        this.carTreadmillStatus.setCarTreadmillStatus(
                EnumCarTreadmillStatus.STOPPED);

        this.label.setText(this.carTreadmillStatus.getCarTreadmillStatus()
                .toString());

        this.semSprinklersAndDryers.release();
    }

    private void moveBackward() throws InterruptedException {
        this.semTreadmill.acquire(2);
        logs.writeToLogsFile(LocalDateTime.now() + " Tapete anda para trás");
        this.carTreadmillStatus.setCarTreadmillStatus(
                EnumCarTreadmillStatus.MOV_BACKWARD);

        this.label.setText(this.carTreadmillStatus.getCarTreadmillStatus()
                .toString());

        Thread.sleep(this.duration);
        this.logs.writeToLogsFile(LocalDateTime.now() + " Tapete para");
        this.logs.writeToLogsFile(LocalDateTime.now() + " Fim da Lavagem");
        this.carTreadmillStatus.setCarTreadmillStatus(
                EnumCarTreadmillStatus.STOPPED);

        this.label.setText(this.carTreadmillStatus.getCarTreadmillStatus()
                .toString());

        this.carQueueFlag.release();
    }

    /**
     * This method is invocated when the Thread is started.
     */
    @Override
    public void run() {
        while (true) {
            this.label.setText(this.carTreadmillStatus.getCarTreadmillStatus()
                    .toString());

            this.showWindow();

            if (this.semTreadmill.drainPermits() < 2) {
                try {
                    this.moveForward();
                } catch (InterruptedException ex) {
                    System.out.println("Interrupted");
                }
            }

            try {
                this.moveBackward();
            } catch (InterruptedException ex) {
                System.out.println("Interrupted");
            }
        }
    }

}
