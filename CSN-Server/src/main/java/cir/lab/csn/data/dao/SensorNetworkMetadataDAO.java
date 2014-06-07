package cir.lab.csn.data.dao;

import cir.lab.csn.data.db.ConnectionMaker;
import cir.lab.csn.data.DAOReturnType;
import cir.lab.csn.exception.ExceptionProcessor;
import cir.lab.csn.metadata.network.SNDefaultMetadata;
import cir.lab.csn.metadata.network.SensorNetworkMetadata;
import cir.lab.csn.util.TimeGeneratorUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class SensorNetworkMetadataDAO {
    //Logger logger = LoggerFactory.getLogger(SensorNetworkMetadataDAO.class);

    private static final String SN_DEFAULT_META_TABLE_NM = "csn_sn_meta";
    private static final String SN_MEMBERS_TABLE_NM = "csn_sn_members";
    private static final String SN_OPT_META_TABLE_NM = "csn_sn_options";
    private static final String SN_TAG_TABLE_NM = "csn_sn_tags";

    private ConnectionMaker connectionMaker;

    public SensorNetworkMetadataDAO(ConnectionMaker connectionMaker) {
        this.connectionMaker = connectionMaker;
    }

    public DAOReturnType addDefaultSensorNetworkMetadata(String id, String name, String creationTime,
                                                         String topicName, Set<String> members) {
        DAOReturnType retType;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + SN_DEFAULT_META_TABLE_NM +
                    "(sn_id, sn_name, sn_creation_time, sn_topic_name) VALUES(?, ?, ?, ?)");
            ps.setString(1, id);
            ps.setString(2, name);
            ps.setString(3, creationTime);
            ps.setString(4, topicName);

            ps.executeUpdate();
            ps.close();
            c.close();

            retType = addSensorSetToSensorNetwork(id, members);
            if(retType == DAOReturnType.RETURN_ERROR)
                throw new Exception("Add a Sensor Set Exception");
            retType = DAOReturnType.RETURN_OK;
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType addSensorToSensorNetwork(String id, String member) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + SN_MEMBERS_TABLE_NM + "(sn_id, snsr_member) VALUES(?, ?)");
            ps.setString(1, id);
            ps.setString(2, member);

            ps.executeUpdate();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType addSensorSetToSensorNetwork(String id, Set<String> members) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            if(members != null) {
                for(String member : members) {
                    retType = addSensorToSensorNetwork(id, member);
                    if(retType == DAOReturnType.RETURN_ERROR)
                        throw new Exception("Can't set a Sensor Members to the Sensor Network");
                }
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public Set<String> getAllSensorNetworkID() {
        Set<String> idSet = null;
        try {
            idSet = new HashSet<String>();
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT sn_id FROM " + SN_DEFAULT_META_TABLE_NM + " WHERE sn_alive=?");
            ps.setInt(1,1);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                idSet.add(rs.getString("sn_id"));

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
        }
        return idSet;
    }

    public String getSensorNetworkTopicNameByID(String id) {
        String topicName = null;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT sn_topic_name FROM " + SN_DEFAULT_META_TABLE_NM + " WHERE sn_id=? AND sn_alive=?");
            ps.setString(1, id);
            ps.setInt(2,1);
            ResultSet rs = ps.executeQuery();
            rs.next();
            topicName = rs.getString("sn_topic_name");

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
        }
        return topicName;

    }

    public Set<String> getAllSensorNetworkTopicName() {
        Set<String> topicSet;
        try {
            topicSet = new HashSet<String>();
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT sn_topic_name FROM " + SN_DEFAULT_META_TABLE_NM + " WHERE sn_alive=?");
            ps.setInt(1,1);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                topicSet.add(rs.getString("sn_topic_name"));
            }

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            topicSet = null;
        }
        return topicSet;
    }

    public SNDefaultMetadata getDefaultSensorNetworkMetadata(String id) {
        SNDefaultMetadata metadata;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT * FROM " + SN_DEFAULT_META_TABLE_NM + " WHERE sn_id=? AND sn_alive=?");
            ps.setString(1, id);
            ps.setInt(2, 1);
            ResultSet rs = ps.executeQuery();
            rs.next();
            Set<String> members = getSensorMembersInSensorNetwork(id);
            metadata = new SNDefaultMetadata(rs.getString("sn_name"), members,
                    rs.getString("sn_id"),
                    rs.getString("sn_creation_time"),
                    rs.getString("sn_removal_time"),
                    rs.getString("sn_topic_name"),
                    rs.getInt("sn_alive"));

            if(members == null)
                throw new Exception("Can't get sensor members");
            metadata.setMembers(members);
            metadata.setMembers(members);

            rs.close();
            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            metadata = null;
        }
        return metadata;

    }

    public Set<String> getSensorMembersInSensorNetwork(String id) {
        Set<String> members = new HashSet<String>();
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT snsr_member FROM "+ SN_MEMBERS_TABLE_NM +" WHERE sn_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String member = rs.getString("snsr_member");
                members.add(member);
            }

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            members = null;
        }
        return members;
    }

    public Map<String, Set<String>> getAllSNTopicNameAndTheirMemberIDs() {
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        try {
            Set<String> idSet = this.getAllSensorNetworkID();
            for(String id : idSet) {
                String snTopicName = this.getSensorNetworkTopicNameByID(id);
                Set<String> members = this.getSensorMembersInSensorNetwork(id);
                map.put(snTopicName, members);
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            map = null;
        }
        return map;
    }

    public DAOReturnType addOptionalSensorNetworkMetadata(String id, String optName, String optVal) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + SN_OPT_META_TABLE_NM + "(sn_id, opt_name, opt_val) VALUES(?, ?, ?)");
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

    public DAOReturnType addOptionalSensorNetworkMetadata(String id, Map<String, String> metadataMap) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Set<String> optNameSet = metadataMap.keySet();
            for(String optName : optNameSet) {
                String optVal = metadataMap.get(optName);
                retType = addOptionalSensorNetworkMetadata(id, optName, optVal);
                if(retType == DAOReturnType.RETURN_ERROR)
                    throw new Exception("Can't set Sensor Optional Metadata");
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public Set<String> getOptionalSensorNetworkMetadataKeySet(String id){
        Set<String> keySet = new HashSet<String>();
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT opt_name FROM " + SN_OPT_META_TABLE_NM + " WHERE sn_id=?");
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

    public String getOptionalSensorNetworkMetadataValue(String id, String optName) {
        String optVal;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT opt_val FROM " + SN_OPT_META_TABLE_NM + " WHERE sn_id=? AND opt_name=?");
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

    public Map<String, String> getAllOptionalSensorNetworkMetadataMap(String id) {
        Map<String, String> metadataMap = new HashMap<String, String>();
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT opt_name, opt_val FROM " + SN_OPT_META_TABLE_NM + " WHERE sn_id=?");
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

    public DAOReturnType updateOptionalSensorNetworkMetadataVal(String id, String optName, String newOptVal) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            boolean isItValue = isItOptionalSensorNetworkMetadata(id, optName);
            if(isItValue == true) {
                Connection c = connectionMaker.makeConnection();
                PreparedStatement ps = c.prepareStatement("UPDATE " + SN_OPT_META_TABLE_NM + " SET opt_val=? WHERE sn_id=? AND opt_name=?");

                ps.setString(1, newOptVal);
                ps.setString(2, id);
                ps.setString(3, optName);
                ps.executeUpdate();

                ps.close();
                c.close();
            }
            else {
                retType = addOptionalSensorNetworkMetadata(id, optName, newOptVal);
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

    public DAOReturnType updateOptionalSensorNetworkMetadata(String id, Map<String, String> metadataMap) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Set<String> optNameSet = metadataMap.keySet();
            for(String optName : optNameSet) {
                String optVal = metadataMap.get(optName);
                retType = updateOptionalSensorNetworkMetadataVal(id, optName, optVal);
                if(retType == DAOReturnType.RETURN_ERROR)
                    throw new Exception("Can't set Sensor Optional Metadata");
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType removeOptionalSensorNetworkMetadataVal(String id) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SN_OPT_META_TABLE_NM + " WHERE sn_id=?");
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

    public DAOReturnType removeOptionalSensorNetworkMetadataVal(String id, String optName) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SN_OPT_META_TABLE_NM + " WHERE sn_id=? AND opt_name=?");
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

    public boolean isItOptionalSensorNetworkMetadata(String id, String optName) {
        boolean retVal = false;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM " + SN_OPT_META_TABLE_NM + " WHERE sn_id=? AND opt_name=?");
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
        }
        return retVal;

    }

    public DAOReturnType addTagToSensorNetwork(String id, String tag) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("INSERT INTO " + SN_TAG_TABLE_NM + "(sn_id, sn_tag) VALUES(?, ?)");
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

    public DAOReturnType addTagSetToSensorNetwork(String id, Set<String> tagSet) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            for(String tag : tagSet) {
                retType = addTagToSensorNetwork(id, tag);
                if(retType == DAOReturnType.RETURN_ERROR)
                    throw new Exception("Can't set a Tag to the Sensor");
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public Set<String> getAllTagBySensorNetworkID(String id) {
        Set<String> tagSet = new HashSet<String>();
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT sn_tag FROM " + SN_TAG_TABLE_NM + " WHERE sn_id=?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                tagSet.add(rs.getString("sn_tag"));

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            tagSet = null;
        }
        return tagSet;
    }

    public Set<String> getAllSensorNetworkIDByTag(String tag) {
        Set<String> idSet = new HashSet<String>();
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT sn_id FROM " + SN_TAG_TABLE_NM + " WHERE sn_tag=?");
            ps.setString(1, tag);
            ResultSet rs = ps.executeQuery();
            while(rs.next())
                idSet.add(rs.getString("sn_id"));

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            idSet = null;
        }
        return idSet;
    }

    public Set<String> getAllSensorNetworkTopicNameByTag(String tag) {
        Set<String> topicNameSet = new HashSet<String>();
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT sn_id FROM " + SN_TAG_TABLE_NM + " WHERE sn_tag=?");
            ps.setString(1, tag);
            ResultSet rs = ps.executeQuery();
            Set<String> idSet = new HashSet<String>();
            while(rs.next())
                idSet.add(rs.getString("sn_id"));

            for(String id : idSet)
                topicNameSet.add(getSensorNetworkTopicNameByID(id));

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            topicNameSet = null;
        }
        return topicNameSet;

    }

    public DAOReturnType updateTagInSensorNetwork(String id, String oldTag, String newTag) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("UPDATE " + SN_TAG_TABLE_NM + " SET sn_tag=? WHERE sn_id=? AND sn_tag=?");
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

    public DAOReturnType removeAllTagInSensorNetwork(String id) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SN_TAG_TABLE_NM + " WHERE sn_id=?");
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

    public DAOReturnType removeTagInSensorNetwork(String id, String tag) {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SN_TAG_TABLE_NM + " WHERE sn_id=? AND sn_tag=?");
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

    public SensorNetworkMetadata getSensorNetworkMetadata(String id) {
        SensorNetworkMetadata sensorNetworkMetadata = new SensorNetworkMetadata();
        try {
            SNDefaultMetadata defaultSensorNetworkMetadata = getDefaultSensorNetworkMetadata(id);
            if(defaultSensorNetworkMetadata == null)
                throw new Exception("Can't Get Default Sensor Network Metadata");
            else
                sensorNetworkMetadata.setDef_meta(defaultSensorNetworkMetadata);

            Map<String, String> optionalMetadataMap = getAllOptionalSensorNetworkMetadataMap(id);
            if(optionalMetadataMap == null)
                throw new Exception("Can't Get Optional Sensor Network Metadata");
            else
                sensorNetworkMetadata.getOpt_meta().setElmts(optionalMetadataMap);

            Set<String> tagSet = getAllTagBySensorNetworkID(id);
            if(tagSet == null)
                throw new Exception("Can't Get Sensor Network Tags");
            else
                sensorNetworkMetadata.getSnsr_tag().setTags(tagSet);
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            sensorNetworkMetadata = null;
        }
        return sensorNetworkMetadata;

    }

    public DAOReturnType removeSensorNetworkMetadata(String id) {
        DAOReturnType retType;
        try {
            Connection c = connectionMaker.makeConnection();

            PreparedStatement ps = c.prepareStatement("UPDATE " + SN_DEFAULT_META_TABLE_NM + " SET sn_removal_time=?, sn_alive=? WHERE sn_id=?");
            ps.setString(1, TimeGeneratorUtil.getCurrentTimestamp());
            ps.setInt(2, 0);
            ps.setString(3, id);
            ps.executeUpdate();

            retType = removeOptionalSensorNetworkMetadataVal(id);
            if(retType == DAOReturnType.RETURN_ERROR)
                throw new Exception("Can't remove Optional Sensor Network Metadata");

            retType = removeAllTagInSensorNetwork(id);
            if(retType == DAOReturnType.RETURN_ERROR)
                throw new Exception("Can't remove Tags in Sensor Network");

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public boolean isItSensorNetworkMetadata(String id) {
        boolean retVal = false;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) FROM " + SN_DEFAULT_META_TABLE_NM + " WHERE sn_id=?");
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

    public List<SensorNetworkMetadata> getAllSensorNetworkMetadata() {
        List<SensorNetworkMetadata> metadataList = new ArrayList<SensorNetworkMetadata>();
        try {
            Set<String> idSet = getAllSensorNetworkID();
            if(idSet == null)
                throw new Exception("Can't Get All Sensor Network's ID");
            else{
                for(String id : idSet) {
                    SensorNetworkMetadata tempSensorNetworkMetadata = getSensorNetworkMetadata(id);
                    if(tempSensorNetworkMetadata == null)
                        throw new Exception("Can't Get SensorNetworkMetadata");
                    else
                        metadataList.add(tempSensorNetworkMetadata);
                }
            }
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            metadataList = null;
        }
        return metadataList;

    }

    public DAOReturnType deleteAllSensorNetworkMetadata()  {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();

            PreparedStatement ps = c.prepareStatement("UPDATE " + SN_DEFAULT_META_TABLE_NM + " SET sn_removal_time=?, sn_alive=?");
            ps.setString(1, TimeGeneratorUtil.getCurrentTimestamp());
            ps.setInt(2, 0);
            ps.executeUpdate();

            ps.close();
            c.close();
        } catch (Exception e) {
            ExceptionProcessor.handleException(e, this.getClass().getName());
            retType = DAOReturnType.RETURN_ERROR;
        }
        return retType;
    }

    public DAOReturnType eraseAllSensorNetworkMetadata()  {
        DAOReturnType retType = DAOReturnType.RETURN_OK;
        try {
            Connection c = connectionMaker.makeConnection();
            PreparedStatement ps = c.prepareStatement("DELETE FROM " + SN_DEFAULT_META_TABLE_NM);
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
