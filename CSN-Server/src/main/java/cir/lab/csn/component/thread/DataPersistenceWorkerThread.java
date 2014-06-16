package cir.lab.csn.component.thread;

import cir.lab.csn.metadata.SensorData;
import cir.lab.csn.data.db.CSNDAOFactory;
import cir.lab.csn.data.dao.SensorDataPersistenceDAO;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;

/**
 * Created by nfm on 2014. 5. 20..
 */
public class DataPersistenceWorkerThread extends Thread {
    Logger logger = LoggerFactory.getLogger(DataPersistenceWorkerThread.class);

    private final String connectionUri = "tcp://localhost:61616";
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;
    static final String TEST_TOPIC = "CSN.SINGLE.PERSIST";
    private MessageConsumer consumer;

    private volatile boolean stopped = false;

    public DataPersistenceWorkerThread(String name) {
        super(name);
    }

    public void before() throws Exception {
        logger.info("Connecting to Messaging Queue");
        connectionFactory = new ActiveMQConnectionFactory(connectionUri);
        connection = connectionFactory.createConnection();
        connection.start();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createTopic(TEST_TOPIC);
    }

    public void after() throws Exception {
        if (connection != null) {
            connection.close();
            consumer.close();
        }
    }

    @Override
    public void run() {
        try {
            before();
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(new EventListener());
            while( !isStopped() ) {
            }
            logger.info("Disconnecting to MQ");
            after();
            logger.info("Thread will be stopped");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class EventListener implements MessageListener {
        Logger logger = LoggerFactory.getLogger(EventListener.class);
        private SensorDataPersistenceDAO dao;

        public EventListener() {
            dao = new CSNDAOFactory().sensorDataPersistenceDAO();
        }

        public void onMessage(Message message) {
            try {
                String snsrURI = message.getStringProperty("uri");
                String timestamp = message.getStringProperty("time");
                String val = message.getStringProperty("val");
                logger.info("Sensor ID: {} timestamp: {} val: {}", snsrURI, timestamp, val);
                SensorData sensorData = new SensorData(snsrURI, timestamp, val);
                dao.add(sensorData);
                logger.info("Add Sensor Data to DB");
            } catch (Exception e) {
                logger.warn("Worker caught an Exception");
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
