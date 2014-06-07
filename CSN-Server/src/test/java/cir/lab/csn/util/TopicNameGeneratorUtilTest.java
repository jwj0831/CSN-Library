package cir.lab.csn.util;


import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import static org.junit.Assert.assertEquals;

/**
 * Created by nfm on 2014. 5. 24..
 */
public class TopicNameGeneratorUtilTest {
    static Logger logger = LoggerFactory.getLogger(TopicNameGeneratorUtilTest.class);

    @Test
    public void getSingleTopicNameTest() {
        String name = "Test";
        String creationTime = TimeGeneratorUtil.getCurrentTimestamp();
        long epoch = TimeGeneratorUtil.convertDateToEpoch(creationTime);
        String expectedTopicName = "CSN.SINGLE." + name + "." + epoch;

        String actualTopicName = TopicNameGeneratorUtil.getSingleSensorNetworkTopicName(name, epoch);

        logger.info("Expected Topic Name: {}", expectedTopicName);
        logger.info("Actual Topic Name: {}", actualTopicName);

        assertEquals(expectedTopicName, actualTopicName);
    }


}
