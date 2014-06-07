package cir.lab.rest.util;

import cir.lab.rest.client.RestAPIFactory;
import cir.lab.rest.client.SensorAPI;
import cir.lab.rest.client.SensorNetworkAPI;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class TestUtil {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private SensorAPI sensorAPI = new RestAPIFactory().sensorAPI();
    private SensorNetworkAPI sensorNetworkAPI = new RestAPIFactory().sensorNetworkAPI();
    private ObjectMapper jsonMapper = new ObjectMapper();

    public String addSensorMetaForTest(String name){
        String input = "{\"seed\":{\"name\":\"" + name + "\", \"measure\":\"Weather\"}}";
        String output = sensorAPI.postSensorMetadata(input);
        logger.info(output);
        String id = null;
        try {
            Map<String,String> map = jsonMapper.readValue(output, Map.class);
            id = map.get("id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

     public void checkResultOk(String output) {
        try {
            Map<String,String> map = jsonMapper.readValue(output, Map.class);
            String result = map.get("result");
            assertEquals("OK", result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String addSensorNetworkMetaForTest(int snsrNum, String snName) {
        String memberString = "";
        for(int i=1; i<=snsrNum;i++ ){
            String tempSnsrName = "test-node-" + i;
            logger.info("Created sensor name: {}", tempSnsrName);
            String tempID = addSensorMetaForTest(tempSnsrName);
            memberString += "\"" + tempID + "\"";
            if(i < snsrNum)
                memberString += ", ";
            logger.info("Current Member List: {}", memberString);
        }
        String input = "{\"seed\":{\"name\":\"" + snName + "\", \"members\":[" + memberString + "]}}";
        String output = sensorNetworkAPI.postSensorNetworkMetadata(input);
        logger.info(output);
        String id = null;
        try {
            Map<String,String> map = jsonMapper.readValue(output, Map.class);
            id = map.get("id");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return id;
    }

}
