package cir.lab.csn.cli;

import cir.lab.csn.client.CSNOperator;
import cir.lab.csn.client.ServiceOperatorProvider;
import cir.lab.csn.client.SensorNetworkManager;

import java.util.*;

/**
 * Created by nfm on 2014. 5. 21..
 */
public class CLITestMain {
    private CSNOperator service;
    private SensorNetworkManager sensorNetworkManager;
    private Scanner sc;

    public static void main(String args[]) {
        CLITestMain system = new CLITestMain();
        system.startTest();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        system.startCLIInterface();
        system.service.stopSystem();
        System.out.println("Bye");
    }

    private void startTest() {
        service = ServiceOperatorProvider.getCSNCoreService("Test");
        service.initCSN();
        service.startSystem();
        sc = new Scanner(System.in);
        sensorNetworkManager = service.getSensorNetworkManager();
    }

    private void startCLIInterface() {
        int commandType = 0;
        boolean continue_flag = true;
        do {
            System.out.println("----Command Choose----");
            System.out.println("1. Log out");
            System.out.println("2. Create Sensor Meta");
            System.out.println("3. Create Sensor Network");
            System.out.print("Choose Command: ");
            commandType = sc.nextInt();
            sc.nextLine();

            switch (commandType) {
                case 1:
                    continue_flag = false;
                    break;
                case 2:
                    this.createSensorMeta();
                    break;
                case 3:
                    this.createSensorNetwork();
                    break;
                default:
                    System.out.println("Wrong Command");
                    break;
            }
        } while (continue_flag);
    }

    private void createSensorMeta() {
        String name = null;
        String measurement = null;
        System.out.println("Create Sensor Meta Mode");

        System.out.print("Sensor Name(Physical ID): ");
        name = sc.nextLine();

        System.out.print("Measurement Target: ");
        measurement = sc.nextLine();

        String uri = sensorNetworkManager.createSensorMetadata(name, measurement);
        if( uri != null )
            System.out.println("Finish DB Input");
        else
            System.out.println("Error");
    }

    private void createSensorNetwork() {
        String sn_name = null;
        String sensorName = null;
        Set<String> sensorURIs = new HashSet<String>();
        System.out.println("Create Sensor Network Mode");
        System.out.print("Sensor Network Name: ");
        sn_name = sc.nextLine();

        for(;;){
            System.out.print("Input Sensor(for quit to type 'x'): ");
            sensorName = sc.nextLine();
            if( sensorName.compareTo("x") == 0 )
                break;
            else {
                sensorURIs.add(sensorName);
            }
        }
        sensorNetworkManager.createSensorNetworkMetadata(sn_name, sensorURIs);
    }
}
