package carwashmachine;

import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;

/**
 *
 * @author Rui Neto
 */
public class CarsQueue implements Runnable {

    private final Semaphore semCarsQueue;
    private final Semaphore semTreadmill;
    private final Semaphore carQueueFlag;
    private final JLabel carsQueueLabel;

    /**
     * Constructor for the CarsQueue class.
     *
     * @param semCarsQueue the Semaphore wich the number of resources is equal
     * to the number of queued cars
     * @param semTreadmill the Semaphore that controlls the Treadmill
     * @param carQueueFlag the Semaphore that controlls the queued cars
     * @param carsQueueLabel the Label that counts the queued cars
     */
    public CarsQueue(Semaphore semCarsQueue, Semaphore semTreadmill,
            Semaphore carQueueFlag, JLabel carsQueueLabel) {

        this.semCarsQueue = semCarsQueue;
        this.semTreadmill = semTreadmill;
        this.carQueueFlag = carQueueFlag;
        this.carsQueueLabel = carsQueueLabel;
        this.carQueueFlag.release();
    }

    /**
     * This method is invocated when the Thread is started.
     */
    @Override
    public void run() {
        while (true) {
            try {
                this.carQueueFlag.acquire();
                this.semCarsQueue.acquire();
                Thread.sleep(5000);
                this.semTreadmill.release();
                this.carsQueueLabel.setText(String.valueOf("Cars in Queue: "
                        + this.semCarsQueue.availablePermits()));

            } catch (InterruptedException ex) {
                Logger.getLogger(CarsQueue.class.getName()).log(Level.SEVERE,
                        null, ex);
            }
        }
    }

}
