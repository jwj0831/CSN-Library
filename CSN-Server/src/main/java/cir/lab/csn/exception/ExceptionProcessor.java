package cir.lab.csn.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.SQLException;

/**
 * Created by nfm on 2014. 6. 2..
 */
public class ExceptionProcessor {
    static Logger logger = LoggerFactory.getLogger(ExceptionProcessor.class);

    public static void handleException(Exception e, String className) {
        logger.error("Exception Location: {}", className);
        if( e instanceof ClassNotFoundException ) {
            logger.error("Exception Type: ClassNotFoundException");
        }
        else if( e instanceof SQLException) {
            logger.error("Exception Type: SQLException");
        }
        else {
        }
        logger.error(e.toString());
    }
}
