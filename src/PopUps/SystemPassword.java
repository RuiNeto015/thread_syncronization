package PopUps;

import FileHandling.LogsFile;
import Status.SystemStatus;
import enums.EnumSystemStatus;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;

/**
 *
 * @author Victor
 */
public class SystemPassword implements ActionListener, Runnable {

    private final JFrame window;
    private final JPasswordField password;
    private final Semaphore semSystemPassword;
    private final Semaphore semSystemStatus;
    private final Semaphore semReset;
    private final Semaphore semCarsQueue;
    private final SystemStatus systemStatus;
    private final JLabel SystemStatusLabel;
    private final JLabel carsQueueLabel;
    private final LogsFile logs;

    /**
     * The SystemPassword class constructor.
     *
     * @param semSystemPassword the Semaphore that controlls the System Password
     * @param semSystemStatus the Semaphore that controlls the System status
     * @param semReset the Semaphore that controlls the System reset
     * @param semCarsQueue the Semaphore that controlls the cars queue
     * @param systemStatus the object that store the system status
     * @param SystemStatusLabel the system status label
     * @param carsQueueLabel the label that shows the number of queued cars
     * @param logs the logs objecta
     */
    public SystemPassword(Semaphore semSystemPassword,
            Semaphore semSystemStatus, Semaphore semReset,
            Semaphore semCarsQueue, SystemStatus systemStatus,
            JLabel SystemStatusLabel, JLabel carsQueueLabel, LogsFile logs) {

        this.window = new JFrame("Password");
        this.password = new JPasswordField();
        this.semSystemPassword = semSystemPassword;
        this.semSystemStatus = semSystemStatus;
        this.semReset = semReset;
        this.semCarsQueue = semCarsQueue;
        this.systemStatus = systemStatus;
        this.SystemStatusLabel = SystemStatusLabel;
        this.carsQueueLabel = carsQueueLabel;
        this.logs = logs;

    }

    private void showWindow() {
        this.window.setPreferredSize(new Dimension(300, 100));
        this.window.getContentPane().setLayout(new FlowLayout());
        this.window.setLayout(null);

        JButton enter = new JButton("Enter");
        this.window.add(password);
        this.window.add(enter);

        enter.addActionListener((ActionListener) this);

        this.password.setSize(100, 30);
        this.password.setLocation(60, 20);
        enter.setSize(70, 30);
        enter.setLocation(170, 20);
        
        JLabel passwordLabel = new JLabel();
        passwordLabel.setText("password= CSGO");
        passwordLabel.setSize(300, 50);
        passwordLabel.setLocation(0, -20);
        this.window.add(passwordLabel);

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

        if (action.equals("Enter") && this.semSystemStatus
                .availablePermits() == 0 && semReset.availablePermits() != 1) {

            if (this.password.getText().equals("CSGO")) {
                semSystemStatus.release();
                this.logs.writeToLogsFile(LocalDateTime.now()
                        + " Sistema Aberto");

                systemStatus.setSystemStatus(EnumSystemStatus.OPENED);
                SystemStatusLabel.setText(
                        systemStatus.getSystemStatus().toString());
            }
            this.password.setText("");
            this.window.dispose();

        } else if (action.equals("Enter") && semSystemStatus
                .availablePermits() == 1 && semReset.availablePermits() != 1) {

            if (this.password.getText().equals("CSGO")) {
                try {
                    semSystemStatus.acquire();
                    this.logs.writeToLogsFile(LocalDateTime.now()
                            + " Sistema Fechado");

                    systemStatus.setSystemStatus(EnumSystemStatus.CLOSED);
                    SystemStatusLabel.setText(
                            systemStatus.getSystemStatus().toString());

                } catch (InterruptedException ex) {
                    Logger.getLogger(SystemPassword.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
            }
            this.password.setText("");
            this.window.dispose();

        } else if (action.equals("Enter") && semReset.availablePermits() == 1) {
            if (this.password.getText().equals("CSGO")) {
                try {
                    semReset.acquire();
                    this.logs.writeToLogsFile(LocalDateTime.now()
                            + " Reset do Sistema");

                    this.semCarsQueue.acquire(semCarsQueue.availablePermits());
                    this.carsQueueLabel.setText(String.valueOf("Cars in Queue: "
                            + semCarsQueue.availablePermits()));

                } catch (InterruptedException ex) {
                    Logger.getLogger(SystemPassword.class.getName()).log(
                            Level.SEVERE, null, ex);
                }
            }
            this.password.setText("");
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
                this.semSystemPassword.acquire();
                this.showWindow();
            } catch (InterruptedException ex) {
                Logger.getLogger(SystemPassword.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
    }

}
