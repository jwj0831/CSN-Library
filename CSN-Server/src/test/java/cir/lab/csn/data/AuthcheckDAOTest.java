package cir.lab.csn.data;

import cir.lab.csn.client.SensorNetworkManager;
import cir.lab.csn.component.SensorNetworkManagerImpl;
import cir.lab.csn.data.dao.AuthCheckDAO;
import cir.lab.csn.data.db.CSNDAOFactory;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by nfm on 2014. 6. 2..
 */
public class AuthCheckDAOTest {
//    Logger logger = LoggerFactory.getLogger(SensorMetadataDAOTest.class);
//    private SensorNetworkManager controller;
//    AuthCheckDAO dao = new CSNDAOFactory().authCheckDAO();
//
//    private String testKey1;
//    private String testKey2;
//    private String testKey3;
//
//    //Test Name Set
//    final static String name1 = "N1";
//    final static String name2 = "N2";
//    final static String name3 = "N3";
//
//    //Test Measurement Set
//    final static String measurement1 = "Weather";
//    final static String measurement2 = "Temperature";
//    final static String measurement3 = "PM10";
//
//    //Test Key
//    final static String key1 = "2d207a02df6601572b6542f00bc90f3f9becf358";
//    final static String key2 = "fbbe099f4bb9308383d15ba3ec9573832db7346a";
//    final static String key3 = "071124d2bfa1ab3cacc3a16e1400a9978335706b";
//
//    @Before
//    public void setUp() {
//        logger.info("Set Up Phase");
//        controller = new SensorNetworkManagerImpl();
//        controller.deleteAllSensorMetadata();
//        controller.deleteAllSensorNetworkMetadata();
//
//        String id1 = controller.createSensorMetadata(name1, measurement1);
//        String id2 = controller.createSensorMetadata(name2, measurement2);
//        String id3 = controller.createSensorMetadata(name3, measurement3);
//        testKey1 = dao.getSensorAuthKey(id1);
//        testKey2 = dao.getSensorAuthKey(id2);
//        testKey3 = dao.getSensorAuthKey(id3);
//    }
//
//    @Test
//    public void sensorKeyTest() {
//        assertTrue(dao.isItSensorKey(testKey1));
//        assertTrue(dao.isItSensorKey(testKey2));
//        assertTrue(dao.isItSensorKey(testKey3));
//        assertFalse(dao.isItSensorKey(key1));
//        assertFalse(dao.isItSensorKey(key2));
//        assertFalse(dao.isItSensorKey(key3));
//    }
//
//    @Test
//    public void testCheckHasKey() {
//        assertTrue(dao.checkHasKey(testKey1));
//        assertTrue(dao.checkHasKey(testKey2));
//        assertTrue(dao.checkHasKey(testKey3));
//    }
//
//    @After
//    public void tearDown() {
//        logger.info("Tear Down Phase");
//        controller.deleteAllSensorMetadata();
//        controller.eraseAllSensorNetworkMetadata();
//        controller = null;
//    }
}
