package cir.lab.csn.data.dao;

import cir.lab.csn.data.db.ConnectionMaker;
import cir.lab.csn.data.DAOReturnType;
import cir.lab.csn.metadata.SensorData;
import cir.lab.csn.exception.ExceptionProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class SensorDataPersistenceDAO {
    Logger logger = LoggerFactory.getLogger(SensorDataPersistenceDAO.class);

    private static final String SNSR_PERSIST_TABLE_NM = "csn_snsr_persistence";

    private ConnectionMaker connectionMaker;

    public SensorDataPersistenceDAO(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public DAOReturnType add(SensorData sensorData) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + SNSR_PERSIST_TABLE_NM + "(snsr_id, snsr_time, snsr_val) VALUES(?, ?, ?)");

            ps.setString(1, sensorData.getUri());
            ps.setString(2, sensorData.getTime());
            ps.setString(3, sensorData.getVal());
            ps.executeUpdate();

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }
}
