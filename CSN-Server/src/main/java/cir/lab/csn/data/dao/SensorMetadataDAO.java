package cir.lab.csn.data.dao;

import cir.lab.csn.data.db.CSNDAOFactory;
import cir.lab.csn.data.db.ConnectionMaker;
import cir.lab.csn.data.DAOReturnType;
import cir.lab.csn.metadata.sensor.DefaultMetadata;
import cir.lab.csn.metadata.sensor.SensorMetadata;
import cir.lab.csn.exception.ExceptionProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class SensorMetadataDAO {
    Logger logger = LoggerFactory.getLogger(SensorMetadataDAO.class);

    private static final String SNSR_DEFAULT_META_TABLE_NM = "csn_snsr_meta";
    private static final String SNSR_OPT_META_TABLE_NM = "csn_snsr_options";
    private static final String SNSR_TAG_TABLE_NM = "csn_snsr_tags";
    private static final String SN_MEMBERS_TABLE_NM = "csn_sn_members";

    private SensorNetworkMetadataDAO sensorNetworkMetadataDAO = new CSNDAOFactory().sensorNetworkMetadataDAO();

    private ConnectionMaker connectionMaker;

    public SensorMetadataDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public DAOReturnType addDefaultSensorMetadata(String id, String name, String measurement, String creationTime) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + SNSR_DEFAULT_META_TABLE_NM +
                    "(snsr_id, snsr_name, snsr_measurement, snsr_creation_time) VALUES(?, ?, ?, ?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, measurement);
            ps.setString(4, creationTime);
            ps.executeUpdate();

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public Set<String> getAllSensorID() {
        Set<String> idSet = null;
        try {
            idSet = new HashSet<String>();
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT snsr_id FROM " + SNSR_DEFAULT_META_TABLE_NM);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                idSet.add(rs.getString("snsr_id"));

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
        }
        return idSet;
    }

    public int getAllSensorNum() {
        Set<String> idSet = null;
        try {
            idSet = new HashSet<String>();
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT snsr_id FROM " + SNSR_DEFAULT_META_TABLE_NM);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                idSet.add(rs.getString("snsr_id"));

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
        }
        return idSet.size();
    }

    public Set<String> getSensorIDs(int index, int num) {
        Set<String> idSet = null;
        try {
            idSet = new HashSet<String>();
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT snsr_id FROM " + SNSR_DEFAULT_META_TABLE_NM + " ORDER BY snsr_id ASC LIMIT ?, ?");
            ps.setInt(1, index);
            ps.setInt(2, num);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                idSet.add(rs.getString("snsr_id"));

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
        }
        return idSet;
    }

    public DefaultMetadata getDefaultSensorMetadata(String id) {
        DefaultMetadata metadata;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " + SNSR_DEFAULT_META_TABLE_NM + " WHERE snsr_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            metadata = new DefaultMetadata(rs.getString("snsr_name"),
                    rs.getString("snsr_measurement"),
                    rs.getString("snsr_id"),
                    rs.getString("snsr_creation_time") );

            rs.close();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            metadata = null;
        }
        return metadata;
    }

    public Set<String> getSensorNetworkIDsOfSensor(String id){
        Set<String> snIDs = new HashSet<String>();
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT sn_id FROM "+ SN_MEMBERS_TABLE_NM +" WHERE snsr_member =?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String snID= rs.getString("sn_id");
                snIDs.add(snID);
            }

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            snIDs = null;
        }
        return snIDs;
    }

    public Set<String> getTopicNamesOfSensor(String id){
        Set<String> topicNames = new HashSet<String>();
        Set<String> snIDs = this.getSensorNetworkIDsOfSensor(id);

        Iterator<String> iter = snIDs.iterator();

        while(iter.hasNext()) {
            String snID = iter.next();
            String topicName = sensorNetworkMetadataDAO.getSensorNetworkTopicNameByID(snID);
            topicNames.add(topicName);
        }

        return topicNames;
    }

    public DAOReturnType addOptionalSensorMetadata(String id, String optName, String optVal) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + SNSR_OPT_META_TABLE_NM + "(snsr_id, opt_name, opt_val) VALUES(?, ?, ?)");
            ps.setString(1, id);
            ps.setString(2, optName);
            ps.setString(3, optVal);
            ps.executeUpdate();

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType addOptionalSensorMetadata(String id, Map<String, String> metadataMap) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Set<String> optNameSet =  metadataMap.keySet();
            for(String optName : optNameSet) {
                String optVal = metadataMap.get(optName);
                retType = addOptionalSensorMetadata(id, optName, optVal);
                if(retType == DAOReturnType.RETURN_ERROR)
                    throw new Exception("Can't set Sensor Optional Metadata");
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public Set<String> getOptionalSensorMetadataKeySet(String id){
        Set<String> keySet = new HashSet<String>();
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT opt_name FROM " + SNSR_OPT_META_TABLE_NM + " WHERE snsr_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                keySet.add(rs.getString("opt_name"));

            rs.close();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            keySet = null;
        }
        return keySet;
    }

    public String getOptionalSensorMetadataValue(String id, String optName) {
        String optVal;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT opt_val FROM " + SNSR_OPT_META_TABLE_NM + " WHERE snsr_id=? AND opt_name=?");
            ps.setString(1, id);
            ps.setString(2, optName);
            ResultSet rs = ps.executeQuery();
            rs.next();
            optVal = rs.getString("opt_val");

            rs.close();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            optVal = null;
        }
        return optVal;
    }

    public Map<String, String> getAllOptionalSensorMetadataMap(String id) {
        Map<String, String> metadataMap = new HashMap<String, String>();
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT opt_name, opt_val FROM " + SNSR_OPT_META_TABLE_NM + " WHERE snsr_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                metadataMap.put(rs.getString("opt_name"), rs.getString("opt_val"));

            rs.close();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            metadataMap = null;
        }
        return metadataMap;
    }

    public DAOReturnType updateOptionalSensorMetadataVal(String id, String optName, String newOptVal) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            if(isItOptionalSensorMetadata(id, optName)) {
                Connection c = connectionMaker.makeConnection();
                logger.info("ID: {}, Option Name: {}, New Option Value: {}", id, optName, newOptVal);
                PreparedStatement ps = c.prepareStatement("UPDATE " + SNSR_OPT_META_TABLE_NM + " SET opt_val=? WHERE snsr_id=? AND opt_name=?");
                ps.setString(1, newOptVal);
                ps.setString(2, id);
                ps.setString(3, optName);
                ps.executeUpdate();
                ps.close();
                c.close();
            }
            else {
                retType = addOptionalSensorMetadata(id, optName, newOptVal);
                if(retType == null) {
                    throw new Exception("Add a New Optional Metadata Error in Update");
                }
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType updateOptionalSensorMetadata(String id, Map<String, String> metadataMap) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Set<String> optNameSet =  metadataMap.keySet();
            for(String optName : optNameSet) {
                String newOptVal = metadataMap.get(optName);
                retType = updateOptionalSensorMetadataVal(id, optName, newOptVal);
                if(retType == DAOReturnType.RETURN_ERROR)
                    throw new Exception("Can't set Sensor Optional Metadata");
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType removeOptionalSensorMetadata(String id) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SNSR_OPT_META_TABLE_NM + " WHERE snsr_id=?");
            ps.setString(1, id);
            ps.executeUpdate();

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType removeOptionalSensorMetadataVal(String id, String optName) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SNSR_OPT_META_TABLE_NM + " WHERE snsr_id=? AND opt_name=?");
            ps.setString(1, id);
            ps.setString(2, optName);

            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public boolean isItOptionalSensorMetadata(String id, String optName) {
        boolean retVal = false;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM " + SNSR_OPT_META_TABLE_NM + " WHERE snsr_id=? AND opt_name=?");
            ps.setString(1, id);
            ps.setString(2, optName);
            ResultSet rs = ps.executeQuery();

            rs.next();
            int num = rs.getInt(1);
            if(num > 0)
                retVal = true;

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retVal = false;
        }
        return retVal;
    }

    public DAOReturnType addTagToSensor(String id, String tag) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + SNSR_TAG_TABLE_NM + "(snsr_id, snsr_tag) VALUES(?, ?)");
            ps.setString(1, id);
            ps.setString(2, tag);

            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType addTagSetToSensor(String id, Set<String> tagSet) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            for(String tag : tagSet) {
                retType = addTagToSensor(id, tag);
                if(retType == DAOReturnType.RETURN_ERROR)
                    throw new Exception("Can't set a Tag to the Sensor");
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public Set<String> getAllTagBySensorID(String id) {
        Set<String> tagSet = new HashSet<String>();
        try {
            Connection c = connectionMaker.makeConnection();
            logger.info("Sensor ID: {}", id);
            PreparedStatement ps = c.prepareStatement("SELECT snsr_tag FROM " + SNSR_TAG_TABLE_NM + " WHERE snsr_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                tagSet.add(rs.getString("snsr_tag"));

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            tagSet = null;
        }
        return tagSet;

    }

    public Set<String> getAllSensorIDByTag(String tag) {
        Set<String> idSet = new HashSet<String>();
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT snsr_id FROM " + SNSR_TAG_TABLE_NM + " WHERE snsr_tag=?");
            ps.setString(1, tag);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                idSet.add(rs.getString("snsr_id"));

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            idSet = null;
        }
        return idSet;

    }

    public DAOReturnType updateTagInSensor(String id, String oldTag, String newTag) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE " + SNSR_TAG_TABLE_NM + " SET snsr_tag=? WHERE snsr_id=? AND snsr_tag=?");
            ps.setString(1, newTag);
            ps.setString(2, id);
            ps.setString(3, oldTag);

            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType removeAllTagInSensor(String id) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SNSR_TAG_TABLE_NM + " WHERE snsr_id=?");
            ps.setString(1, id);

            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType removeTagInSensor(String id, String tag) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SNSR_TAG_TABLE_NM + " WHERE snsr_id=? AND snsr_tag=?");
            ps.setString(1, id);
            ps.setString(2, tag);

            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public SensorMetadata getSensorMetadata(String id) {
        SensorMetadata sensorMetadata = new SensorMetadata();
        try {
            DefaultMetadata defaultMetadata = getDefaultSensorMetadata(id);
            if(defaultMetadata == null)
                throw new Exception("Can't Get Default Sensor Metadata");
            else
                sensorMetadata.setDefMeta(defaultMetadata);

            Map<String, String> optionalMetadataMap = getAllOptionalSensorMetadataMap(id);
            if(optionalMetadataMap == null)
                throw new Exception("Can't Get Optional Sensor Metadata");
            else
                sensorMetadata.setOptMeta(optionalMetadataMap);

            Set<String> tagSet = getAllTagBySensorID(id);
            if(tagSet == null)
                throw new Exception("Can't Get Sensor Tags");
            else
                sensorMetadata.setSnsrTags(tagSet);
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            sensorMetadata = null;
        }
        return sensorMetadata;

    }

    public DAOReturnType removeSensorMetadata(String id) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SNSR_DEFAULT_META_TABLE_NM + " WHERE snsr_id=?");
            ps.setString(1, id);

            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public boolean isItSensorMetadata(String id) {
        boolean retVal = false;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM " + SNSR_DEFAULT_META_TABLE_NM + " WHERE snsr_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            int num = rs.getInt(1);
            if(num > 0)
                retVal = true;

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retVal = false;
        }
        return retVal;
    }

    public List<SensorMetadata> getAllSensorMetadata() {
        List<SensorMetadata> metadataList = new ArrayList<SensorMetadata>();
        try {
            Set<String> idSet = getAllSensorID();
            if(idSet == null)
                throw new Exception("Can't Get All Sensor's ID");
            else{
                for(String id : idSet) {
                    SensorMetadata tempSensorMetadata = getSensorMetadata(id);
                    if(tempSensorMetadata == null)
                        throw new Exception("Can't Get Sensor Metadata");
                    else
                        metadataList.add(tempSensorMetadata);
                }
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            metadataList = null;
        }
        return metadataList;
    }

    public DAOReturnType deleteAllSensorMetadata()  {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SNSR_DEFAULT_META_TABLE_NM);
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
