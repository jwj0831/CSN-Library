package cir.lab.csn.execute;

import cir.lab.csn.metadata.SensorData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
/**
 * Created by nfm on 2014. 5. 21..
 */
public class TestMQTTClient implements MqttCallback {
    MqttClient myClient;
    MqttConnectOptions connOpt;
    private ObjectMapper jsonMapper;

    static final String BROKER_URL = "tcp://localhost:1883";
    static final String NODE_ID = "/N1/1401539641807";
    static final String TOPIC_NAME = "CSN/CENTRAL/DATA";
    static final int MSG_NUM = 10;

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
        TestMQTTClient publisher = new TestMQTTClient();

        try {
            publisher.publishSensorData();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void publishSensorData() throws JsonProcessingException {
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

        // Setup topic
        String myTopic = TOPIC_NAME;
        MqttTopic topic = myClient.getTopic(myTopic);
        int randNum = 0;
        jsonMapper = new ObjectMapper();

        for (int i=1; i<=MSG_NUM; i++) {
            Calendar calendar = Calendar.getInstance();
            Date date = calendar.getTime();
            String today = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));

            Random oRandom = new Random();

            // 1~10까지의 정수를 랜덤하게 출력
            randNum = oRandom.nextInt(999) + 1;

            SensorData sensorData = new SensorData(NODE_ID, today, Integer.toString(randNum));
            String jsonStr = jsonMapper.writeValueAsString(sensorData);
            System.out.println("Pub MSG: " + jsonStr);

            int pubQoS = 0;
            MqttMessage message = new MqttMessage(jsonStr.getBytes());
            message.setQos(pubQoS);
            message.setRetained(false);

            // Publish the message
            //System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
            MqttDeliveryToken token = null;
            try {
                // publish message to broker
                token = topic.publish(message);
                // Wait until the message has been delivered to the broker
                token.waitForCompletion();
                Thread.sleep(500);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        // disconnect
        try {
            myClient.disconnect();
            System.out.println("Close Connection");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
