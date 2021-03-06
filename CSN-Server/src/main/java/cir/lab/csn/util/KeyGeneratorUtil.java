package cir.lab.csn.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by nfm on 2014. 5. 22..
 * http://www.javapractices.com/topic/TopicAction.do?Id=56
 */
public class KeyGeneratorUtil {
    static Logger logger = LoggerFactory.getLogger(KeyGeneratorUtil.class);

    public static String getKey() {
        String newKey = null;
        try {
            //Initialize SecureRandom
            //This is a lengthy operation, to be done only upon
            //initialization of the application
            SecureRandom prng = SecureRandom.getInstance("SHA1PRNG");

            //generate a random number
            String randomNum = Integer.toString(prng.nextInt());

            //get its digest
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            byte[] result =  sha.digest(randomNum.getBytes());
            newKey = hexEncode(result);
            logger.info("Created New Key: {}", newKey);
        }
        catch (NoSuchAlgorithmException ex) {
           logger.error(ex.toString());
        }

        return newKey;
    }

    /**
     * The byte[] returned by MessageDigest does not have a nice
     * textual representation, so some form of encoding is usually performed.
     *
     * This implementation follows the example of David Flanagan's book
     * "Java In A Nutshell", and converts a byte array into a String
     * of hex characters.
     *
     * Another popular alternative is to use a "Base64" encoding.
     */
    static private String hexEncode(byte[] aInput){
        StringBuilder result = new StringBuilder();
        char[] digits = {'0', '1', '2', '3', '4','5','6','7','8','9','a','b','c','d','e','f'};
        for (int idx = 0; idx < aInput.length; ++idx) {
            byte b = aInput[idx];
            result.append(digits[ (b&0xf0) >> 4 ]);
            result.append(digits[ b&0x0f]);
        }
        return result.toString();
    }
}
