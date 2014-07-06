package cir.lab.csn.client;

import cir.lab.csn.component.BrokerManagerImpl;
import cir.lab.csn.component.HistoricalDataManager;
import cir.lab.csn.metadata.csn.CSNConfigMetadata;

/**
 * Created by nfm on 2014. 5. 20..
 */
public interface CSNOperator {
    /**
     * This method initialize all the component of CSN, which are BrokerManager, CSNMonitor,
     * and Metadata Controller.
     * @return If successfully done, it returns 1, or -1.
     */
    public int initCSN();

    /**
     * This method start the CSN system.
     * @return If successfully done, it returns 1, or -1.
     */
    public int startSystem();

    /**
     * This method restart the CSN system.
     * @return If successfully done, it returns 1, or -1.
     */
    public int restartSystem();

    /**
     * This method stop the CSN system.
     * @return If successfully done, it returns 1, or -1.
     */
    public int stopSystem();

    public boolean isCSNWorking();

    public CSNConfigMetadata getCsnConfigMetadata();

    public SensorNetworkManager getSensorNetworkManager();

    public BrokerManager getBrokerManager();

    public HistoricalDataManager getHistoricalDataManager();
}
