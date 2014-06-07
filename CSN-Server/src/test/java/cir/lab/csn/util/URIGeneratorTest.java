package cir.lab.csn.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


/**
 * Created by nfm on 2014. 5. 22..
 */
public class URIGeneratorTest {

    @Test
    public void getSensorIDTest() {
        String name = "Test";
        String creation_time = "1234";
        String expectedID = name + "-" + creation_time;
        String actualID = IDGeneratorUtil.getSensorID(name, creation_time);
        assertEquals(expectedID, actualID);
    }

    @Test
    public void getSensorNetworkIDTest() {
        String name = "WhetherNetwork";
        String creation_time = "1234";
        String expectedID = name + "-" + creation_time;
        String actualID = IDGeneratorUtil.getSensorNetworkID(name, creation_time);
        assertEquals(expectedID, actualID);
    }
}
