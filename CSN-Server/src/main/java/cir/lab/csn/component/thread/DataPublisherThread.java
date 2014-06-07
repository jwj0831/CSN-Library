package cir.lab.csn.component.thread;

import cir.lab.csn.metadata.SensorData;
import cir.lab.csn.data.SensorNetworkList;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * Created by nfm on 2014. 5. 20..
 */
public class DataPublisherThread extends Thread {
    Logger logger = LoggerFactory.getLogger(DataPublisherThread.class);

    protected BlockingQueue queue;
    private static final String CONN_URI = "vm://localhost"; //"tcp://localhost:61616";
    private ActiveMQConnectionFactory connectionFactory;
    private Connection connection;
    private Session session;
    private Destination destination;

    private volatile boolean stopped = false;

    public DataPublisherThread(String name, BlockingQueue queue) {
        super(name);
        this.queue = queue;
    }

    private void before() throws Exception {
        logger.info("Connecting to Messaging Queue");
        connectionFactory = new ActiveMQConnectionFactory(CONN_URI);
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

    }

    public void after() throws Exception {
        logger.info("Closing Connection");
        if (connection != null) {
            connection.close();
        }
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
                if (sensorSet.contains(sensorData.getUri())) {
                    logger.info("Current Sensor Data is a member of {}", snTopicName);
                    if(snTopicName.substring(0,10).compareTo("CSN.SINGLE") == 0)
                        snTopicName = "CSN.SINGLE.PERSIST";
                    destination = session.createTopic(snTopicName);
                    MessageProducer producer = session.createProducer(destination);
                    Message message = session.createMessage();
                    message.setStringProperty("uri", sensorData.getUri());
                    message.setStringProperty("time", sensorData.getTime());
                    message.setStringProperty("val", sensorData.getVal());
                    logger.info("Send to Network: {}", snTopicName);
                    logger.info("Sensor ID: {} timestamp: {} val: {}", sensorData.getUri(), sensorData.getTime(), sensorData.getVal());
                    producer.send(message);
                    producer.close();
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
