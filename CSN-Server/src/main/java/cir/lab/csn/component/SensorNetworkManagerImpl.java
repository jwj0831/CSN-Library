package cir.lab.csn.component;

//import cir.lab.csn.data.AuthUserType;
import cir.lab.csn.data.SensorNetworkList;
//import cir.lab.csn.data.dao.AuthCheckDAO;
import cir.lab.csn.data.db.CSNDAOFactory;
import cir.lab.csn.data.DAOReturnType;
import cir.lab.csn.data.dao.SensorMetadataDAO;
import cir.lab.csn.data.dao.SensorNetworkMetadataDAO;
import cir.lab.csn.client.SensorNetworkManager;
import cir.lab.csn.metadata.sensor.DefaultMetadata;
import cir.lab.csn.metadata.sensor.SensorMetadata;
import cir.lab.csn.metadata.network.SNDefaultMetadata;
import cir.lab.csn.metadata.network.SensorNetworkMetadata;
import cir.lab.csn.util.IDGeneratorUtil;
//import cir.lab.csn.util.KeyGeneratorUtil;
import cir.lab.csn.util.TimeGeneratorUtil;
import cir.lab.csn.util.TopicNameGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SensorNetworkManagerImpl implements SensorNetworkManager {
    Logger logger = LoggerFactory.getLogger(SensorNetworkManagerImpl.class);

    private SensorMetadataDAO sensorMetaDAO;
    private SensorNetworkMetadataDAO sensorNetworkMetadataDAO;
    //private AuthCheckDAO authCheckDAO;

    public SensorNetworkManagerImpl() {
        sensorMetaDAO = new CSNDAOFactory().sensorMetadataDAO();
        sensorNetworkMetadataDAO = new CSNDAOFactory().sensorNetworkMetadataDAO();
        //authCheckDAO = new CSNDAOFactory().authCheckDAO();
    }

    @Override
    public String createSensorMetadata(String name, String measurement) {
        String creationTime = TimeGeneratorUtil.getCurrentTimestamp();
        long epoch = TimeGeneratorUtil.convertDateToEpoch(creationTime);
        String id = IDGeneratorUtil.getSensorID(name, Long.toString(epoch));
        //String key = KeyGeneratorUtil.getKey();
        DAOReturnType retType = sensorMetaDAO.addDefaultSensorMetadata(id, name, measurement, creationTime);

        if (retType == DAOReturnType.RETURN_ERROR)
            return null;
        else {
//            retType = authCheckDAO.addSensorAuthKey(key, AuthUserType.AUTH_SENSOR, id);
//            if (retType == DAOReturnType.RETURN_ERROR)
//                return null;
            Set<String> members = new HashSet<String>();
            members.add(id);
            // Add a Single Sensor Network Metadata
            String singleTopicName = TopicNameGeneratorUtil.getSingleSensorNetworkTopicName(name, epoch);
            retType = sensorNetworkMetadataDAO.addDefaultSensorNetworkMetadata(id, name, creationTime, singleTopicName, members);
            if (retType == DAOReturnType.RETURN_ERROR)
                return null;

            //Add Real-time Data
            SensorNetworkList.setUpdating(true);
            SensorNetworkList.registerNewSensorNetwork(singleTopicName, members);
            SensorNetworkList.setUpdating(false);
        }
        return id;
    }

//    @Override
//    public boolean isItSensorKey(String key) {
//        return authCheckDAO.isItSensorKey(key);
//    }
//
//    @Override
//    public String getSensorKey(String id) {
//        return authCheckDAO.getSensorAuthKey(id);
//    }

    @Override
    public Set<String> getAllSensorID() {
        return sensorMetaDAO.getAllSensorID();
    }

    @Override
    public DefaultMetadata getDefaultSensorMetadata(String id) {
        return sensorMetaDAO.getDefaultSensorMetadata(id);
    }

    @Override
    public DAOReturnType addOptionalSensorMetadata(String id, String optName, String optVal) {
        return sensorMetaDAO.addOptionalSensorMetadata(id, optName, optVal);
    }

    @Override
    public DAOReturnType addOptionalSensorMetadata(String id, Map<String, String> metadataMap) {
        return sensorMetaDAO.addOptionalSensorMetadata(id, metadataMap);
    }

    @Override
    public Set<String> getOptionalSensorMetadataKeySet(String id){
        return sensorMetaDAO.getOptionalSensorMetadataKeySet(id);
    }

    @Override
    public String getOptionalSensorMetadataValue(String id, String optName) {
        return sensorMetaDAO.getOptionalSensorMetadataValue(id, optName);
    }

    @Override
    public Map<String, String> getAllOptionalSensorMetadata(String id) {
        return sensorMetaDAO.getAllOptionalSensorMetadataMap(id);
    }

    @Override
    public DAOReturnType updateOptionalSensorMetadataValue(String id, String optName, String newOptVal) {
        return sensorMetaDAO.updateOptionalSensorMetadataVal(id, optName, newOptVal);
    }

    @Override
    public DAOReturnType updateOptionalSensorMetadata(String id, Map<String, String> metadataMap) {
        return sensorMetaDAO.updateOptionalSensorMetadata(id, metadataMap);
    }

    @Override
    public DAOReturnType removeOptionalSensorMetadata(String id) {
        return sensorMetaDAO.removeOptionalSensorMetadata(id);
    }

    @Override
    public DAOReturnType removeOptionalSensorMetadataValue(String id, String optName) {
        return sensorMetaDAO.removeOptionalSensorMetadataVal(id, optName);
    }

    @Override
    public boolean isItOptionalSensorMetadata(String id, String optName) {
        return sensorMetaDAO.isItOptionalSensorMetadata(id, optName);
    }

    @Override
    public DAOReturnType addTagToSensor(String id, String tag) {
        return sensorMetaDAO.addTagToSensor(id, tag);
    }

    @Override
    public DAOReturnType addTagSetToSensor(String id, Set<String> tagSet) {
        return sensorMetaDAO.addTagSetToSensor(id, tagSet);
    }

    @Override
    public Set<String> getAllTagBySensorID(String id) {
        return sensorMetaDAO.getAllTagBySensorID(id);
    }

    @Override
    public Set<String> getAllSensorIDByTag(String tag) {
        return sensorMetaDAO.getAllSensorIDByTag(tag);
    }

    @Override
    public DAOReturnType updateTagInSensor(String id, String oldTag, String newTag) {
        return sensorMetaDAO.updateTagInSensor(id, oldTag, newTag);
    }

    @Override
    public DAOReturnType removeAllTagInSensor(String id) {
        return sensorMetaDAO.removeAllTagInSensor(id);
    }

    @Override
    public DAOReturnType removeTagInSensor(String id, String tag) {
        return sensorMetaDAO.removeTagInSensor(id, tag);
    }

    @Override
    public SensorMetadata getSensorMetadata(String id) {
        return sensorMetaDAO.getSensorMetadata(id);
    }

    @Override
    public DAOReturnType removeSensorMetadata(String id) {
        return sensorMetaDAO.removeSensorMetadata(id);
    }

    @Override
    public boolean isItSensorMetadata(String id) {
        return sensorMetaDAO.isItSensorMetadata(id);
    }

    @Override
    public List<SensorMetadata> getAllSensorMetadata() {
        return sensorMetaDAO.getAllSensorMetadata();
    }

    @Override
    public DAOReturnType deleteAllSensorMetadata() {
        return sensorMetaDAO.deleteAllSensorMetadata();
    }


    @Override
    public String createSensorNetworkMetadata(String name, Set<String> members) {
        String creationTime = TimeGeneratorUtil.getCurrentTimestamp();
        long epoch = TimeGeneratorUtil.convertDateToEpoch(creationTime);

        String id = IDGeneratorUtil.getSensorNetworkID(name, Long.toString(epoch));
        String topicName = TopicNameGeneratorUtil.getMultiSensorNetworkTopicName(name, epoch);
        DAOReturnType retType = sensorNetworkMetadataDAO.addDefaultSensorNetworkMetadata(id, name, creationTime, topicName, members);

        if (retType == DAOReturnType.RETURN_ERROR)
            id = null;

        SensorNetworkList.setUpdating(true);
        SensorNetworkList.registerNewSensorNetwork(topicName, members);
        SensorNetworkList.setUpdating(false);
        return id;
    }

    @Override
    public Set<String> getAllSensorNetworkID() {
        return sensorNetworkMetadataDAO.getAllSensorNetworkID();
    }

    @Override
    public String getSensorNetworkTopicName(String id) {
        return sensorNetworkMetadataDAO.getSensorNetworkTopicNameByID(id);
    }

    @Override
    public Set<String> getAllSensorNetworkTopicName() {
        return sensorNetworkMetadataDAO.getAllSensorNetworkTopicName();
    }

    @Override
    public SNDefaultMetadata getDefaultSensorNetworkMetadata(String id) {
        return sensorNetworkMetadataDAO.getDefaultSensorNetworkMetadata(id);
    }

    @Override
    public Set<String> getSensorMembersInSensorNetwork(String id) {
        return sensorNetworkMetadataDAO.getSensorMembersInSensorNetwork(id);
    }

    @Override
    public Map<String, Set<String>> getAllSNTopicNameAndTheirMemberIDs() {
        return sensorNetworkMetadataDAO.getAllSNTopicNameAndTheirMemberIDs();
    }

    @Override
    public DAOReturnType addOptionalSensorNetworkMetadata(String id, String optName, String optVal) {
        return sensorNetworkMetadataDAO.addOptionalSensorNetworkMetadata(id, optName, optVal);
    }

    @Override
    public DAOReturnType addOptionalSensorNetworkMetadata(String id, Map<String, String> metadataMap) {
        return sensorNetworkMetadataDAO.addOptionalSensorNetworkMetadata(id, metadataMap);
    }

    @Override
    public Set<String> getOptionalSensorNetworkMetadataKeySet(String id){
        return sensorNetworkMetadataDAO.getOptionalSensorNetworkMetadataKeySet(id);
    }

    @Override
    public String getOptionalSensorNetworkMetadataValue(String id, String optName) {
        return sensorNetworkMetadataDAO.getOptionalSensorNetworkMetadataValue(id, optName);
    }

    @Override
    public Map<String, String> getAllOptionalSensorNetworkMetadata(String id) {
        return sensorNetworkMetadataDAO.getAllOptionalSensorNetworkMetadataMap(id);
    }

    @Override
    public DAOReturnType updateOptionalSensorNetworkMetadataValue(String id, String optName, String newOptVal) {
        return sensorNetworkMetadataDAO.updateOptionalSensorNetworkMetadataVal(id, optName, newOptVal);
    }

    @Override
    public DAOReturnType updateOptionalSensorNetworkMetadata(String id, Map<String, String> metadataMap) {
        return sensorNetworkMetadataDAO.updateOptionalSensorNetworkMetadata(id, metadataMap);
    }


    @Override
    public DAOReturnType removeOptionalSensorNetworkMetadata(String id) {
        return sensorNetworkMetadataDAO.removeOptionalSensorNetworkMetadataVal(id);
    }

    @Override
    public DAOReturnType removeOptionalSensorNetworkMetadataValue(String id, String optName) {
        return sensorNetworkMetadataDAO.removeOptionalSensorNetworkMetadataVal(id, optName);
    }

    @Override
    public boolean isItOptionalSensorNetworkMetadata(String id, String optName) {
        return sensorNetworkMetadataDAO.isItOptionalSensorNetworkMetadata(id, optName);
    }

    @Override
    public DAOReturnType addTagToSensorNetwork(String id, String tag) {
        return sensorNetworkMetadataDAO.addTagToSensorNetwork(id, tag);
    }

    @Override
    public DAOReturnType addTagSetToSensorNetwork(String id, Set<String> tagSet) {
        return sensorNetworkMetadataDAO.addTagSetToSensorNetwork(id, tagSet);
    }

    @Override
    public Set<String> getAllTagBySensorNetworkID(String id) {
        return sensorNetworkMetadataDAO.getAllTagBySensorNetworkID(id);
    }

    @Override
    public Set<String> getAllSensorNetworkIDByTag(String tag) {
        return sensorNetworkMetadataDAO.getAllSensorNetworkIDByTag(tag);
    }

    @Override
    public Set<String> getAllSensorNetworkTopicNameByTag(String tag) {
        return sensorNetworkMetadataDAO.getAllSensorNetworkTopicNameByTag(tag);
    }

    @Override
    public DAOReturnType updateTagInSensorNetwork(String id, String oldTag, String newTag) {
        return sensorNetworkMetadataDAO.updateTagInSensorNetwork(id, oldTag, newTag);
    }

    @Override
    public DAOReturnType removeAllTagInSensorNetwork(String id) {
        return sensorNetworkMetadataDAO.removeAllTagInSensorNetwork(id);
    }

    @Override
    public DAOReturnType removeTagInSensorNetwork(String id, String tag) {
        return sensorNetworkMetadataDAO.removeTagInSensorNetwork(id, tag);
    }

    @Override
    public SensorNetworkMetadata getSensorNetworkMetadata(String id) {
        return sensorNetworkMetadataDAO.getSensorNetworkMetadata(id);
    }

    @Override
    public DAOReturnType removeSensorNetworkMetadata(String id) {
        return sensorNetworkMetadataDAO.removeSensorNetworkMetadata(id);
    }

    @Override
    public boolean isItSensorNetworkMetadata(String id) {
        return sensorNetworkMetadataDAO.isItSensorNetworkMetadata(id);
    }

    @Override
    public List<SensorNetworkMetadata> getAllSensorNetworkMetadata() {
        return sensorNetworkMetadataDAO.getAllSensorNetworkMetadata();
    }

    @Override
    public DAOReturnType deleteAllSensorNetworkMetadata() {
        return sensorNetworkMetadataDAO.deleteAllSensorNetworkMetadata();
    }

    @Override
    public DAOReturnType eraseAllSensorNetworkMetadata(){
        return sensorNetworkMetadataDAO.eraseAllSensorNetworkMetadata();
    }

    public DAOReturnType getCSNMetadataControllerStatusInternal() {
        return DAOReturnType.RETURN_OK;
    }
}
