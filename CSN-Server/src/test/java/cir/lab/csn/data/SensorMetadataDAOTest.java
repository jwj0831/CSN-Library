package cir.lab.csn.data;

import cir.lab.csn.client.SensorNetworkManager;
import cir.lab.csn.component.SensorNetworkManagerImpl;
import cir.lab.csn.metadata.sensor.DefaultMetadata;
import cir.lab.csn.metadata.sensor.SensorMetadata;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.junit.Assert.*;

public class SensorMetadataDAOTest {
    Logger logger = LoggerFactory.getLogger(this.getClass());
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

    //Test Key
//    final static String key1 = "2d207a02df6601572b6542f00bc90f3f9becf358";
//    final static String key2 = "fbbe099f4bb9308383d15ba3ec9573832db7346a";
//    final static String key3 = "071124d2bfa1ab3cacc3a16e1400a9978335706b";
//    final static String key4 = "edc405c91717db8943b0fe6c41be8e2e47f2d3bd";
//    final static String key5 = "56e17c6faa0520a892ed9fbfbacf11285d1112ea";
//    final static String key6 = "77f5737369aae5cfa6f93fdf9599215b596586e6";

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
        logger.info("Set Up Phase");
        controller = new SensorNetworkManagerImpl();
        controller.deleteAllSensorMetadata();
        controller.deleteAllSensorNetworkMetadata();
    }

    @Test
    public void defaultSensorMetadataTest() {
        String id = controller.createSensorMetadata(name1, measurement1);
        //String key = controller.getSensorKey(id);

        assertNotNull(id);
        logger.info("Sensor ID: {}", id);
        //logger.info("Sensor Key: {}", key);
        DefaultMetadata defaultMetadata = controller.getDefaultSensorMetadata(id);

        assertEquals(id, defaultMetadata.getId());
        assertEquals(name1, defaultMetadata.getName());
        assertEquals(measurement1, defaultMetadata.getMeasure());

        DAOReturnType retType = controller.removeSensorMetadata(id);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        boolean retVal = controller.isItSensorMetadata(id);
        assertFalse(retVal);
    }


    @Test
    public void getAllSensorIDTest() {
        List<String> idList = new ArrayList<String>();
        for(int i = 1; i <= 10; i++ ) {
            String name = "TestName" + i;
            String measurement = "Measurement" + i;
            String tempID = controller.createSensorMetadata(name, measurement);
            idList.add(tempID);
        }
        Set<String> idSet = controller.getAllSensorID();
        assertNotNull(idSet);

        for(String tempID : idList) {
            assertTrue(idSet.contains(tempID));
            idSet.remove(tempID);
            assertFalse(idSet.contains(tempID));
        }
    }

    @Test
    public void optionalSensorMetadataTest() {
        DAOReturnType retType;
        String id = controller.createSensorMetadata(name1, measurement1);
        retType = controller.addOptionalSensorMetadata(id, optName1, optVal1);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        logger.info("ID: {}\tOption Name: {}", id, optName1);

        String actual_optVal = controller.getOptionalSensorMetadataValue(id, optName1);
        assertEquals(optVal1, actual_optVal);

        Set<String> keySet = controller.getOptionalSensorMetadataKeySet(id);
        assertNotNull(keySet);
        for(String optName : keySet)
            logger.info("Option Name: {}", optName);

        assertTrue(keySet.contains(optName1));
        assertFalse(keySet.contains(optName2));

        Map<String, String> metadataMap = controller.getAllOptionalSensorMetadata(id);
        assertNotNull(metadataMap);

        retType = controller.removeOptionalSensorMetadataValue(id, optName1);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        boolean retVal = controller.isItOptionalSensorMetadata(id, optName1);
        assertFalse(retVal);
    }

    @Test
    public void optionalSensorMetadataMapTest() {
        DAOReturnType retType;
        String id = controller.createSensorMetadata(name1, measurement1);
        Map<String, String> metadataMap = new HashMap<String, String>();
        metadataMap.put(optName1, optVal1);
        metadataMap.put(optName2, optVal2);
        metadataMap.put(optName3, optVal3);
        metadataMap.put(optName4, optVal4);
        metadataMap.put(optName5, optVal5);
        metadataMap.put(optName6, optVal6);


        retType = controller.addOptionalSensorMetadata(id, metadataMap);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        Map<String, String> actualMetadataMap = controller.getAllOptionalSensorMetadata(id);
        assertNotNull(actualMetadataMap);

        Set<String> keySet = controller.getOptionalSensorMetadataKeySet(id);
        assertNotNull(keySet);
        for(String optName : keySet) {
            logger.info("Option Name: {}", optName);
            logger.info("Expected optName val: {}\tActual optName val: {}", metadataMap.get(optName), actualMetadataMap.get(optName));
            assertEquals(metadataMap.get(optName), actualMetadataMap.get(optName));
        }
    }

    @Test
    public void updateOptionalSensorMetadataValueTest() {
        DAOReturnType retType;
        String id = controller.createSensorMetadata(name1, measurement1);

        retType = controller.addOptionalSensorMetadata(id, optName1, optVal1);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        logger.info("ID: {}\tOption Name: {}", id, optName1);

        retType = controller.updateOptionalSensorMetadataValue(id, optName1, optVal2);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        String actualNewVal = controller.getOptionalSensorMetadataValue(id, optName1);
        assertEquals(optVal2, actualNewVal);

        retType = controller.removeOptionalSensorMetadataValue(id, optName1);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        boolean retVal = controller.isItOptionalSensorMetadata(id, optName1);
        assertFalse(retVal);
    }


    @Test
    public void updateOptionalSensorMetadataTest() {
        DAOReturnType retType;
        String id = controller.createSensorMetadata(name1, measurement1);

        retType = controller.addOptionalSensorMetadata(id, optName1, optVal1);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        logger.info("ID: {}\tOption Name: {}", id, optName1);

        retType = controller.addOptionalSensorMetadata(id, optName2, optVal2);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        logger.info("ID: {}\tOption Name: {}", id, optName2);

        retType = controller.addOptionalSensorMetadata(id, optName3, optVal3);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        logger.info("ID: {}\tOption Name: {}", id, optName3);

        Map<String, String> map = new HashMap<String, String>();
        map.put(optName1, optVal4);
        map.put(optName2, optVal5);
        map.put(optName3, optVal6);

        retType = controller.updateOptionalSensorMetadata(id, map);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        String actualNewVal = controller.getOptionalSensorMetadataValue(id, optName1);
        assertEquals(optVal4, actualNewVal);

        actualNewVal = controller.getOptionalSensorMetadataValue(id, optName2);
        assertEquals(optVal5, actualNewVal);

        actualNewVal = controller.getOptionalSensorMetadataValue(id, optName3);
        assertEquals(optVal6, actualNewVal);

        retType = controller.removeOptionalSensorMetadataValue(id, optName1);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        boolean retVal = controller.isItOptionalSensorMetadata(id, optName1);
        assertFalse(retVal);
    }

    @Test
    public void sensorTagTest() {
        DAOReturnType retType;
        String id = controller.createSensorMetadata(name1, measurement1);
        Set<String> tagSet;

        retType = controller.addTagToSensor(id, tag1);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        tagSet = controller.getAllTagBySensorID(id);
        assertNotNull(tagSet);
        assertTrue(tagSet.contains(tag1));
        assertFalse(tagSet.contains(tag2));

        retType = controller.updateTagInSensor(id, tag1, tag2);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        tagSet = controller.getAllTagBySensorID(id);
        assertNotNull(tagSet);
        assertTrue(tagSet.contains(tag2));
        assertFalse(tagSet.contains(tag1));

        retType = controller.removeTagInSensor(id, tag2);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        tagSet = controller.getAllTagBySensorID(id);
        assertFalse(tagSet.contains(tag2));
    }

    @Test
    public void getAllSensorIDbyTagTest() {
        DAOReturnType retType;
        String id1 = controller.createSensorMetadata(name1, measurement1);
        String id2 = controller.createSensorMetadata(name2, measurement1);
        String id3 = controller.createSensorMetadata(name3, measurement1);

        retType = controller.addTagToSensor(id1, tag1);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        retType = controller.addTagToSensor(id2, tag1);
        assertEquals(DAOReturnType.RETURN_OK, retType);
        retType = controller.addTagToSensor(id3, tag1);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        Set<String> idSet = controller.getAllSensorIDByTag(tag1);
        assertNotNull(idSet);
        for(String id : idSet)
            logger.info("ID by tag {} : {}", tag1, id);
    }

    @Test
    public void addTagSetToSensorTest() {
        DAOReturnType retType;
        String id = controller.createSensorMetadata(name1, measurement1);
        Set<String> tagSet = new HashSet<String>();
        Set<String> actualTagSet;
        tagSet.add(tag1);
        tagSet.add(tag2);
        tagSet.add(tag3);

        retType = controller.addTagSetToSensor(id, tagSet);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        actualTagSet = controller.getAllTagBySensorID(id);
        assertNotNull(actualTagSet);
        assertTrue(actualTagSet.contains(tag1));
        assertTrue(actualTagSet.contains(tag2));
        assertTrue(actualTagSet.contains(tag3));

        retType = controller.removeTagInSensor(id, tag1);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        actualTagSet = controller.getAllTagBySensorID(id);
        assertNotNull(actualTagSet);
        assertFalse(actualTagSet.contains(tag1));
        assertTrue(actualTagSet.contains(tag2));
        assertTrue(actualTagSet.contains(tag3));

        retType = controller.removeTagInSensor(id, tag2);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        actualTagSet = controller.getAllTagBySensorID(id);
        assertNotNull(actualTagSet);
        assertFalse(actualTagSet.contains(tag1));
        assertFalse(actualTagSet.contains(tag2));
        assertTrue(actualTagSet.contains(tag3));

        retType = controller.removeTagInSensor(id, tag3);
        assertEquals(DAOReturnType.RETURN_OK, retType);

        actualTagSet = controller.getAllTagBySensorID(id);
        assertNotNull(actualTagSet);
        assertFalse(actualTagSet.contains(tag1));
        assertFalse(actualTagSet.contains(tag2));
        assertFalse(actualTagSet.contains(tag3));
    }

    @Test
    public void getSensorMetadataTest() {
        String id = controller.createSensorMetadata(name1, measurement1);
        Map<String, String> metadataMap = new HashMap<String, String>();
        metadataMap.put(optName1, optVal1);
        metadataMap.put(optName2, optVal2);
        metadataMap.put(optName3, optVal3);
        metadataMap.put(optName4, optVal4);
        metadataMap.put(optName5, optVal5);
        metadataMap.put(optName6, optVal6);
        controller.addOptionalSensorMetadata(id, metadataMap);

        Set<String> tagSet = new HashSet<String>();
        tagSet.add(tag1);
        tagSet.add(tag2);
        tagSet.add(tag3);
        tagSet.add(tag4);
        tagSet.add(tag5);
        tagSet.add(tag6);
        tagSet.add(tag7);
        tagSet.add(tag8);

        controller.addTagSetToSensor(id, tagSet);

        SensorMetadata meta = controller.getSensorMetadata(id);
        assertNotNull(meta);
        logger.info("ID: {}", meta.getDefMeta().getId());
        logger.info("Name: {}", meta.getDefMeta().getName());
        logger.info("Creation Time: {}", meta.getDefMeta().getReg_time());

        tagSet = meta.getSnsrTags();
        for(String iterTag : tagSet) {
            logger.info("Tag: {}", iterTag);
            assertTrue(tagSet.contains(iterTag));
        }

        Map<String,String> optionalMetadataMap = meta.getOptMeta();
        Set<String> keySet = optionalMetadataMap.keySet();
        for(String optName : keySet) {
            String optVal = optionalMetadataMap.get(optName);
            logger.info("Opt Name: {}, Opt Val: {}", optName, optVal);
            assertEquals(metadataMap.get(optName), optVal);
        }
    }

    @Test
    public void getAllSensorMetadataTest() {
        String id1 = controller.createSensorMetadata(name1, measurement1);
        String id2 = controller.createSensorMetadata(name2, measurement1);
        String id3 = controller.createSensorMetadata(name3, measurement2);
        String id4 = controller.createSensorMetadata(name4, measurement2);
        String id5 = controller.createSensorMetadata(name5, measurement3);
        String id6 = controller.createSensorMetadata(name6, measurement3);

        Map<String, String> metadataMap = new HashMap<String, String>();
        metadataMap.put(optName1, optVal1);
        metadataMap.put(optName2, optVal2);
        controller.addOptionalSensorMetadata(id1, metadataMap);
        controller.addOptionalSensorMetadata(id2, metadataMap);
        metadataMap.put(optName3, optVal3);
        metadataMap.put(optName4, optVal4);
        controller.addOptionalSensorMetadata(id3, metadataMap);
        controller.addOptionalSensorMetadata(id4, metadataMap);
        metadataMap.put(optName5, optVal5);
        metadataMap.put(optName6, optVal6);
        controller.addOptionalSensorMetadata(id5, metadataMap);
        controller.addOptionalSensorMetadata(id6, metadataMap);


        Set<String> tagSet = new HashSet<String>();
        tagSet.add(tag1);
        tagSet.add(tag2);
        controller.addTagSetToSensor(id1, tagSet);
        tagSet.add(tag3);
        tagSet.add(tag4);
        controller.addTagSetToSensor(id3, tagSet);
        controller.addTagSetToSensor(id4, tagSet);
        tagSet.add(tag5);
        tagSet.add(tag6);
        controller.addTagSetToSensor(id5, tagSet);
        controller.addTagSetToSensor(id6, tagSet);

        List<SensorMetadata> list = controller.getAllSensorMetadata();
        assertNotNull(list);
        for(SensorMetadata meta : list ) {
            logger.info("ID: {}", meta.getDefMeta().getId());
            logger.info("Name: {}", meta.getDefMeta().getName());
            logger.info("Creation Time: {}", meta.getDefMeta().getReg_time());

            tagSet = meta.getSnsrTags();
            for(String iterTag : tagSet)
                logger.info("Tag: {}", iterTag);

            Map<String,String> optionalMetadataMap = meta.getOptMeta();
            Set<String> optNameSet = optionalMetadataMap.keySet();
            for(String optName : optNameSet) {
                String optVal = optionalMetadataMap.get(optName);
                logger.info("Opt Name: {}, Opt Val: {}", optName, optVal);
            }

        }
    }

    @After
    public void tearDown() {
        logger.info("Tear Down Phase");
        controller.deleteAllSensorMetadata();
        controller.eraseAllSensorNetworkMetadata();
        controller = null;
    }
}
