package cir.lab.rest.network;//package cir.lab.rest.network;
//
//import cir.lab.rest.client.RestAPIFactory;
//import cir.lab.rest.client.SensorAPI;
//import cir.lab.rest.client.SensorNetworkAPI;
//import cir.lab.rest.util.TestUtil;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import org.junit.Test;
//
//import java.io.IOException;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//public class SensorNetworkAPITest {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private SensorNetworkAPI api = new RestAPIFactory().sensorNetworkAPI();
//    private SensorAPI sensorAPI = new RestAPIFactory().sensorAPI();
//    private ObjectMapper jsonMapper = new ObjectMapper();
//    private TestUtil util = new TestUtil();
//
//
//    @Test
//    public void postSensorNetworkMetadataTest() {
//        String id1 = util.addSensorMetaForTest("T-Node1");
//        String id2 = util.addSensorMetaForTest("T-Node2");
//        String id3 = util.addSensorMetaForTest("T-Node3");
//        String input = "{\"seed\":{\"name\":\"All\", \"members\":[\"" + id1 + "\", \"" + id2 + "\", \"" + id3 + "\"]}}";
//        String output = api.postSensorNetworkMetadata(input);
//        logger.info(output);
//        util.checkResultOk(output);
//        api.deleteAllSensorNetworkMetadata();
//        sensorAPI.deleteAllSensorMetadata();
//    }
//
//
//    @Test
//    public void getAllSensorNetworkMetadataTest() {
//        util.addSensorNetworkMetaForTest(5, "TestNet");
//        String output = api.getAllSensorNetworkMetadata();
//        logger.info(output);
//
//        api.deleteAllSensorNetworkMetadata();
//        sensorAPI.deleteAllSensorMetadata();
//    }
//
////    @Test
////    public void deleteAllSensorMetadataTest() {
////        String output = api.deleteAllSensorNetworkMetadata();
////        logger.info(output);
////        util.checkResultOk(output);
////    }
//
//    @Test
//    public void getAllSensorNetworkIDListTest() {
//        util.addSensorNetworkMetaForTest(3, "test-net-1");
//        util.addSensorNetworkMetaForTest(2, "test-net-2");
//        util.addSensorNetworkMetaForTest(5, "test-net3-3");
//
//        String output = api.getAllSensorNetworkIDList();
//        logger.info(output);
//        api.deleteAllSensorNetworkMetadata();
//    }
//
//    @Test
//    public void getAllSensorNetworkTopicNameListTest() {
//        util.addSensorNetworkMetaForTest(3, "test-net-1");
//        util.addSensorNetworkMetaForTest(2, "test-net-2");
//        util.addSensorNetworkMetaForTest(5, "test-net-3");
//
//        String output = api.getAllSensorNetworkTopicNameList();
//        logger.info(output);
//        api.deleteAllSensorNetworkMetadata();
//    }
//
//    @Test
//    public void getAllSensorNetworkMembersTest() {
//        util.addSensorNetworkMetaForTest(3, "test-net-1");
//        util.addSensorNetworkMetaForTest(2, "test-net-2");
//        util.addSensorNetworkMetaForTest(5, "test-net-3");
//
//        String output = api.getAllSensorNetworkMembersList();
//        logger.info(output);
//        api.deleteAllSensorNetworkMetadata();
//    }
//
//    @Test
//    public void getAndDeleteSensorNetworkMetadataTest() {
//        String id = util.addSensorNetworkMetaForTest(3, "test-net-1");
//        String output = api.getSensorNetworkMetadata(id);
//        logger.info(output);
//        try {
//            Map<String,Object> map = jsonMapper.readValue(output, Map.class);
//            Map<String,String> map2 = (Map<String,String>) map.get("def_meta");
//            String actualID = map2.get("id");
//            assertEquals(id, actualID);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        output = api.deleteSensorNetworkMetadata(id);
//        logger.info(output);
//        util.checkResultOk(output);
//    }
//
//    @Test
//    public void getSensorNetworkMembersTest() {
//        String id = util.addSensorNetworkMetaForTest(3, "test-net-1");
//        String output = api.getSensorNetworkMembers(id);
//        logger.info(output);
//
//        output = api.deleteSensorNetworkMetadata(id);
//        logger.info(output);
//        util.checkResultOk(output);
//    }
//
//    @Test
//    public void getSensorNetworkTopicNameTest() {
//        String id = util.addSensorNetworkMetaForTest(3, "test-net-1");
//        String output = api.getSensorNetworkTopicName(id);
//        logger.info(output);
//        output = api.deleteSensorNetworkMetadata(id);
//        logger.info(output);
//        util.checkResultOk(output);
//    }
//
//
//
//    @Test
//    public void postSensorNetworkAllOptionalMetaata() {
//        String input = "{\"opt_meta\":{\"elmts\":{\"opt1\":\"opt val1\", \"opt2\":\"opt val2\"}}}";
//        String id = util.addSensorNetworkMetaForTest(3, "opt-net");
//        String output = api.postSensorNetworkAllOptionalMetadata(id, input);
//        logger.info(output);
//        util.checkResultOk(output);
//
//        api.deleteSensorNetworkMetadata(id);
//    }
//
//    @Test
//    public void getSensorNetworkAllOptionalMetadata() {
//        String input = "{\"opt_meta\":{\"elmts\":{\"opt1\":\"opt val1\", \"opt2\":\"opt val2\"}}}";
//        String id = util.addSensorNetworkMetaForTest(3, "opt-net");
//        api.postSensorNetworkAllOptionalMetadata(id, input);
//
//        String output = api.getSensorNetworkAllOptionalMetadata(id);
//        logger.info(output);
//
//        api.deleteSensorNetworkMetadata(id);
//    }
//
//    @Test
//    public void updateSensorNetworkAllOptionalMetadata() {
//        String input = "{\"opt_meta\":{\"elmts\":{\"opt1\":\"opt val1\", \"opt2\":\"opt val2\"}}}";
//        String id = util.addSensorNetworkMetaForTest(3, "TestOpt");
//        api.postSensorNetworkAllOptionalMetadata(id, input);
//
//        input = "{\"opt_meta\":{\"elmts\":{\"opt1\":\"new val1\", \"opt2\":\"new val2\"}}}";
//        String output = api.updateSensorNetworkAllOptionalMetadata(id, input);
//        util.checkResultOk(output);
//
//        output = api.getSensorNetworkAllOptionalMetadata(id);
//        logger.info(output);
//
//        api.deleteSensorNetworkMetadata(id);
//    }
//
//
//    @Test
//    public void deleteSensorNetworkAllOptionalMetadata() {
//        String input = "{\"opt_meta\":{\"elmts\":{\"opt1\":\"opt val1\", \"opt2\":\"opt val2\"}}}";
//        String id = util.addSensorNetworkMetaForTest(3, "TestOpt");
//        api.postSensorNetworkAllOptionalMetadata(id, input);
//
//        String output = api.deleteSensorNetworkAllOptionalMetadata(id);
//        logger.info(output);
//        util.checkResultOk(output);
//    }
//
//
//    @Test
//    public void getSensorNetworkOptionalMetadata() {
//        String id = util.addSensorNetworkMetaForTest(3, "TestOpt");
//        String optName = "opt1";
//        String optVal = "val";
//        String input = "{\"opt_meta\":{\"elmts\":{\"" + optName + "\":\"" + optVal + "\", \"opt2\":\"opt val2\"}}}";
//        api.postSensorNetworkAllOptionalMetadata(id, input);
//        String output = api.getSensorNetworkOptionalMetadata(id, "opt1");
//        logger.info(output);
//        try {
//            Map<String,String> map = jsonMapper.readValue(output, Map.class);
//            String actualOptVal = map.get(optName);
//            assertEquals(optVal, actualOptVal);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//
//    @Test
//    public void updateSensorNetworkOptionalMetadata() {
//        String id = util.addSensorNetworkMetaForTest(3, "TestOpt");
//        String optName = "optTest";
//        String optVal = "val";
//        String newOptVal = "new val";
//        String input = "{\"opt_meta\":{\"elmts\":{\"" + optName + "\":\"" + optVal + "\", \"opt2\":\"opt val2\"}}}";
//        api.postSensorNetworkAllOptionalMetadata(id, input);
//
//        String output = api.updateSensorNetworkOptionalMetadata(id, optName, newOptVal);
//        logger.info(output);
//        util.checkResultOk(output);
//        api.deleteSensorNetworkMetadata(id);
//    }
//
//
//    @Test (expected = RuntimeException.class)
//    public void deleteSensorNetworkOptionalMetadata() {
//        String id = util.addSensorNetworkMetaForTest(3, "TestOpt");
//        String optName = "opt1";
//        String optVal = "val";
//        String input = "{\"opt_meta\":{\"elmts\":{\"" + optName + "\":\"" + optVal + "\", \"opt2\":\"opt val2\"}}}";
//        api.postSensorNetworkAllOptionalMetadata(id, input);
//
//        String output = api.deleteSensorNetworkOptionalMetadata(id, optName);
//        logger.info(output);
//        util.checkResultOk(output);
//
//        output = api.getSensorNetworkOptionalMetadata(id, optName);
//        logger.info(output);
//        api.deleteSensorNetworkMetadata(id);
//    }
//
//    @Test
//    public void postSensorNetworkAllTagMetadata() {
//        String input = "{\"tag_meta\":{\"tags\":[\"tag2\", \"tag4\"]}}";
//        String id = util.addSensorNetworkMetaForTest(3, "TestOpt");
//        String output = api.postSensorNetworkAllTagMetadata(id, input);
//        logger.info(output);
//        util.checkResultOk(output);
//
//        api.deleteSensorNetworkMetadata(id);
//    }
//
//    @Test
//    public void getSensorNetworkAllTagMetadata() {
//        String input = "{\"tag_meta\":{\"tags\":[\"tag2\", \"tag4\"]}}";
//        String id = util.addSensorNetworkMetaForTest(3, "TestOpt");
//        String output = api.postSensorNetworkAllTagMetadata(id, input);
//        logger.info(output);
//
//        output = api.getSensorNetworkAllTagMetadata(id);
//        logger.info(output);
//        api.deleteSensorNetworkMetadata(id);
//    }
//
//    @Test
//    public void deleteSensorAllTagMetadata() {
//        String input = "{\"tag_meta\":{\"tags\":[\"tag2\", \"tag4\"]}}";
//        String id = util.addSensorNetworkMetaForTest(3, "TestOpt");
//        String output = api.postSensorNetworkAllTagMetadata(id, input);
//        logger.info(output);
//
//        output = api.deleteSensorNetworkAllTagMetadata(id);
//        logger.info(output);
//        util.checkResultOk(output);
//    }
//
//    @Test
//    public void updateSensorTagMetadata() {
//        String tag1 = "tag1";
//        String newTag = "tag2";
//        String input = "{\"tag_meta\":{\"tags\":[\"" + tag1 + "\", \"tag3\"]}}";
//        String id = util.addSensorNetworkMetaForTest(3, "TestOpt");
//        String output = api.postSensorNetworkAllTagMetadata(id, input);
//        logger.info(output);
//        output = api.updateSensorNetworkTagMetadata(id, tag1, newTag);
//        logger.info(output);
//
//        output = api.getSensorNetworkAllTagMetadata(id);
//        try {
//            Set<String> set = (Set<String>) jsonMapper.readValue(output, Set.class);
//            assertTrue(set.contains(newTag));
//            assertFalse(set.contains(tag1));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        logger.info(output);
//        api.deleteSensorNetworkMetadata(id);
//    }
//
//
//    @Test
//    public void deleteSensorTagMetadata() {
//        String tag1 = "tag1";
//        String tag2 = "tag2";
//        String input = "{\"tag_meta\":{\"tags\":[\"" + tag1 + "\", \"" + tag2 + "\"]}}";
//        String id = util.addSensorNetworkMetaForTest(3, "TestOpt");
//        String output = api.postSensorNetworkAllTagMetadata(id, input);
//        logger.info(output);
//        output = api.deleteSensorNetworkTagMetadata(id, tag1);
//        logger.info(output);
//        util.checkResultOk(output);
//        output = api.getSensorNetworkAllTagMetadata(id);
//        try {
//            Set<String> set = (Set<String>) jsonMapper.readValue(output, Set.class);
//            assertTrue(set.contains(tag2));
//            assertFalse(set.contains(tag1));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        logger.info(output);
//        api.deleteSensorNetworkMetadata(id);
//    }
//
//}
