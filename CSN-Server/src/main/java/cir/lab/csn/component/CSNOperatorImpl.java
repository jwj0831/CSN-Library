package cir.lab.csn.component;

import cir.lab.csn.client.BrokerManager;
import cir.lab.csn.client.CSNOperator;
import cir.lab.csn.metadata.csn.CSNConfigMetadata;
import cir.lab.csn.data.SensorNetworkList;
import cir.lab.csn.client.SensorNetworkManager;
import cir.lab.csn.util.TimeGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSNOperatorImpl implements CSNOperator {
    private CSNConfigMetadata csnConfigMetadata;
    private SensorNetworkManager sensorNetworkManager;
    private BrokerManager brokerManager;
    private SensorNetworkDataAgent dataAgent;
    private HistoricalDataManager historicalDataManager;

    Logger logger = LoggerFactory.getLogger(CSNOperatorImpl.class);

    public CSNOperatorImpl(String name){
        csnConfigMetadata = new CSNConfigMetadata(name);
    }

    public CSNOperatorImpl(String name, CSNConfigMetadata configMetadata){
        csnConfigMetadata = configMetadata;
        csnConfigMetadata.setName(name);
        csnConfigMetadata.setCreationTime(TimeGeneratorUtil.getCurrentTimestamp());
}

    @Override
    public int initCSN() {
        logger.info("Creating Modules");
        createSubModuleInstance();

        logger.info("Initializing Modules");

        dataAgent.initSensorNetworkDataAgent();
        historicalDataManager.initHistoricalDataManager();

        SensorNetworkList.setSensorNetworkListMap(sensorNetworkManager.getAllSNTopicNameAndTheirMemberIDs());
        return 0;
    }

    private void createSubModuleInstance() {
        this.setSensorNetworkManager(new SensorNetworkManagerImpl());
        this.setBrokerManager(new BrokerManagerImpl());
        this.setDataAgent(new SensorNetworkDataAgent());
        this.setHistoricalDataManager(new HistoricalDataManager());
    }

    @Override
    public int startSystem() {
        logger.info("Setting Broker Configuration");
        brokerManager.setBrokerConfiguration("xbean:activemq.xml");

        logger.info("Starting Broker");
        brokerManager.startBroker();

        try {
            int waitSec = 1;
            logger.info("Waiting {} secs...", waitSec);
            Thread.sleep( waitSec * 1000 );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Starting Sensor Network Manager");
        dataAgent.startSensorNetworkDataAgentThreads();
        historicalDataManager.startPersistenceWorkerThread();
        return 0;
    }

    @Override
    public int restartSystem() {
        logger.info("Restarting Sensor Network Manager");
        stopSystem();

        try {
            int waitSec = 1;
            logger.info("Waiting {} secs...", waitSec);
            Thread.sleep( waitSec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        startSystem();
        return 0;
    }

    @Override
    public int stopSystem() {
        logger.info("Stopping Sensor Network Manager");
        dataAgent.stopSensorNetworkDataAgentThreads();
        historicalDataManager.stopPersistenceWorkerThread();
        try {

            int waitSec = 1;
            logger.info("Waiting {} secs...", waitSec);
            Thread.sleep( waitSec * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Stopping Broker");
       brokerManager.stopBroker();
        return 0;
    }

    @Override
    public CSNConfigMetadata getCsnConfigMetadata() {
        return csnConfigMetadata;
    }

    @Override
    public SensorNetworkManager getSensorNetworkManager() {
        return sensorNetworkManager;
    }

    @Override
    public BrokerManager getBrokerManager() {
        return brokerManager;
    }

    public void setSensorNetworkManager(SensorNetworkManager sensorNetworkManager) {
        this.sensorNetworkManager = sensorNetworkManager;
    }

    public void setBrokerManager(BrokerManager brokerManager) {
        this.brokerManager = brokerManager;
    }

    public void setDataAgent(SensorNetworkDataAgent dataAgent) {
        this.dataAgent = dataAgent;
    }

    public void setHistoricalDataManager(HistoricalDataManager historicalDataManager) {
        this.historicalDataManager = historicalDataManager;
    }
}
