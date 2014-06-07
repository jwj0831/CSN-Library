package cir.lab.csn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by nfm on 2014. 5. 24..
 */
public class TopicNameGeneratorUtil {
    static Logger logger = LoggerFactory.getLogger(TopicNameGeneratorUtil.class);

    private static final String CSN_PREFIX = "CSN";
    private static final String CENTRAL_MODE_TOPIC_NAME = "CSN.CENTRAL.DATA";
    private static final String MULTI_NETWORK = "MULTI";
    private static final String SINGLE_NETWORK = "SINGLE";

    public static String getCentralModeTopicName() {
        return CENTRAL_MODE_TOPIC_NAME;
    }

    public static String getSingleSensorNetworkTopicName(String name, long epoch) {
        return CSN_PREFIX + "." + SINGLE_NETWORK + "." + name + "." + epoch;
    }

    public static String getMultiSensorNetworkTopicName(String name, long epoch) {
        return CSN_PREFIX + "." + MULTI_NETWORK + "." + name + "." + epoch;
    }
}
