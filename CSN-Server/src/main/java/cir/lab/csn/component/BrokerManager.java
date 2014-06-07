package cir.lab.csn.component;

import org.apache.activemq.broker.BrokerFactory;
import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BrokerManager {
    private BrokerService service;

    Logger logger = LoggerFactory.getLogger(BrokerManager.class);

    protected int setBrokerConfiguration(String settings) {
        try {
            service = BrokerFactory.createBroker(settings);
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    protected int startBroker() {
        try {
            service.start();
            logger.info("Finish to start Broker");
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    protected int restartBroker() {
        return 0;
    }

    protected int stopBroker() {
        try {
            service.stop();
            logger.info("Finish to stop Broker");
            return 0;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /*
     * TODO Add Monitoring API
     * Monitoring API for Monitor Interface
     */
    protected int getBrokerStatusInternal() {
        return 0;
    }
}
