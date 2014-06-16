package cir.lab.csn.component;

import cir.lab.csn.component.thread.DataPublisherThread;
import cir.lab.csn.component.thread.DataSubscriberThread;
import cir.lab.csn.metadata.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class SensorNetworkDataAgent {
    private DataSubscriberThread subsThread;
    private DataPublisherThread pubThread;
    private BlockingQueue<SensorData> queue;

    private static final String pubThreadName = "Sensor Data Publisher";
    private static final String subThreadName = "Sensor Data Subscriber";

    Logger logger = LoggerFactory.getLogger(SensorNetworkDataAgent.class);

    public SensorNetworkDataAgent() {
        queue = new ArrayBlockingQueue<SensorData>(1024);
    }

    protected int initSensorNetworkDataAgent() {
        logger.info("Initializing Thread Instances");
        this.initSubscriberThread();
        this.initPublisherThread();
        return 0;
    }

    protected int startSensorNetworkDataAgentThreads() {
        logger.info("Starting Thread Instances");
        this.startSubscriberThread();
        this.startPublisherThread();
        return 0;
    }

    protected int restartSensorNetworkDataAgentThreads() {
        return 0;
    }

    protected int stopSensorNetworkDataAgentThreads() {
        logger.info("Stopping Thread Instances");
        this.stopSubscriberThread();
        this.stopPublisherThread();
        return 0;
    }

    protected int initSubscriberThread() {
        subsThread = new DataSubscriberThread(subThreadName, queue);
        return 0;
    }

    protected int initPublisherThread() {
        pubThread = new DataPublisherThread(pubThreadName, queue);
        return 0;
    }

    protected int startPublisherThread() {
        pubThread.start();
        return 0;
    }

    protected int startSubscriberThread() {
        subsThread.start();
        return 0;
    }

    protected int stopPublisherThread() {
        pubThread.setStopped(true);
        return 0;
    }

    protected int stopSubscriberThread() {
        subsThread.setStopped(true);
        return 0;
    }

    protected int getSensorNetworkManagerStatusInternal() {
        return 0;
    }
}
