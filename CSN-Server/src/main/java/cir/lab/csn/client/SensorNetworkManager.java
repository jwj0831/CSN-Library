package cir.lab.csn.client;

import cir.lab.csn.metadata.network.SNDefaultMetadata;
import cir.lab.csn.metadata.sensor.DefaultMetadata;
import cir.lab.csn.metadata.sensor.SensorMetadata;
import cir.lab.csn.metadata.network.SensorNetworkMetadata;
import cir.lab.csn.data.DAOReturnType;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by nfm on 2014. 5. 20..
 */
public interface SensorNetworkManager {
    /**
     * This method adds default sensor metadata into database.
     * @param name the name of the Sensor
     * @param measurement the measurement object target, for example "humidity"
     * @return If successfully done, it returns the id of sensor, or null.
     */
    public String createSensorMetadata(String name, String measurement);

    public String createSensorMetadata(SensorMetadata metadata);

    /**
     * This method checks if the key is registered or not.
     * @param key sensor's authentication key
     * @return If successfully done, it returns true, or false.
     */
    //public boolean isItSensorKey(String key);

    /**
     * This method returns the key for accessing REST API.
     * @param id sensor's identifier
     * @return If successfully done, it returns the key of sensor, or null.
     */
    //public String getSensorKey(String id);

    /**
     * This method returns all the id of sensor in CSN.
     * @return If successfully done, it returns all the ID set of sensors, or null.
     */
    public Set<String> getAllSensorID();

    public int getAllSensorNum();

    public Set<String> getSensorIDs(int index, int num);

    /**
     * This method returns the default metadata of a sensor.
     * @param id sensor's identifier
     * @return If successfully done, it returns the DefaultSensorMetadata instance, or null.
     */
    public DefaultMetadata getDefaultSensorMetadata(String id);


    public Set<String> getSensorNetworkIDsOfSensor(String id);

    public Set<String> getTopicNamesOfSensor(String id);

    /**
     * This method adds optional sensor metadata into database.
     * @param id Sensor's identifier
     * @param optName Option Name
     * @param optVal Option Value
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType addOptionalSensorMetadata(String id, String optName, String optVal);

    /**
     * This method adds optional sensor metadata into database.
     * @param id Sensor's identifier
     * @param metadataMap option key-value map
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType addOptionalSensorMetadata(String id, Map<String, String> metadataMap);

    /**
     * This method returns sensor's optional metadata name set.
     * @param id Sensor's identifier
     * @return If successfully done, it returns name set of optional metadata, or null
     */
    public Set<String> getOptionalSensorMetadataKeySet(String id);

    /**
     * This method returns sensor's optional metadata value.
     * @param id Sensor's identifier
     * @param optName optional metadata key name
     * @return If successfully done, it returns option value, or null
     */
    public String getOptionalSensorMetadataValue(String id, String optName);

    /**
     * This method returns sensor's optional metadata map.
     * @param id Sensor's identifier
     * @return If successfully done, it returns all optional metadata of sensor, or null
     */
    public Map<String, String> getAllOptionalSensorMetadata(String id);

    /**
     * This method update optional sensor metadata in database.
     * @param id Sensor's identifier
     * @param optName Option Name
     * @param newOptVal New Option Value
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType updateOptionalSensorMetadataValue(String id, String optName, String newOptVal);

    /**
     * This method update optional sensor metadata in database.
     * @param id Sensor's identifier
     * @param metadataMap option key-value map
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType updateOptionalSensorMetadata(String id, Map<String, String> metadataMap);

    /**
     * This method remove all the sensor's optional metadata in database.
     * @param id Sensor's identifier
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType removeOptionalSensorMetadata(String id);

    /**
     * This method remove sensor's specific optional metadata in database.
     * @param id Sensor's identifier
     * @param optName Option Name
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType removeOptionalSensorMetadataValue(String id, String optName);

    /**
     * This method check if whether the option value is or not.
     * @param id Sensor's identifier
     * @param optName Option Name
     * @return If successfully done, it returns true, or false..
     */
    public boolean isItOptionalSensorMetadata(String id, String optName);

    /**
     * This method add a tag to a sensor.
     * @param id Sensor's identifier
     * @param tag The tag which is added to a sensor.
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType addTagToSensor(String id, String tag);

    /**
     * This method add a tag to a sensor.
     * @param id Sensor's identifier
     * @param tagSet The set of tag which are added to a sensor.
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType addTagSetToSensor(String id, Set<String> tagSet);

    /**
     * This method returns all the tag which is added to a sensor.
     * @param id Sensor's identifier
     * @return If successfully done, it returns set of sensor tag, or null.
     */
    public Set<String> getAllTagBySensorID(String id);

    /**
     * This method returns all the sensr ID which has the tag.
     * @param tag The tag which is added to a sensor.
     * @return If successfully done, it returns set of sensor ID, or null.
     */
    public Set<String> getAllSensorIDByTag(String tag);

    /**
     * This method add a tag to a sensor.
     * @param id Sensor's identifier
     * @param oldTag A old tag which is override with new tag information.
     * @param newTag A new tag which is updated instead of old tag information.
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType updateTagInSensor(String id, String oldTag, String newTag);

    /**
     * This method remove a tag to a sensor.
     * @param id Sensor's identifier
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType removeAllTagInSensor(String id);

    /**
     * This method remove a tag to a sensor.
     * @param id Sensor's identifier
     * @param tag The tag which is removed to a sensor.
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType removeTagInSensor(String id, String tag);

    /**
     * This method returns sensor metadata instance.
     * @param id Sensor's identifier
     * @return If successfully done, it returns the metadata of sensor, or null.
     */
    public SensorMetadata getSensorMetadata(String id);

    /**
     * This method remove all metadata of a sensor.
     * @param id sensor's identifier
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType removeSensorMetadata(String id);

    /**
     * This method check if default metadata of a sensor is or not.
     * @param id sensor's identifier
     * @return If successfully done, it returns true, or false..
     */
    public boolean isItSensorMetadata(String id);

    /**
     * This method returns all the sensor metadata instance.
     * @return If successfully done, it returns all the metadata of sensor, or null.
     */
    public List<SensorMetadata> getAllSensorMetadata();

    /**
     * This method remove all the sensor's metadata in the database;
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType deleteAllSensorMetadata();

    /**
     * This method adds default sensor network metadata into database.
     * @param name the name of the Sensor Network
     * @param members the set of sensors which are member of the sensor network.
     * @return If successfully done, it returns the id of sensor network, or null.
     */
    public String createSensorNetworkMetadata(String name, Set<String> members);

    public String createSensorNetworkMetadata(SensorNetworkMetadata metadata);

    /**
     * This method returns all the id of sensor networks in CSN.
     * @return If successfully done, it returns all the ID set of sensors, or null.
     */
    public Set<String> getAllSensorNetworkID();

    public int getAliveSensorNetworkNum();

    public int getDeadSensorNetworkNum();

    public int getAllSensorNetworkNum();

    public Set<String> getSensorNetworkIDs(int index, int num);

    /**
     * This method returns the topic name of a sensor networks in CSN.
     * @param id sensor network's identifier
     * @return If successfully done, it returns the topic name of the sensor network, or null.
     */
    public String getSensorNetworkTopicName(String id);

    /**
     * This method returns all the topic name of sensor networks in CSN.
     * @return If successfully done, it returns all the Topic Name of sensor network, or null.
     */
    public Set<String> getAllSensorNetworkTopicName();

    public Set<String> getSensorNetworkTopicNames(int index, int num);

    /**
     * This method returns the default metadata of a sensor network.
     * @param id sensor network's identifier
     * @return If successfully done, it returns the DefaultSensorNetworkMetadata instance, or null.
     */
    public SNDefaultMetadata getDefaultSensorNetworkMetadata(String id);

    /**
     * This method returns the set of sensors in a sensor network.
     * @param id sensor network's identifier
     * @return If successfully done, it returns the set of sensors which are member of the sensor network, or null.
     */
    public Set<String> getSensorMembersInSensorNetwork(String id);

    /**
     * This method returns the all the sensor network topic and their member list.
     * @return If successfully done, it returns the set of sensors which are member of the sensor network, or null.
     */
    public Map<String, Set<String>> getAllSNTopicNameAndTheirMemberIDs();

    /**
     * This method adds optional sensor network metadata into database.
     * @param id Sensor Network's identifier
     * @param optName Option Name
     * @param optVal Option Value
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType addOptionalSensorNetworkMetadata(String id, String optName, String optVal);

    /**
     * This method adds optional sensork network metadata into database.
     * @param id Sensor Network's identifier
     * @param metadataMap option key-value map
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType addOptionalSensorNetworkMetadata(String id, Map<String, String> metadataMap);

    /**
     * This method returns sensor network's optional metadata name set.
     * @param id Sensor Network's identifier
     * @return If successfully done, it returns name set of optional metadata, or null
     */
    public Set<String> getOptionalSensorNetworkMetadataKeySet(String id);

    /**
     * This method returns sensor network's optional metadata value.
     * @param id Sensor Network's identifier
     * @param optName optional metadata key name
     * @return If successfully done, it returns option value, or null
     */
    public String getOptionalSensorNetworkMetadataValue(String id, String optName);

    /**
     * This method returns sensor network's optional metadata map.
     * @param id Sensor's identifier
     * @return If successfully done, it returns all optional metadata of sensor, or null
     */
    public Map<String, String> getAllOptionalSensorNetworkMetadata(String id);

    /**
     * This method update optional sensor network's metadata in database.
     * @param id Sensor Network's identifier
     * @param optName Option Name
     * @param newOptVal New Option Value
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType updateOptionalSensorNetworkMetadataValue(String id, String optName, String newOptVal);

    /**
     * This method update optional sensor network's metadata in database.
     * @param id Sensor Network's identifier
     * @param metadataMap option key-value map
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType updateOptionalSensorNetworkMetadata(String id, Map<String, String> metadataMap);

    /**
     * This method remove optional sensor network's metadata in database.
     * @param id Sensor Network's identifier
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType removeOptionalSensorNetworkMetadata(String id);

    /**
     * This method remove optional sensor network's metadata in database.
     * @param id Sensor Network's identifier
     * @param optName Option Name
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType removeOptionalSensorNetworkMetadataValue(String id, String optName);

    /**
     * This method check if whether the option value is or not.
     * @param id Sensor Network's identifier
     * @param optName Option Name
     * @return If successfully done, it returns true, or false..
     */
    public boolean isItOptionalSensorNetworkMetadata(String id, String optName);

    /**
     * This method add a tag to a sensor network.
     * @param id Sensor Network's identifier
     * @param tag The tag which is added to a sensor network.
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType addTagToSensorNetwork(String id, String tag);

    /**
     * This method add a tag to a sensor network.
     * @param id Sensor Network's identifier
     * @param tagSet The set of tag which are added to a sensor network.
     * @return If successfully done, it returns 1 of sensor, or -1.
     */
    public DAOReturnType addTagSetToSensorNetwork(String id, Set<String> tagSet);

    /**
     * This method returns all the tag which is added to a sensor network.
     * @param id Sensor Network's identifier
     * @return If successfully done, it returns set of sensor tag, or null.
     */
    public Set<String> getAllTagBySensorNetworkID(String id);

    /**
     * This method returns all the sensor network ID which has the tag.
     * @param tag The tag which is added to a sensor network.
     * @return If successfully done, it returns set of sensor network ID, or null.
     */
    public Set<String> getAllSensorNetworkIDByTag(String tag);

    /**
     * This method returns all the sensor network topic name which has the tag.
     * @param tag The tag which is added to a sensor network.
     * @return If successfully done, it returns set of sensor network topic name, or null.
     */
    public Set<String> getAllSensorNetworkTopicNameByTag(String tag);

    /**
     * This method add a tag to a sensor network.
     * @param id Sensor Network's identifier
     * @param oldTag A old tag which is override with new tag information.
     * @param newTag A new tag which is updated instead of old tag information.
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType updateTagInSensorNetwork(String id, String oldTag, String newTag);

    /**
     * This method remove a tag to a sensor network.
     * @param id Sensor Network's identifier
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType removeAllTagInSensorNetwork(String id);

    /**
     * This method remove a tag to a sensor network.
     * @param id Sensor Network's identifier
     * @param tag The tag which is added to a sensor network.
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType removeTagInSensorNetwork(String id, String tag);

    /**
     * This method returns sensor network metadata instance.
     * @param id Sensor network's identifier
     * @return If successfully done, it returns the metadata of sensor network, or null.
     */
    public SensorNetworkMetadata getSensorNetworkMetadata(String id);

    /**
     * This method remove all metadata of a sensor network.
     * @param id sensor network's identifier
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType removeSensorNetworkMetadata(String id);

    /**
     * This method check if default metadata of a sensor network is or not.
     * @param id sensor network's identifier
     * @return If successfully done, it returns true, or false..
     */
    public boolean isItSensorNetworkMetadata(String id);

    /**
     * This method returns all the sensor network metadata instance.
     * @return If successfully done, it returns all the metadata of sensor network, or null.
     */
    public List<SensorNetworkMetadata> getAllSensorNetworkMetadata();

    /**
     * This method deactivate all the sensor network's metadata in the database;
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType deleteAllSensorNetworkMetadata();

    /**
     * This method remove all the sensor network's metadata in the database;
     * @return If successfully done, it returns RETURN_OK, or RETURN_ERROR.
     */
    public DAOReturnType eraseAllSensorNetworkMetadata();

}
