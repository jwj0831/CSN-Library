package cir.lab.csn.data.dao;

import cir.lab.csn.data.AuthUserType;
import cir.lab.csn.data.DAOReturnType;
import cir.lab.csn.data.db.ConnectionMaker;
import cir.lab.csn.exception.ExceptionProcessor;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Created by nfm on 2014. 6. 2..
 */
public class AuthCheckDAO {
    private static final String AUTH_KEY_TABLE_NM = "csn_auth_key";

    private ConnectionMaker connectionMaker;

    public AuthCheckDAO(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }

    public DAOReturnType addSensorAuthKey(String key, AuthUserType flag, String id) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        int authType;
        switch (flag){
            case AUTH_SENSOR:
                authType = 0;
                break;
            case AUTH_USER:
                authType = 1;
                break;
            default:
                authType = 0;
                break;
        }
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + AUTH_KEY_TABLE_NM + "(auth_key, attr_flag, snsr_id) VALUES(?, ?, ?)");

            ps.setString(1, key);
            ps.setInt(2, authType);
            ps.setString(3, id);
            ps.executeUpdate();

            ps.close();
            c.close();
        } catch(Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public boolean isItSensorKey(String key) {
        boolean retVal = false;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT attr_flag FROM " + AUTH_KEY_TABLE_NM + " WHERE auth_key=?");
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            rs.next();

            int flag = rs.getInt(1);
            if(flag == 0)
                retVal = true;

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
        }
        return retVal;
    }

    public String getSensorAuthKey(String id) {
        String key = null;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT auth_key FROM " + AUTH_KEY_TABLE_NM + " WHERE snsr_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            key = rs.getString("auth_key");

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
        }
        return key;
    }

    public DAOReturnType addUserAuthKey(String key, AuthUserType flag, int id) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        int authType;
        switch (flag){
            case AUTH_SENSOR:
                authType = 0;
                break;
            case AUTH_USER:
                authType = 1;
                break;
            default:
                authType = 0;
                break;
        }
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + AUTH_KEY_TABLE_NM + "(auth_key, attr_flag, user_id) VALUES(?, ?, ?)");

            ps.setString(1, key);
            ps.setInt(2, authType);
            ps.setInt(3, id);
            ps.executeUpdate();

            ps.close();
            c.close();
        } catch(Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public boolean isItUserKey(String key) {
        boolean retVal = false;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT attr_flag FROM " + AUTH_KEY_TABLE_NM + " WHERE auth_key=?");
            ps.setString(1, key);
            ResultSet rs = ps.executeQuery();
            rs.next();

            int flag = rs.getInt(1);
            if(flag == 1)
                retVal = true;

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
        }
        return retVal;
    }

    public String getUserAuthKey(int id) {
        String key = null;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT auth_key FROM " + AUTH_KEY_TABLE_NM + " WHERE user_id=?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            key = rs.getString("auth_key");

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
        }
        return key;
    }

    public boolean checkHasKey(String key) {
        boolean retVal = false;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT count(auth_key) FROM " + AUTH_KEY_TABLE_NM + " WHERE auth_key=?");

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

}
