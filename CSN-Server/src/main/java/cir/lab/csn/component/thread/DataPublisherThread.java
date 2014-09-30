package cir.lab.csn.component.thread;

import cir.lab.csn.metadata.SensorData;
import cir.lab.csn.data.SensorNetworkList;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.BlockingQueue;

/**
 * Created by nfm on 2014. 5. 20..
 */
public class DataPublisherThread extends Thread implements MqttCallback {
    Logger logger = LoggerFactory.getLogger(DataPublisherThread.class);

    MqttClient myClient;
    MqttConnectOptions connOpt;
    private ObjectMapper jsonMapper;

    protected BlockingQueue queue;
    static final String CLIENT_ID = "Publisher-Node";


    private static final String CONN_URI = "vm://localhost:1883";
    static final String BROKER_URL = "tcp://localhost:1883";
//    private ActiveMQConnectionFactory connectionFactory;
//    private Connection connection;
//    private Session session;
//    private Destination destination;

    private volatile boolean stopped = false;

    public DataPublisherThread(String name, BlockingQueue queue) {
        super(name);
        this.queue = queue;
    }

    private void before() throws Exception {
        logger.info("Connecting to Messaging Queue");
//        connectionFactory = new ActiveMQConnectionFactory(CONN_URI);
//        connection = connectionFactory.createConnection();
//        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // Setup MQTT Client
        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);

        // Connect to Broker
        try {
            myClient = new MqttClient(BROKER_URL, CLIENT_ID);
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        logger.info("Connected to " + BROKER_URL);
    }

    public void after() throws Exception {
        logger.info("Closing Connection");
//        if (connection != null) {
//            connection.close();
//        }

        // disconnect
        try {
            myClient.disconnect();
            System.out.println("Close Connection");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectionLost(Throwable t) {
        System.out.println("Connection lost!");
    }

    public void deliveryComplete(MqttDeliveryToken arg0) {
    }

    public void messageArrived(String arg0, MqttMessage arg1) throws Exception {
    }

    public void deliveryComplete(IMqttDeliveryToken arg0) {
    }

    @Override
    public void run() {
        try {
            before();
            while( !isStopped() ) {
                publishData();
            }
            after();
            logger.info("Thread will be stopped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /* TODO 논문에서 확실히 해두어야 할 부분
     * 센서네트워크 정보의 갱신 후에 어디까지 센서데이터를 전송할 것인지? 즉 트랜잭션, 아토미시티에 대한 명확한 정의를 설명해 주자
     */
    private void publishData() throws JMSException {
        SensorData sensorData = null;

        if( queue.peek() != null ) {
            logger.info("Sensor Data Pop");
            sensorData = (SensorData) queue.poll();

            Iterator<String> iter = SensorNetworkList.getSensorNetworkListMap().keySet().iterator();

            while (iter.hasNext()) {
                String snTopicName = (String) iter.next();
                Set<String> sensorSet = SensorNetworkList.getSensorNetworkListMap().get(snTopicName);
                if (sensorSet.contains(sensorData.getId())) {
                    logger.info("Current Sensor Data is a member of {}", snTopicName);
//                    if(snTopicName.substring(0,10).compareTo("CSN.SINGLE") == 0)
//                        snTopicName = "CSN.SINGLE.PERSIST";

                    String mqttTopicName = snTopicName.replace('.', '/');
                    // Setup topic
                    MqttTopic topic = myClient.getTopic(mqttTopicName);
                    jsonMapper = new ObjectMapper();

                    String jsonStr = null;
                    try {
                        jsonStr = jsonMapper.writeValueAsString(sensorData);
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Pub MSG: " + jsonStr);

                    int pubQoS = 0;
                    MqttMessage message = new MqttMessage(jsonStr.getBytes());
                    message.setQos(pubQoS);
                    message.setRetained(false);
                    MqttDeliveryToken token = null;
                    try {
                        // publish message to broker
                        token = topic.publish(message);
                        // Wait until the message has been delivered to the broker
                        token.waitForCompletion();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    destination = session.createTopic(snTopicName);
//                    MessageProducer producer = session.createProducer(destination);
//                    Message message = session.createMessage();
//                    message.setStringProperty("id", sensorData.getId());
//                    message.setStringProperty("time", sensorData.getTime());
//                    message.setStringProperty("val", sensorData.getVal());
//                    logger.info("Send to Network: {}", snTopicName);
//                    logger.info("Sensor ID: {} timestamp: {} val: {}", sensorData.getId(), sensorData.getTime(), sensorData.getVal());
//                    producer.send(message);
//                    producer.close();
                }
            }
        }
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }


}
