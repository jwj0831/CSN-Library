package cir.lab.csn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IDGeneratorUtil {
    static Logger logger = LoggerFactory.getLogger(IDGeneratorUtil.class);

    public static String getSensorID(String name, String epoch) {
        String id = name + "-" + epoch;
        logger.info("Created ID: {}", id );
        return id;
    }

    public static String getSensorNetworkID(String name, String epoch) {
        String id = name + "-" + epoch;
        logger.info("Created ID: {}", id );
        return id;
    }
}
