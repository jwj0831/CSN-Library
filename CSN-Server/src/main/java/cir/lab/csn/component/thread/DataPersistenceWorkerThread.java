package cir.lab.csn.component.thread;

import cir.lab.csn.metadata.SensorData;
import cir.lab.csn.data.db.CSNDAOFactory;
import cir.lab.csn.data.dao.SensorDataPersistenceDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.eclipse.paho.client.mqttv3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by nfm on 2014. 5. 20..
 */
public class DataPersistenceWorkerThread extends Thread implements MqttCallback {
    Logger logger = LoggerFactory.getLogger(DataPersistenceWorkerThread.class);


    private MqttClient myClient;
    private ObjectMapper jsonMapper;
    private SensorDataPersistenceDAO dao;
    private static final boolean MQTT_CLEAN_SESSION_OPT = true;
    private static final int MQTT_KEEP_ALIVE_INTERVAL_OPT = 30;
    private static final int MQTT_QOS_OPT = 1;
    private static final String MQTT_BROKER_URL_OPT = "tcp://localhost:1883";
    private static final String MQTT_CLIENT_ID = "Persistence-Node";
//    private static final String MQTT_SUBS_TOPIC = "CSN/CENTRAL/DATA";


//    private final String connectionUri = "tcp://localhost:61616";
//    private ActiveMQConnectionFactory connectionFactory;
//    private Connection connection;
//    private Session session;
//    private Destination destination;
    static final String HISTORICAL_DATA_TOPIC = "CSN/SINGLE/PERSIST";
//    private MessageConsumer consumer;

    private volatile boolean stopped = false;

    public DataPersistenceWorkerThread(String name) {
        super(name);
        jsonMapper = new ObjectMapper();
        dao = new CSNDAOFactory().sensorDataPersistenceDAO();
    }

    public void before() throws Exception {
        logger.info("Connecting to Messaging Queue");
//        connectionFactory = new ActiveMQConnectionFactory(connectionUri);
//        connection = connectionFactory.createConnection();
//        connection.start();
//        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//        destination = session.createTopic(TEST_TOPIC);

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
        logger.info("Connected to {}", MQTT_BROKER_URL_OPT);
    }

    public void after() throws Exception {
//        if (connection != null) {
//            connection.close();
//            consumer.close();
//        }
        myClient.disconnect();
    }

    @Override
    public void run() {
        try {
            before();

//            consumer = session.createConsumer(destination);
//            consumer.setMessageListener(new EventListener());

            myClient.subscribe(HISTORICAL_DATA_TOPIC, MQTT_QOS_OPT);
            while( !isStopped() ) {
            }
            logger.info("Disconnecting to MQ");
            after();
            logger.info("Thread will be stopped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void connectionLost(Throwable throwable) {
        // TODO Need to be implemented for Connection Lost Error
        logger.warn("Connection lost!");
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        String data = mqttMessage.toString();
        SensorData sensorData = jsonMapper.readValue(data, SensorData.class);
        dao.add(sensorData);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

//    public static class EventListener implements MessageListener {
//        Logger logger = LoggerFactory.getLogger(EventListener.class);
//        ObjectMapper objectMapper = new ObjectMapper();
//        private SensorDataPersistenceDAO dao;
//
//        public EventListener() {
//            dao = new CSNDAOFactory().sensorDataPersistenceDAO();
//        }
//
//        public void onMessage(Message message) {
//            try {
//                String snsrURI = message.getStringProperty("id");
//                String timestamp = message.getStringProperty("time");
//                String val = message.getStringProperty("val");
//                logger.info("Sensor ID: {} timestamp: {} val: {}", snsrURI, timestamp, val);
//
//
//                SensorData sensorData = objectMapper.readValue(message, SensorData.class);
//                dao.add(sensorData);
//                logger.info("Add Sensor Data to DB");
//            } catch (Exception e) {
//                logger.warn("Worker caught an Exception");
//            }
//        }
//    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
