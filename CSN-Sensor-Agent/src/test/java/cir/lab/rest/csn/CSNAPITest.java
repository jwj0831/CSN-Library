package cir.lab.rest.csn;//package cir.lab.rest.csn;
//
//import cir.lab.rest.client.CSNAPI;
////import org.junit.After;
////import org.junit.Before;
//import cir.lab.rest.client.RestAPIFactory;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
////import static org.junit.Assert.assertEquals;
//
//public class CSNAPITest {
//    private final Logger logger = LoggerFactory.getLogger(this.getClass());
//    private CSNAPI csnAPI = new RestAPIFactory().csnAPI();
//
//    @Test
//    public void testPostCSNConfiguration(){
//        String input = "{\"config\":{\"name\":\"CSN-REST-Test\",\"adminName\":\"NFM\", \"adminEmail\":\"jwj0831@gmail.com\"}}";
//        String output = csnAPI.setCSNConfiguration(input);
//        logger.info(output);
//    }
//
//    @Test
//    public void testGetCSNConfiguration(){
//        String output = csnAPI.getCSNConfiguration();
//        logger.info(output);
//    }
//
//    @Test
//    public void testDeleteCSNConfiguration(){
//        String output = csnAPI.deleteCSNConfiguration();
//        logger.info(output);
//    }
//
//    @Test
//    public void testCSNLifeCycle(){
//        String input = "{\"config\":{\"name\":\"CSN-REST-Test\",\"adminName\":\"NFM\", \"adminEmail\":\"jwj0831@gmail.com\"}}";
//        String output = csnAPI.startSystem(input);
//        logger.info(output);
//        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
//
//        output = csnAPI.restartSystem(input);
//        logger.info(output);
//
//        try { Thread.sleep(1000); } catch (InterruptedException e) { e.printStackTrace(); }
//        output = csnAPI.stopSystem("");
//        logger.info(output);
//    }
//}
