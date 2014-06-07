package cir.lab.rest.sensor;

//import org.junit.After;
//import org.junit.Before;
import cir.lab.rest.client.RestAPIFactory;
import cir.lab.rest.client.SensorAPI;
import cir.lab.rest.util.TestUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class SensorAPITest {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private SensorAPI sensorAPI = new RestAPIFactory().sensorAPI();
    private ObjectMapper jsonMapper = new ObjectMapper();
    private TestUtil util = new TestUtil();

    @Test
    public void postSensorMetadataTest() {
        String input = "{\"seed\":{\"name\":\"Node11\", \"measure\":\"Weather\"}}";
        String output = sensorAPI.postSensorMetadata(input);
        logger.info(output);
        util.checkResultOk(output);
    }


    @Test
    public void getAllSensorMetadataTest() {
        String output = sensorAPI.getAllSensorMetadata();
        logger.info(output);
    }

    @Test
    public void deleteAllSensorMetadataTest() {
        String output = sensorAPI.deleteAllSensorMetadata();
        logger.info(output);
        util.checkResultOk(output);
    }

    @Test
    public void getAllSensorIDListTest() {
        String output = sensorAPI.getAllSensorIDList();
        logger.info(output);
    }

    @Test
    public void getAndDeleteSensorMetadataTest() {
        String id = util.addSensorMetaForTest("TestGet");
        String output = sensorAPI.getSensorMetadata(id);
        logger.info(output);
        try {
            Map<String,Object> map = jsonMapper.readValue(output, Map.class);
            Map<String,String> map2 = (Map<String,String>) map.get("def_meta");
            String actualID = map2.get("id");
            assertEquals(id, actualID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        output = sensorAPI.deleteSensorMetadata(id);
        logger.info(output);
        util.checkResultOk(output);
    }

    @Test
    public void postSensorAllOptionalMetadata() {
        String input = "{\"opt_meta\":{\"elmts\":{\"opt1\":\"opt val1\", \"opt2\":\"opt val2\"}}}";
        String id = util.addSensorMetaForTest("TestOpt");
        String output = sensorAPI.postSensorAllOptionalMetadata(id, input);
        logger.info(output);
        util.checkResultOk(output);

        sensorAPI.deleteSensorMetadata(id);
    }

    @Test
    public void getSensorAllOptionalMetadata() {
        String input = "{\"opt_meta\":{\"elmts\":{\"opt1\":\"opt val1\", \"opt2\":\"opt val2\"}}}";
        String id = util.addSensorMetaForTest("TestOpt");
        sensorAPI.postSensorAllOptionalMetadata(id, input);

        String output = sensorAPI.getSensorAllOptionalMetadata(id);
        logger.info(output);

        sensorAPI.deleteSensorMetadata(id);
    }

    @Test
    public void updateSensorAllOptionalMetadata() {
        String input = "{\"opt_meta\":{\"elmts\":{\"opt1\":\"opt val1\", \"opt2\":\"opt val2\"}}}";
        String id = util.addSensorMetaForTest("TestOpt");
        sensorAPI.postSensorAllOptionalMetadata(id, input);

        input = "{\"opt_meta\":{\"elmts\":{\"opt1\":\"new val1\", \"opt2\":\"new val2\"}}}";
        String output = sensorAPI.updateSensorAllOptionalMetadata(id, input);
        util.checkResultOk(output);

        output = sensorAPI.getSensorAllOptionalMetadata(id);
        logger.info(output);

        sensorAPI.deleteSensorMetadata(id);
    }


    @Test
    public void deleteSensorAllOptionalMetadata() {
        String id = util.addSensorMetaForTest("TestOpt");
        String input = "{\"opt_meta\":{\"elmts\":{\"opt1\":\"opt val1\", \"opt2\":\"opt val2\"}}}";
        sensorAPI.postSensorAllOptionalMetadata(id, input);
        String output = sensorAPI.deleteSensorAllOptionalMetadata(id);
        logger.info(output);
        util.checkResultOk(output);
    }


    @Test
    public void getSensorOptionalMetadata() {
        String id = util.addSensorMetaForTest("TestOpt");
        String optName = "opt1";
        String optVal = "val";
        String input = "{\"opt_meta\":{\"elmts\":{\"" + optName + "\":\"" + optVal + "\", \"opt2\":\"opt val2\"}}}";
        sensorAPI.postSensorAllOptionalMetadata(id, input);
        String output = sensorAPI.getSensorOptionalMetadata(id, "opt1");
        logger.info(output);
        try {
            Map<String,String> map = jsonMapper.readValue(output, Map.class);
            String actualOptVal = map.get(optName);
            assertEquals(optVal, actualOptVal);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void updateSensorOptionalMetadata() {
        String id = util.addSensorMetaForTest("TestOpt");
        String optName = "optTest";
        String optVal = "val";
        String newOptVal = "new val";
        String input = "{\"opt_meta\":{\"elmts\":{\"" + optName + "\":\"" + optVal + "\", \"opt2\":\"opt val2\"}}}";
        sensorAPI.postSensorAllOptionalMetadata(id, input);

        String output = sensorAPI.updateSensorOptionalMetadata(id, optName, newOptVal);
        logger.info(output);
        util.checkResultOk(output);
        sensorAPI.deleteSensorMetadata(id);
    }


    @Test (expected = RuntimeException.class)
    public void deleteSensorOptionalMetadata() {
        String id = util.addSensorMetaForTest("TestOpt");
        String optName = "opt1";
        String optVal = "val";
        String input = "{\"opt_meta\":{\"elmts\":{\"" + optName + "\":\"" + optVal + "\", \"opt2\":\"opt val2\"}}}";
        sensorAPI.postSensorAllOptionalMetadata(id, input);

        String output = sensorAPI.deleteSensorOptionalMetadata(id, optName);
        logger.info(output);
        util.checkResultOk(output);

        output = sensorAPI.getSensorOptionalMetadata(id, optName);
        logger.info(output);
        sensorAPI.deleteSensorMetadata(id);
    }

    @Test
    public void postSensorAllTagMetadata() {
        String input = "{\"tag_meta\":{\"tags\":[\"tag2\", \"tag4\"]}}";
        String id = util.addSensorMetaForTest("TestOpt");
        String output = sensorAPI.postSensorAllTagMetadata(id, input);
        logger.info(output);
        util.checkResultOk(output);

        sensorAPI.deleteSensorMetadata(id);
    }

    @Test
    public void getSensorAllTagMetadata() {
        String input = "{\"tag_meta\":{\"tags\":[\"tag2\", \"tag4\"]}}";
        String id = util.addSensorMetaForTest("TestOpt");
        String output = sensorAPI.postSensorAllTagMetadata(id, input);
        logger.info(output);

        output = sensorAPI.getSensorAllTagMetadata(id);
        logger.info(output);
        sensorAPI.deleteSensorMetadata(id);
    }

    @Test
    public void deleteSensorAllTagMetadata() {
        String input = "{\"tag_meta\":{\"tags\":[\"tag2\", \"tag4\"]}}";
        String id = util.addSensorMetaForTest("TestOpt");
        String output = sensorAPI.postSensorAllTagMetadata(id, input);
        logger.info(output);

        output = sensorAPI.deleteSensorAllTagMetadata(id);
        logger.info(output);
        util.checkResultOk(output);
    }

    @Test
    public void updateSensorTagMetadata() {
        String tag1 = "tag1";
        String newTag = "tag2";
        String input = "{\"tag_meta\":{\"tags\":[\"" + tag1 + "\", \"tag3\"]}}";
        String id = util.addSensorMetaForTest("TestOpt");
        String output = sensorAPI.postSensorAllTagMetadata(id, input);
        logger.info(output);
        output = sensorAPI.updateSensorTagMetadata(id, tag1, newTag);
        logger.info(output);

        output = sensorAPI.getSensorAllTagMetadata(id);
        try {
            Set<String> set = (Set<String>) jsonMapper.readValue(output, Set.class);
            assertTrue(set.contains(newTag));
            assertFalse(set.contains(tag1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(output);
        sensorAPI.deleteSensorMetadata(id);
    }


    @Test
    public void deleteSensorTagMetadata() {
        String tag1 = "tag1";
        String tag2 = "tag2";
        String input = "{\"tag_meta\":{\"tags\":[\"" + tag1 + "\", \"" + tag2 + "\"]}}";
        String id = util.addSensorMetaForTest("TestOpt");
        String output = sensorAPI.postSensorAllTagMetadata(id, input);
        logger.info(output);
        output = sensorAPI.deleteSensorTagMetadata(id, tag1);
        logger.info(output);
        util.checkResultOk(output);
        output = sensorAPI.getSensorAllTagMetadata(id);
        try {
            Set<String> set = (Set<String>) jsonMapper.readValue(output, Set.class);
            assertTrue(set.contains(tag2));
            assertFalse(set.contains(tag1));
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info(output);
        sensorAPI.deleteSensorMetadata(id);
    }
}
