package cir.lab.test2;

import cir.lab.CommonTime;
import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.paho.client.mqttv3.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Receiver2 implements MqttCallback {
    private MqttClient myClient;
    private static final boolean MQTT_CLEAN_SESSION_OPT = true;
    private static final int MQTT_KEEP_ALIVE_INTERVAL_OPT = 30;
    private static final int MQTT_QOS_OPT = 1;
    private static final String MQTT_BROKER_URL_OPT = "tcp://117.16.146.55:1883";
    private static final String MQTT_CLIENT_ID = "Receiver-Node-2";
    private static final String MQTT_RCV_TOPIC = "CSN/MULTI/FirstNet/1403703692671";
    private StopWatch stopWatch;
    private BufferedWriter bw;
    private int sleepTime = CommonTime.SLEEP_TIME;
    private int msgCount;

    public Receiver2() {
        try {
            bw = new BufferedWriter(new FileWriter("rcv2.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopWatch = new StopWatch();
        msgCount = 0;
    }

    public static void main(String[] args) {
        Receiver2 rcv = new Receiver2();
        System.out.println("Start to receive MSG");
        rcv.receive();
        System.out.println("Finish to receive MSG");
    }

    public void receive() {
        // setup MQTT Client
        MqttConnectOptions connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(MQTT_CLEAN_SESSION_OPT);
        connOpt.setKeepAliveInterval(MQTT_KEEP_ALIVE_INTERVAL_OPT);

        // Connect to Broker
        try {
            myClient = new MqttClient(MQTT_BROKER_URL_OPT, MQTT_CLIENT_ID);
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        // Subscribe to topic if subscriber
        try {
            stopWatch.start();
            myClient.subscribe(MQTT_RCV_TOPIC, MQTT_QOS_OPT);
            Thread.sleep(sleepTime);
            myClient.disconnect();
            bw.close();
        }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String data = message.toString();
        msgCount++;
        System.out.println("[RCV2] Subscribed MSG " + msgCount + " : " + data);
        stopWatch.split();
        String testTime = msgCount + "," + stopWatch.getTime();
        if(msgCount == 50 || msgCount == 100) {
            System.out.println("Write");
            bw.write(testTime);
            bw.newLine();
        }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
    }
}

