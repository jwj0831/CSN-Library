package cir.lab.csn.data.db;

import cir.lab.csn.data.dao.*;

/**
 * Created by nfm on 2014. 5. 20..
 */
public class CSNDAOFactory {
    public SensorDataPersistenceDAO sensorDataDAO() {
        return new SensorDataPersistenceDAO(connectionMaker());
    }

    public SensorMetadataDAO sensorMetadataDAO() {
        return new SensorMetadataDAO(connectionMaker());
    }

    public SensorNetworkMetadataDAO sensorNetworkMetadataDAO() {
        return new SensorNetworkMetadataDAO(connectionMaker());
    }

    public SensorDataPersistenceDAO sensorDataPersistenceDAO() {
        return new SensorDataPersistenceDAO(connectionMaker());
    }

    public SearchDAO searchDAO() {
        return new SearchDAO(connectionMaker());
    }

    public AuthCheckDAO authCheckDAO() {
        return new AuthCheckDAO(connectionMaker());
    }

    private ConnectionMaker connectionMaker() {
        return new JDBCConnectionMaker();
    }
}
