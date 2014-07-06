package cir.lab.csn.component;

import cir.lab.csn.component.thread.DataPersistenceWorkerThread;
import cir.lab.csn.data.dao.SearchDAO;
import cir.lab.csn.data.db.CSNDAOFactory;
import cir.lab.csn.metadata.SensorData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class HistoricalDataManager {
    private DataPersistenceWorkerThread persistenceWorkerThread;

    private static final String threadName = "Sensor Data Persistence Worker";
    private SearchDAO searchDAO;

    Logger logger = LoggerFactory.getLogger(HistoricalDataManager.class);

    public HistoricalDataManager() {
        searchDAO = new CSNDAOFactory().searchDAO();
    }

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

    public Set<String> getSensorIDsByTag(String tag) {
        return searchDAO.getSensorIDsByTag(tag);
    }

    public Set<String> getSensorNetworkIDsByTag(String tag) {
        return searchDAO.getSensorNetworkIDsByTag(tag);
    }

    public Set<String> getSensorIDsByTags(Set<String> tags) {
        return searchDAO.getSensorIDsByTags(tags);
    }

    public Set<String> getSensorNetworkIDsByTags(Set<String> tags) {
        return searchDAO.getSensorNetworkIDsByTags(tags);
    }

    public Set<SensorData> getHistoricalDataBySensorIDsWithNum(String id, int num) {
        return searchDAO.getHistoricalDataBySensorIDWithNum(id, num);
    }

    public Set<SensorData> getHistoricalDataBySensorIDsWithMin(String id, int min) {
        return searchDAO.getHistoricalDataBySensorIDsWithMin(id, min);
    }

    public Set<SensorData> getHistoricalDataBySensorNetworkIDsWithNum(String id, int num) {
        return  searchDAO.getHistoricalDataBySensorNetworkIDsWithNum(id, num);
    }

    public Set<SensorData> getHistoricalDataBySensorNetworkIDsWithMin(String id, int min) {
        return searchDAO.getHistoricalDataBySensorNetworkIDsWithMin(id, min);
    }
}
