package cir.lab.csn.component;

import cir.lab.csn.component.thread.DataPersistenceWorkerThread;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nfm on 2014. 5. 28..
 */
public class HistoricalDataManager {
    private DataPersistenceWorkerThread persistenceWorkerThread;

    private static final String threadName = "Sensor Data Persistence Worker";


    Logger logger = LoggerFactory.getLogger(HistoricalDataManager.class);

    protected int initHistoricalDataManager() {
        logger.info("Initialize Historical Data Manager");
        persistenceWorkerThread = new DataPersistenceWorkerThread(threadName);
        return 0;
    }

    protected int startPersistenceWorkerThread() {
        logger.info("Start Historical Data Manager");
        persistenceWorkerThread.start();
        return 0;
    }

    protected int restartPersistenceWorkerThread() {
        return 0;
    }

    protected int stopPersistenceWorkerThread() {
        logger.info("Stop Historical Data Manager");
        persistenceWorkerThread.setStopped(true);
        return 0;
    }
}
