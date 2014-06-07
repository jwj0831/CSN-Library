package cir.lab.csn.data.dao;

import cir.lab.csn.data.DAOReturnType;
import cir.lab.csn.data.db.ConnectionMaker;
import cir.lab.csn.exception.ExceptionProcessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by nfm on 2014. 6. 2..
 */
public class AuthcheckDAO {
    private static final String AUTH_KEY_TABLE_NM = "csn_auth_key";

    private ConnectionMaker connectionMaker;

    public AuthcheckDAO(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public DAOReturnType addKey(int snsrID, String key) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + AUTH_KEY_TABLE_NM + "(snsr_id, key) VALUES(?, ?)");

            ps.setInt(1, snsrID);
            ps.setString(2, key);
            ps.executeUpdate();

            ps.close();
            c.close();
        } catch(Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public boolean checkHasKey(String key) {
        boolean retVal = false;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT count(key) FROM " + AUTH_KEY_TABLE_NM + " WHERE key=?");

            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int num = rs.getInt(1);

            ps.close();
            c.close();

            if(num == 1)
                retVal = true;
        } catch(Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
        }

        return retVal;
    }

//    public boolean getUserKey(String uri) {
//        boolean retVal = false;
//        try {
//            int id = getIdOfSensorInDB(uri);
//            if(id < 1)
//                throw new Exception("Can't get Sensor ID in DB");
//
//            Connection c = connectionMaker.makeConnection();
//            PreparedStatement ps = c.prepareStatement("SELECT count(key) FROM " + AUTH_KEY_TABLE_NM + " WHERE key=?");
//
//            ps.setInt(1, key);
//            ResultSet rs = ps.executeQuery();
//            rs.next();
//            int num = rs.getInt("count(key)");
//
//            ps.close();
//            c.close();
//
//            if(num == 1)
//                retVal = true;
//        } catch(Exception e) {
//            ExceptionProcessor.handleException(e, this.getClass().getName());
//        }
//
//        return retVal;
//    }
}
