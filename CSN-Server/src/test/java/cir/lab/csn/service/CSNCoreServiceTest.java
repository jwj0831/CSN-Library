//package cir.lab.csn.client;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import java.sql.Time;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertNotNull;
//
///**
//* Created by nfm on 2014. 5. 20..
//*/
//public class CSNCoreServiceTest {
//    private int actualIntRetVal;
//    private int expectedIntVal;
//    //private String expectedStrVal;
//    //private String acturalStrRetVal;
//    private CSNCoreService service;
//
//    @Before
//    public void setUp() {
//        service = new CSNCoreServiceImpl("Test", null);
//    }
//
//
//    @Test
//    public void CSNCoreServiceStartTest() {
//
//        expectedIntVal = 0;
//        actualIntRetVal = service.initCSN();
//        assertEquals(expectedIntVal, actualIntRetVal);
//
//        expectedIntVal = 0;
//        actualIntRetVal = service.startSystem();
//        assertEquals(expectedIntVal, actualIntRetVal);
//
//        expectedIntVal = 0;
//        actualIntRetVal = service.stopSystem();
//        assertEquals(expectedIntVal, actualIntRetVal);
//    }
//
//    @Test
//    public void CSNCoreServiceRestartTest() {
//
//        expectedIntVal = 0;
//        actualIntRetVal = service.initCSN();
//        assertEquals(expectedIntVal, actualIntRetVal);
//
//        expectedIntVal = 0;
//        actualIntRetVal = service.startSystem();
//        assertEquals(expectedIntVal, actualIntRetVal);
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        expectedIntVal = 0;
//        actualIntRetVal = service.restartSystem();
//        assertEquals(expectedIntVal, actualIntRetVal);
//
//        expectedIntVal = 0;
//        actualIntRetVal = service.stopSystem();
//        assertEquals(expectedIntVal, actualIntRetVal);
//    }
//
//    @Test
//    public void SubModuleCreationTest() {
////        service.initCSN();
////        assertNotNull(service.getCSNMonitor());
////        assertNotNull(service.getMetadataController());
////        assertNotNull(service.getBrokerManager());
////        assertNotNull(service.getSensorNetworkManager());
//    }
//
//    @After
//    public void tearDown() {
//        service = null;
//    }
//}
