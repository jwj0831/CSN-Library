package cir.lab.rest.client;

/**
 * Created by nfm on 2014. 6. 5..
 */
public interface SensorAPI {

    public String postSensorMetadata(String input);
    public String getAllSensorMetadata();
    public String deleteAllSensorMetadata();

    public String getAllSensorIDList();

    public String getSensorMetadata(String id);
    public String deleteSensorMetadata(String id);

    public String postSensorAllOptionalMetadata(String id, String input);
    public String getSensorAllOptionalMetadata(String id);
    public String updateSensorAllOptionalMetadata(String id, String input);
    public String deleteSensorAllOptionalMetadata(String id);

    public String getSensorOptionalMetadata(String id, String optName);
    public String updateSensorOptionalMetadata(String id, String optName, String optVal);
    public String deleteSensorOptionalMetadata(String id, String optName);

    public String postSensorAllTagMetadata(String id, String input);
    public String getSensorAllTagMetadata(String id);
    public String deleteSensorAllTagMetadata(String id);

    public String updateSensorTagMetadata(String id, String tag, String newTag);
    public String deleteSensorTagMetadata(String id, String tag);
}
