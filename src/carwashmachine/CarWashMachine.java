package carwashmachine;

import CoinAcceptor.CoinAcceptor;
import FileHandling.LogsFile;
import FileHandling.ReadConfigFromFile;
import FileHandling.StatusFromFile;
import FileHandling.StatusToFile;
import PopUps.Exchange;
import PopUps.SystemPassword;
import Status.CarTreadmillStatus;
import Status.InterfaceBuffer;
import Status.SprinklersAndDryersStatus;
import Status.SystemStatus;
import Status.WashRollersStatus;
import enums.EnumCarTreadmillStatus;
import enums.EnumSprinklersAndDryersStatus;
import enums.EnumWashRollersStatus;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.concurrent.Semaphore;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Simão
 */
public class CarWashMachine {

    private static void IButtonPressed(CoinAcceptor coinAcceptor,
            JLabel balanceLabel, Semaphore semCarsQueue, JLabel carsQueueLabel,
            DecimalFormat df) {

        Thread exchange = new Thread(new Exchange(coinAcceptor.payCarWash()));
        exchange.start();

        balanceLabel.setText("Balance: " + String.valueOf(
                df.format(coinAcceptor.getBalance())));

        semCarsQueue.release();
        carsQueueLabel.setText(String.valueOf("Cars in Queue: "
                + semCarsQueue.availablePermits()));
    }

    private static void CButtonPressed(CoinAcceptor coinAcceptor,
            JLabel balanceLabel, DecimalFormat df) {

        Thread exchange = new Thread(new Exchange(coinAcceptor
                .returnCurrentInsertion()));

        exchange.start();
        balanceLabel.setText("Balance: " + String.valueOf(
                df.format(coinAcceptor.getBalance())));
    }

    private static void changesStatusWhenEmergencyOn(
            CarTreadmillStatus carTreadmillStatus,
            WashRollersStatus washRollersStatus,
            SprinklersAndDryersStatus sprinklersAndDryersStatus, JLabel tLabel,
            JLabel sLabel, JLabel wLabel) {

        StatusToFile statusToFile = new StatusToFile("Status.txt");
        statusToFile.writeStatusToFile(carTreadmillStatus
                .getCarTreadmillStatus().toString());

        statusToFile.writeStatusToFile(washRollersStatus.getWashRollersStatus()
                .toString());

        statusToFile.writeStatusToFile(sprinklersAndDryersStatus
                .getSprinklersAndDryersStatus().toString());

        carTreadmillStatus.setCarTreadmillStatus(EnumCarTreadmillStatus.STOPPED);
        washRollersStatus.setWashRollersStatus(EnumWashRollersStatus.STOPPED);
        sprinklersAndDryersStatus.setSprinklersAndDryersStatus(
                EnumSprinklersAndDryersStatus.STOPPED);

        tLabel.setText(carTreadmillStatus.getCarTreadmillStatus().toString());
        wLabel.setText(washRollersStatus.getWashRollersStatus().toString());
        sLabel.setText(sprinklersAndDryersStatus.getSprinklersAndDryersStatus()
                .toString());
    }

    private static void changesStatusWhenEmergencyOff(
            CarTreadmillStatus carTreadmillStatus,
            WashRollersStatus washRollersStatus,
            SprinklersAndDryersStatus sprinklersAndDryersStatus, JLabel tLabel,
            JLabel sLabel, JLabel wLabel) {

        StatusFromFile statusFromFile = new StatusFromFile("Status.txt");

        carTreadmillStatus.setCarTreadmillStatus(statusFromFile
                .getCarTreadmillStatus());

        washRollersStatus.setWashRollersStatus(statusFromFile
                .getWashRollersStatus());

        sprinklersAndDryersStatus.setSprinklersAndDryersStatus(
                statusFromFile.getSprinklersAndDryersStatus());

        tLabel.setText(carTreadmillStatus.getCarTreadmillStatus().toString());
        wLabel.setText(washRollersStatus.getWashRollersStatus().toString());
        sLabel.setText(sprinklersAndDryersStatus.getSprinklersAndDryersStatus()
                .toString());
    }

    private static void EButtonPressed(Semaphore semEmergencyButton,
            Thread carTreadmill, Thread washRollers, Thread sprinklersAndDryers,
            JLabel emergencyLabel, LogsFile logs,
            CarTreadmillStatus carTreadmillStatus,
            WashRollersStatus washRollersStatus,
            SprinklersAndDryersStatus sprinklersAndDryersStatus, JLabel tLabel,
            JLabel sLabel, JLabel wLabel) {

        if (semEmergencyButton.availablePermits() == 0) {
            semEmergencyButton.release();
            logs.writeToLogsFile(LocalDateTime.now()
                    + " Sistema em Emergência");

            emergencyLabel.setText("Emergency: ON");
            changesStatusWhenEmergencyOn(carTreadmillStatus, washRollersStatus,
                    sprinklersAndDryersStatus, tLabel, sLabel, wLabel);

            carTreadmill.suspend();
            washRollers.suspend();
            sprinklersAndDryers.suspend();

        } else if (semEmergencyButton.availablePermits() == 1) {
            try {
                semEmergencyButton.acquire();
                logs.writeToLogsFile(LocalDateTime.now()
                        + " Fim de sistema em Emergência");

                emergencyLabel.setText("Emergency: OFF");
                changesStatusWhenEmergencyOff(carTreadmillStatus, washRollersStatus,
                        sprinklersAndDryersStatus, tLabel, sLabel, wLabel);

                carTreadmill.resume();
                washRollers.resume();
                sprinklersAndDryers.resume();

            } catch (InterruptedException ex) {
                Logger.getLogger(CarWashMachine.class.getName()).log(
                        Level.SEVERE, null, ex);
            }
        }
    }

    /**
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        //Formatter
        DecimalFormat df = new DecimalFormat("#,###.00");

        //Semaphores
        Semaphore semButtonPressed = new Semaphore(0);
        Semaphore semCoinAcceptor = new Semaphore(0);
        Semaphore semTreadmill = new Semaphore(0);
        Semaphore semWashRollers = new Semaphore(0);
        Semaphore semSprinklersAndDryers = new Semaphore(0);
        Semaphore semCarsQueue = new Semaphore(0);
        Semaphore carQueueFlag = new Semaphore(0);
        Semaphore semEmergencyButton = new Semaphore(0);
        Semaphore semSystemStatus = new Semaphore(0);
        Semaphore semSystemPassword = new Semaphore(0);
        Semaphore semReset = new Semaphore(0);

        //FileHandling
        ReadConfigFromFile config = new ReadConfigFromFile("config.txt");
        LogsFile logs = new LogsFile("logs.txt");

        //SharedObjects
        InterfaceBuffer buffer = new InterfaceBuffer();
        CarTreadmillStatus carTreadmillStatus = new CarTreadmillStatus();
        WashRollersStatus washRollersStatus = new WashRollersStatus();
        SprinklersAndDryersStatus sprinklersAndDryersStatus
                = new SprinklersAndDryersStatus();

        SystemStatus systemStatus = new SystemStatus();
        CoinAcceptor coinAcceptor = new CoinAcceptor();
        JFrame iWindow = new JFrame("Interface");
        JFrame tWindow = new JFrame("CarTreadmill");
        JLabel tLabel = new JLabel();
        tLabel.setSize(500, 500);
        JFrame sWindow = new JFrame("SprinklersAndDryers");
        JLabel sLabel = new JLabel();
        sLabel.setSize(500, 500);
        JFrame wWindow = new JFrame("WashRollers");
        JLabel wLabel = new JLabel();
        JLabel balanceLabel = new JLabel();
        JLabel carsQueueLabel = new JLabel();
        JLabel SystemStatusLabel = new JLabel();
        JLabel emergencyLabel = new JLabel();

        //Instanciar Threads
        Thread myInterface = new Thread(new Interface(semButtonPressed, buffer,
                coinAcceptor, systemStatus, iWindow, balanceLabel, carsQueueLabel,
                SystemStatusLabel, emergencyLabel,
                semCarsQueue.availablePermits(), config.getCustoLavagem(), df));

        Thread tCoinAcceptor = new Thread(new TCoinAcceptor(semCoinAcceptor,
                coinAcceptor, balanceLabel, df));

        Thread carTreadmill = new Thread(new CarTreadmill(semTreadmill,
                semSprinklersAndDryers, carQueueFlag, carTreadmillStatus,
                tWindow, tLabel, config.getTempoMovimentoTapete(), logs));

        Thread washRollers = new Thread(new WashRollers(semWashRollers,
                semSprinklersAndDryers, washRollersStatus, wWindow,
                wLabel, config.getTempoMovimentoRolos(), logs));

        Thread sprinklersAndDryers = new Thread(new SprinklersAndDryers(
                semSprinklersAndDryers, semWashRollers, semTreadmill,
                sprinklersAndDryersStatus, sWindow, sLabel,
                config.getTempoMovimentoAspersores(), config.getTempoSecagem(),
                logs));

        Thread carsQueue = new Thread(new CarsQueue(semCarsQueue,
                semTreadmill, carQueueFlag, carsQueueLabel));

        Thread systemPassword = new Thread(new SystemPassword(semSystemPassword,
                semSystemStatus, semReset, semCarsQueue, systemStatus,
                SystemStatusLabel, carsQueueLabel, logs));

        //iniciar Threads
        myInterface.start();
        tCoinAcceptor.start();
        carsQueue.start();
        carTreadmill.start();
        washRollers.start();
        sprinklersAndDryers.start();
        systemPassword.start();

        while (true) {
            semButtonPressed.acquire();

            if (buffer.getBotao().equals("I") && coinAcceptor.hasEnough()
                    && semSystemStatus.availablePermits() == 1) {

                logs.writeToLogsFile(LocalDateTime.now() + " Carro adicionado à"
                        + " lista de espera");

                IButtonPressed(coinAcceptor, balanceLabel, semCarsQueue,
                        carsQueueLabel, df);

            } else if (buffer.getBotao().equals("Insert Coin")) {
                semCoinAcceptor.release();

            } else if (buffer.getBotao().equals("A")
                    && semSystemStatus.availablePermits() == 0) {

                semSystemPassword.release();

            } else if (buffer.getBotao().equals("F")
                    && semSystemStatus.availablePermits() == 1) {

                semSystemPassword.release();

            } else if (buffer.getBotao().equals("C")) {
                CButtonPressed(coinAcceptor, balanceLabel, df);

            } else if (buffer.getBotao().equals("E")) {
                EButtonPressed(semEmergencyButton, carTreadmill, washRollers,
                        sprinklersAndDryers, emergencyLabel, logs,
                        carTreadmillStatus, washRollersStatus,
                        sprinklersAndDryersStatus, tLabel, sLabel, wLabel);

            } else if (buffer.getBotao().equals("R")) {
                semSystemPassword.release();
                semReset.release();
            }
        }
    }

}
