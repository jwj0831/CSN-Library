package cir.lab.csn.cli;

import cir.lab.csn.client.BrokerManager;
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
    private BrokerManager brokerManager;
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
        brokerManager = service.getBrokerManager();
    }

    private void startCLIInterface() {
        int commandType = 0;
        boolean continue_flag = true;
        do {
            System.out.println("----Command Choose----");
            System.out.println("1. Log out");
            System.out.println("2. Create Sensor Meta");
            System.out.println("3. Create Sensor Network");
            System.out.println("4. Get Broker Status");
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
                case 4:
                    System.out.println("Broker Total Enqueue #: " + brokerManager.getTotalEnqueueCount());
                    System.out.println("Broker Total Dequeue #: " + brokerManager.getTotalDequeueCount());
                    System.out.println("Broker Total Consumer #: " + brokerManager.getTotalConsumerCount());
                    System.out.println("Broker Total Producer #: " + brokerManager.getTotalProducerCount());
                    System.out.println("Broker Storage %: " + brokerManager.getStoreUsagePercentage());
                    System.out.println("Broker Memory %: " + brokerManager.getMemoryUsagePercentage());
                    System.out.println("Total Topic Subs: " + brokerManager.getTotalTopicSubscribers());
                    break;
                case 5:
                    Set<Map<String, Object>> enqSet = brokerManager.getTopicEnqueueCount();
                    for(Map<String, Object> obj : enqSet)
                        System.out.println("Topic '" + obj.get("topicName") + "' Enq. Num: " + obj.get("num"));
                    break;
                case 6:
                    Set<Map<String, Object>> deqSet = brokerManager.getTopicDequeueCount();
                    for(Map<String, Object> obj : deqSet)
                        System.out.println("Topic '" + obj.get("topicName") + "' Deq. Num: " + obj.get("num"));
                    break;
                case 7:
                    Map<String, Long> conMap = brokerManager.getTopicConsumerCount();
                    for(String key : conMap.keySet())
                        System.out.println("Topic '" + key +"' Con. Num: " + conMap.get(key));
                    break;
                case 8:
                    Map<String, Set<String>> subMap = brokerManager.getTopicSubscribers();
                    for(String key : subMap.keySet())
                        for(String subs : subMap.get(key))
                            System.out.println("Topic '" + key +"' Subscriber: " + subs);
                    break;
                case 9:
                    System.out.println("Currnet Health Status :" + brokerManager.getCurrentHealthStatus());
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
