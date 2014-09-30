package cir.lab.csn.component.thread;

import cir.lab.csn.data.db.MongoDBConnectionMaker;
import cir.lab.csn.metadata.SensorData;
import cir.lab.csn.data.db.CSNDAOFactory;
import cir.lab.csn.data.dao.SensorDataPersistenceDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.util.JSON;
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
    private DB mongo;

    private static final boolean MQTT_CLEAN_SESSION_OPT = true;
    private static final int MQTT_KEEP_ALIVE_INTERVAL_OPT = 30;
    private static final int MQTT_QOS_OPT = 1;
    private static final String MQTT_BROKER_URL_OPT = "tcp://localhost:1883";
    private static final String MQTT_CLIENT_ID = "Persistence-Node";
    static final String HISTORICAL_DATA_TOPIC = "CSN/SINGLE/>";

    private volatile boolean stopped = false;

    public DataPersistenceWorkerThread(String name) {
        super(name);
        jsonMapper = new ObjectMapper();
        dao = new CSNDAOFactory().sensorDataPersistenceDAO();
        mongo = new MongoDBConnectionMaker().getMongoDB();
    }

    public void before() throws Exception {
        logger.info("Connecting to Messaging Queue");

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
        myClient.disconnect();
    }

    @Override
    public void run() {
        try {
            before();
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

        DBCollection userTable = mongo.getCollection("data");
        DBObject dbObject = (DBObject) JSON.parse(data);
        userTable.insert(dbObject);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}
