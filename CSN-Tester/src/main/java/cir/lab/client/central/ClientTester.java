package cir.lab.client.central;
import cir.lab.csn.metadata.SensorData;
import cir.lab.rest.client.RestAPIFactory;
import cir.lab.rest.client.SensorAPI;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.time.StopWatch;
import org.eclipse.paho.client.mqttv3.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Set;

public class ClientTester implements MqttCallback {
    MqttClient myClient;
    MqttConnectOptions connOpt;
    private ObjectMapper jsonMapper;

    static final String BROKER_URL = "tcp://117.16.146.55:1883";

    static final String NODE_ID = "Node1-1403634195838";
    //static final String NODE_ID = "Node2-1403661725203";
    //static final String NODE_ID = "Node4-1403662105700";
    //static final String NODE_ID = "Node11-1403662835072";
    //static final String NODE_ID = "Node13-1403662872002";

    static final int MSG_NUM = 10;
    static final String TOPIC_NAME = "CSN/CENTRAL/DATA";

    private StopWatch stopWatch;
    private BufferedWriter bw;

    public void connectionLost(Throwable t) {
        System.out.println("Connection lost!");
    }

    public void deliveryComplete(MqttDeliveryToken arg0) {
    }

    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
    }

    public void deliveryComplete(IMqttDeliveryToken arg0) {
    }

    public static void main(String[] args) {
        ClientTester publisher = new ClientTester();
        try {
            publisher.publishSensorData();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void publishSensorData() throws JsonProcessingException {
        try {
            bw = new BufferedWriter(new FileWriter("centralMode.csv"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        stopWatch = new StopWatch();

        // Setup MQTT Client
        String clientID = NODE_ID;
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);

        // Connect to Broker
        try {
            myClient = new MqttClient(BROKER_URL, clientID);
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        System.out.println("Connected to " + BROKER_URL);

        int randNum = 0;
        jsonMapper = new ObjectMapper();
        stopWatch.start();
        for (int i = 1; i <= MSG_NUM; i++) {
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            String today = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));

            Random oRandom = new Random();

            // 1~10까지의 정수를 랜덤하게 출력
            randNum = oRandom.nextInt(999) + 1;

            SensorData sensorData = new SensorData(NODE_ID, today, Integer.toString(randNum));
            String jsonStr = jsonMapper.writeValueAsString(sensorData);
            //System.out.println("Pub MSG: " + jsonStr);

            int pubQoS = 0;
            MqttMessage message = new MqttMessage(jsonStr.getBytes());
            message.setQos(pubQoS);
            message.setRetained(false);

            // Publish the message
            //System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
            MqttDeliveryToken token = null;
            try {
                MqttTopic mqttTopic = myClient.getTopic(TOPIC_NAME);
                // publish message to broker
                token = mqttTopic.publish(message);
                // Wait until the message has been delivered to the broker
                token.waitForCompletion();
                //Thread.sleep(10);
                stopWatch.split();
                String testTime = i + "," + stopWatch.getTime();
                if(i == 50 || i == 100) {
                    bw.write(testTime);
                    bw.newLine();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // disconnect
        try {
            myClient.disconnect();
            bw.close();
            System.out.println("Close Connection");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
