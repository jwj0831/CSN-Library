package cir.lab.csn.util;

import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

/**
 * Created by nfm on 2014. 5. 22..
 */
public class KeyGeneratorUtilTest {

    @Test
    public void getKeyTest() {
        String key1 = KeyGeneratorUtil.getKey();
        String key2 = KeyGeneratorUtil.getKey();

        assertNotEquals(key1, key2);
    }
}
