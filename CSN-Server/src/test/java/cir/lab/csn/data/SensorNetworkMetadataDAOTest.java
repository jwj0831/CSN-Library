package cir.lab.csn.data;

import cir.lab.csn.client.SensorNetworkManager;
import cir.lab.csn.component.SensorNetworkManagerImpl;
import cir.lab.csn.metadata.network.SensorNetworkMetadata;
import cir.lab.csn.metadata.network.SNDefaultMetadata;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.junit.Assert.*;

public class SensorNetworkMetadataDAOTest {
    Logger logger = LoggerFactory.getLogger(SensorNetworkMetadataDAOTest.class);
    private SensorNetworkManager controller;

    //Test Name Set
    final static String name1 = "N1";
    final static String name2 = "N2";
    final static String name3 = "N3";
    final static String name4 = "N4";
    final static String name5 = "N5";
    final static String name6 = "N6";

    //Test Measurement Set
    final static String measurement1 = "Weather";
    final static String measurement2 = "Temperature";
    final static String measurement3 = "PM10";

    //Test Name Set
    final static String netName1 = "Net1";
    final static String netName2 = "Net2";
    final static String netName3 = "Net3";
    final static String netName4 = "Net4";
    final static String netName5 = "Net5";
    final static String netName6 = "Net6";

    //Test Member ID
    final static String member1 = "/N1/1401539641807";
    final static String member2 = "/N2/1401539645891";
    final static String member3 = "/N3/1401539651000";
    final static String member4 = "/N4/1401539655453";
    final static String member5 = "/N5/1401598331671";
    final static String member6 = "/N6/1401642712852";

    //Test Datetime
//    final static String time1 = "2014-05-31 21:34:02";
//    final static String time2 = "2014-05-31 21:34:06";
//    final static String time3 = "2014-05-31 21:34:11";
//    final static String time4 = "2014-05-31 21:34:15";
//    final static String time5 = "2014-06-01 13:52:12";
//    final static String time6 = "2014-06-02 02:11:53";

    //Test Key
//    final static String key1 = "2d207a02df6601572b6542f00bc90f3f9becf358";
//    final static String key2 = "fbbe099f4bb9308383d15ba3ec9573832db7346a";
//    final static String key3 = "071124d2bfa1ab3cacc3a16e1400a9978335706b";
    final static String key4 = "edc405c91717db8943b0fe6c41be8e2e47f2d3bd";
    final static String key5 = "56e17c6faa0520a892ed9fbfbacf11285d1112ea";
    final static String key6 = "77f5737369aae5cfa6f93fdf9599215b596586e6";

    //Test Tag Set
    final static String tag1 = "IoT";
    final static String tag2 = "WSN";
    final static String tag3 = "Seoul";
    final static String tag4 = "Busan";
    final static String tag5 = "Cellular";
    final static String tag6 = "Ethernet";
    final static String tag7 = "SKT";
    final static String tag8 = "KT";

    //Test Optional Metadata
    final static String optName1 = "option name 1";
    final static String optName2 = "option name 2";
    final static String optName3 = "option name 3";
    final static String optName4 = "option name 4";
    final static String optName5 = "option name 5";
    final static String optName6 = "option name 6";
    final static String optVal1 = "option value 1";
    final static String optVal2 = "option value 2";
    final static String optVal3 = "option value 3";
    final static String optVal4 = "option value 4";
    final static String optVal5 = "option value 5";
    final static String optVal6 = "option value 6";


    @Before
    public void setUp() {
        controller = new SensorNetworkManagerImpl();
    }

    @Test
    public void addDefaultSensorNetworkMetadataTest() {
        String id1 = controller.createSensorMetadata(name1, measurement1);
        String id2 = controller.createSensorMetadata(name2, measurement1);
        String id3 = controller.createSensorMetadata(name3, measurement2);
        String id4 = controller.createSensorMetadata(name4, measurement2);
        String id5 = controller.createSensorMetadata(name5, measurement3);
        String id6 = controller.createSensorMetadata(name6, measurement3);

        Set<String> members = new HashSet<String>();
        members.add(id1);
        members.add(id2);
        members.add(id3);
        String netUri1 = controller.createSensorNetworkMetadata(netName1, members);
        logger.info("SN ID: {}", netUri1);

        SNDefaultMetadata defaultSensorNetworkMetadata = controller.getDefaultSensorNetworkMetadata(netUri1);

        logger.info("SN Name: {}",defaultSensorNetworkMetadata.getName());
        logger.info("Topic Name: {}",defaultSensorNetworkMetadata.getTopicName());

        Set<String> actualMembers = controller.getSensorMembersInSensorNetwork(netUri1);
        assertTrue(actualMembers.contains(id1));
        assertTrue(actualMembers.contains(id2));
        assertTrue(actualMembers.contains(id3));

        actualMembers.remove(id1);
        assertFalse(actualMembers.contains(id1));
        assertTrue(actualMembers.contains(id2));
        assertTrue(actualMembers.contains(id3));

        actualMembers.remove(id2);
        assertFalse(actualMembers.contains(id1));
        assertFalse(actualMembers.contains(id2));
        assertTrue(actualMembers.contains(id3));

        actualMembers.remove(id3);
        assertFalse(actualMembers.contains(id1));
        assertFalse(actualMembers.contains(id2));
        assertFalse(actualMembers.contains(id3));
    }

//    @Test
//    public void addSensorToSensorNetworkTest() {
//        DAOReturnType retType;
//
//        String snsrName1 = "TempTest1";
//        String measurement = "Test";
//        String snsrID1 = controller.createSensorMetadata(snsrName1, measurement);
//        String id = controller.createSensorNetworkMetadata("TempNet", null);
//        retType = controller.addSensorToSensorNetwork(id, snsrID1);
//
//        assertEquals(DAOReturnType.RETURN_OK, retType);
//
//        Set<String> members = controller.getSensorMembersInSensorNetwork(id);
//        String actualSnsrID = members.iterator().next();
//        assertEquals(snsrID1, actualSnsrID);
//    }

    @Test
    public void getAllSensorNetworkIDTest() {
        List<String> idList = new ArrayList<String>();
        int idNum = 10;
        for(int i = 1; i <= 10; i++ ) {
            String name = "TestName" + i;
            String tempID = controller.createSensorNetworkMetadata(name, null);
            idList.add(tempID);
        }
        Set<String> idSet = controller.getAllSensorNetworkID();
        assertNotNull(idSet);

        Iterator<String> idIter = idList.iterator();
        while(idIter.hasNext()) {
            String tempID = idIter.next();
            logger.info("Get SN ID: {}", tempID);
            assertTrue(idSet.contains(tempID));
            idSet.remove(tempID);
            assertFalse(idSet.contains(tempID));
        }
    }

    @Test
    public void getSensorNetworkTopicNameTest() {
        String name = "TestName";
        String tempID = controller.createSensorNetworkMetadata(name, null);
        SNDefaultMetadata tempMeta = controller.getDefaultSensorNetworkMetadata(tempID);
        String topicName = tempMeta.getTopicName();
        String actualTopicName = controller.getSensorNetworkTopicName(tempID);
        assertEquals(topicName, actualTopicName);
    }

    @Test
    public void getAllSensorNetworkTopicNameTest() {
        Set<String> topicSet = new HashSet<String>();
        int idNum = 10;
        for(int i = 1; i <= 10; i++ ) {
            String name = "TestName" + i;
            String tempID = controller.createSensorNetworkMetadata(name, null);
            SNDefaultMetadata tempMeta = controller.getDefaultSensorNetworkMetadata(tempID);
            topicSet.add(tempMeta.getTopicName());
        }
        Set<String> topicNameSet = controller.getAllSensorNetworkTopicName();
        assertNotNull(topicNameSet);

        Iterator<String> topicIter = topicSet.iterator();
        while(topicIter.hasNext()) {
            String tempID = topicIter.next();
            logger.info("Get TopicName: {}", tempID);
            assertTrue(topicNameSet.contains(tempID));
            topicNameSet.remove(tempID);
            assertFalse(topicNameSet.contains(tempID));
        }
    }

    @Test
    public void optionalSensorMetadataTest() {
        DAOReturnType retType;
        String name = "TestName";
        String id = controller.createSensorNetworkMetadata(name, null);
        String optName = "option name";
        String optVal = "option value";

        retType = controller.addOptionalSensorNetworkMetadata(id, optName, optVal);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        logger.info("ID: {}\tOption Name: {}", id, optName);

        String actualOptVal = controller.getOptionalSensorNetworkMetadataValue(id, optName);
        assertEquals(optVal, actualOptVal);

        Set<String> keySet = controller.getOptionalSensorNetworkMetadataKeySet(id);
        assertNotNull(keySet);
        Iterator<String> iter = keySet.iterator();
        while(iter.hasNext())
            logger.info("Option Name: {}", iter.next());

        assertTrue(keySet.contains(optName));

        Map<String, String> metadataMap = controller.getAllOptionalSensorNetworkMetadata(id);
        assertNotNull(metadataMap);

        retType = controller.removeOptionalSensorNetworkMetadataValue(id, optName);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        boolean retVal = controller.isItOptionalSensorNetworkMetadata(id, optName);
        assertFalse(retVal);
    }

    @Test
    public void optionalSensorMetadataMapTest() {
        DAOReturnType retType;
        int optNum = 5;
        String name = "TestName";
        String id = controller.createSensorNetworkMetadata(name, null);

        String optName = null;
        String optVal = null;
        Map<String, String> metadataMap = new HashMap<String, String>();
        for(int i = 1; i <= optNum; i++) {
            optName = "option name " + i;
            optVal = "option value " + i;
            metadataMap.put(optName, optVal);
        }

        retType = controller.addOptionalSensorNetworkMetadata(id, metadataMap);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        Map<String, String> actualMetadataMap = controller.getAllOptionalSensorNetworkMetadata(id);
        assertNotNull(actualMetadataMap);

        Set<String> keySet = controller.getOptionalSensorNetworkMetadataKeySet(id);
        assertNotNull(keySet);
        Iterator<String> iter = keySet.iterator();
        while(iter.hasNext()) {
            optName = iter.next();
            logger.info("Option Name: {}", optName);
            logger.info("Expected opt val: {}\tActual opt val: {}", metadataMap.get(optName), actualMetadataMap.get(optName));
            assertEquals(metadataMap.get(optName), actualMetadataMap.get(optName));
        }
    }

    @Test
    public void updateOptionalSensorMetadataValueTest() {
        DAOReturnType retType;
        String name = "TestName";
        String id = controller.createSensorNetworkMetadata(name, null);
        String optName = "option name";
        String optVal = "value";

        retType = controller.addOptionalSensorNetworkMetadata(id, optName, optVal);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        logger.info("ID: {}\tOption Name: {}", id, optName);

        String new_val = "new option value";
        retType = controller.updateOptionalSensorNetworkMetadataValue(id, optName, new_val);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        String actual_new_val = controller.getOptionalSensorNetworkMetadataValue(id, optName);
        assertEquals(new_val, actual_new_val);

        retType = controller.removeOptionalSensorNetworkMetadataValue(id, optName);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        boolean retVal = controller.isItOptionalSensorNetworkMetadata(id, optName);
        assertFalse(retVal);
    }

    @Test
    public void sensorNetworkTagTest() {
        DAOReturnType retType;
        String name = "TestName";
        String id = controller.createSensorNetworkMetadata(name, null);
        String tag = "testTag";
        String newTag = "newTestTag";
        Set<String> tagSet = null;

        retType = controller.addTagToSensorNetwork(id, tag);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        tagSet = controller.getAllTagBySensorNetworkID(id);
        assertNotNull(tagSet);
        assertTrue(tagSet.contains(tag));
        assertFalse(tagSet.contains(newTag));

        retType = controller.updateTagInSensorNetwork(id, tag, newTag);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        tagSet = controller.getAllTagBySensorNetworkID(id);
        assertNotNull(tagSet);
        assertTrue(tagSet.contains(newTag));
        assertFalse(tagSet.contains(tag));

        retType = controller.removeTagInSensorNetwork(id, newTag);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        tagSet = controller.getAllTagBySensorNetworkID(id);
        assertFalse(tagSet.contains(newTag));
    }

    @Test
    public void getAllSensorNetworkIDbyTagTest() {
        DAOReturnType retType;
        String name1 = "TempNetwork1";
        String name2 = "TempNetwork2";
        String name3 = "TempNetwork3";
        String id1 = controller.createSensorNetworkMetadata(name1, null);
        String id2 = controller.createSensorNetworkMetadata(name2, null);
        String id3 = controller.createSensorNetworkMetadata(name3, null);
        String tag = "testTag";
        Set<String> idSet = null;

        retType = controller.addTagToSensorNetwork(id1, tag);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        retType = controller.addTagToSensorNetwork(id2, tag);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        retType = controller.addTagToSensorNetwork(id3, tag);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        idSet = controller.getAllSensorNetworkIDByTag(tag);
        assertNotNull(idSet);
        Iterator<String> idIter = idSet.iterator();
        while(idIter.hasNext()) {
            String id = idIter.next();
            logger.info("ID by tag {} : {}", tag, id);
        }
    }

    @Test
    public void getAllSensorNetworkTopicNameByTagTest() {
        DAOReturnType retType;
        String name1 = "TempNetwork1";
        String name2 = "TempNetwork2";
        String name3 = "TempNetwork3";
        String id1 = controller.createSensorNetworkMetadata(name1, null);
        String id2 = controller.createSensorNetworkMetadata(name2, null);
        String id3 = controller.createSensorNetworkMetadata(name3, null);

        String topic1 = controller.getSensorNetworkTopicName(id1);
        String topic2 = controller.getSensorNetworkTopicName(id2);
        String topic3 = controller.getSensorNetworkTopicName(id3);

        String tag = "testTag";

        Set<String> topicSet = null;

        retType = controller.addTagToSensorNetwork(id1, tag);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        retType = controller.addTagToSensorNetwork(id2, tag);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        retType = controller.addTagToSensorNetwork(id3, tag);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        topicSet = controller.getAllSensorNetworkTopicNameByTag(tag);
        assertNotNull(topicSet);
        Iterator<String> topicIter = topicSet.iterator();
        while(topicIter.hasNext()) {
            String topicName = topicIter.next();
            logger.info("Topic by tag {} : {}", tag, topicName);
        }

        assertTrue(topicSet.contains(topic1));
        assertTrue(topicSet.contains(topic2));
        assertTrue(topicSet.contains(topic3));

        topicSet.remove(topic1);
        assertFalse(topicSet.contains(topic1));
        assertTrue(topicSet.contains(topic2));
        assertTrue(topicSet.contains(topic3));

        topicSet.remove(topic2);
        assertFalse(topicSet.contains(topic1));
        assertFalse(topicSet.contains(topic2));
        assertTrue(topicSet.contains(topic3));

        topicSet.remove(topic3);
        assertFalse(topicSet.contains(topic1));
        assertFalse(topicSet.contains(topic2));
        assertFalse(topicSet.contains(topic3));
    }

    @Test
    public void addTagSetToSensorNetworkTest() {
        DAOReturnType retType;
        String name = "TestName";
        String id = controller.createSensorNetworkMetadata(name, null);
        String tag1 = "testTag1";
        String tag2 = "testTag2";
        String tag3 = "testTag3";
        Set<String> tagSet = new HashSet<String>();
        Set<String> actualTagSet = null;
        tagSet.add(tag1);
        tagSet.add(tag2);
        tagSet.add(tag3);

        retType = controller.addTagSetToSensorNetwork(id, tagSet);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        actualTagSet = controller.getAllTagBySensorNetworkID(id);
        assertNotNull(actualTagSet);
        assertTrue(actualTagSet.contains(tag1));
        assertTrue(actualTagSet.contains(tag2));
        assertTrue(actualTagSet.contains(tag3));

        retType = controller.removeTagInSensorNetwork(id, tag1);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        actualTagSet = controller.getAllTagBySensorNetworkID(id);
        assertNotNull(actualTagSet);
        assertFalse(actualTagSet.contains(tag1));
        assertTrue(actualTagSet.contains(tag2));
        assertTrue(actualTagSet.contains(tag3));

        retType = controller.removeTagInSensorNetwork(id, tag2);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        actualTagSet = controller.getAllTagBySensorNetworkID(id);
        assertNotNull(actualTagSet);
        assertFalse(actualTagSet.contains(tag1));
        assertFalse(actualTagSet.contains(tag2));
        assertTrue(actualTagSet.contains(tag3));

        retType = controller.removeTagInSensorNetwork(id, tag3);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        actualTagSet = controller.getAllTagBySensorNetworkID(id);
        assertNotNull(actualTagSet);
        assertFalse(actualTagSet.contains(tag1));
        assertFalse(actualTagSet.contains(tag2));
        assertFalse(actualTagSet.contains(tag3));
    }


    @Test
    public void getSensorNetworkMetadataTest() {
        String name = "TestName";
        String id = controller.createSensorNetworkMetadata(name, null);

        int num = 10;
        String optName = null;
        String optVal = null;
        Map<String, String> metadataMap = new HashMap<String, String>();
        for(int i = 1; i <= num; i++) {
            optName = "option name " + i;
            optVal = "option value " + i;
            metadataMap.put(optName, optVal);
        }
        controller.addOptionalSensorNetworkMetadata(id, metadataMap);

        Set<String> tagSet = new HashSet<String>();
        String tempTag = null;
        for(int i = 1; i <= num; i++) {
            tempTag = "Test Tag " + i;
            tagSet.add(tempTag);
        }
        controller.addTagSetToSensorNetwork(id, tagSet);

        SensorNetworkMetadata meta = controller.getSensorNetworkMetadata(id);
        assertNotNull(meta);
        logger.info("ID: {}", meta.getDef_meta().getId());
        logger.info("Name: {}", meta.getDef_meta().getName());
        logger.info("Topic Name: {}", meta.getDef_meta().getTopicName());
        logger.info("Creation Time: {}", meta.getDef_meta().getCreationTime());

        Iterator<String> tagIter = meta.getSnsr_tag().getTags().iterator();
        while(tagIter.hasNext()) {
            String iterTag = tagIter.next();
            logger.info("Tag: {}", iterTag);
            assertTrue(tagSet.contains(iterTag));
        }

        Map<String,String> optionalMetadataMap = meta.getOpt_meta().getElmts();
        Iterator<String> optIter = optionalMetadataMap.keySet().iterator();
        while(optIter.hasNext()) {
            optName = optIter.next();
            optVal = optionalMetadataMap.get(optName);
            logger.info("Opt Name: {}, Opt Val: {}", optName, optVal);
            assertEquals(metadataMap.get(optName), optVal);
        }
    }

    @Test
    public void getAllSensorNetworkMetadataTest() {
        int num = 10;
        for(int i = 1; i <= num; i++ ) {
            String name = "TestName" + i;
            String id = controller.createSensorNetworkMetadata(name, null);

            String optName = null;
            String optVal = null;
            Map<String, String> metadataMap = new HashMap<String, String>();
            for(int j = 1; i <= num; i++) {
                optName = "option name " + i;
                optVal = "option value " + i;
                metadataMap.put(optName, optVal);
            }
            controller.addOptionalSensorNetworkMetadata(id, metadataMap);

            Set<String> tagSet = new HashSet<String>();
            String tempTag = null;
            for(int j = 1; i <= num; i++) {
                tempTag = "Test Tag " + i;
                tagSet.add(tempTag);
            }
            controller.addTagSetToSensorNetwork(id, tagSet);
        }


        List<SensorNetworkMetadata> list = null;
        list = controller.getAllSensorNetworkMetadata();
        assertNotNull(list);
        Iterator<SensorNetworkMetadata> iter = list.iterator();
        while(iter.hasNext()) {
            SensorNetworkMetadata meta = iter.next();
            logger.info("ID: {}", meta.getDef_meta().getId());
            logger.info("Name: {}", meta.getDef_meta().getName());
            logger.info("TopicName: {}", meta.getDef_meta().getTopicName());
            logger.info("Creation Time: {}", meta.getDef_meta().getCreationTime());

            Iterator<String> tagIter = meta.getSnsr_tag().getTags().iterator();
            while(tagIter.hasNext()) {
                String iterTag = tagIter.next();
                logger.info("Tag: {}", iterTag);
            }

            Map<String,String> optionalMetadataMap = meta.getOpt_meta().getElmts();
            Iterator<String> optIter = optionalMetadataMap.keySet().iterator();
            while(optIter.hasNext()) {
                String optName = optIter.next();
                String optVal = optionalMetadataMap.get(optName);
                logger.info("Opt Name: {}, Opt Val: {}", optName, optVal);
            }

        }

    }


    @After
    public void tearDown() {
        controller.deleteAllSensorMetadata();
        controller.eraseAllSensorNetworkMetadata();
        controller = null;
    }
}
