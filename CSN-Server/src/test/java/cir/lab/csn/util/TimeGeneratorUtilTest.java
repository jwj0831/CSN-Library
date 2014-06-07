package cir.lab.csn.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by nfm on 2014. 5. 22..
 */
public class TimeGeneratorUtilTest {

//    @Test
//    public void epochTimeConvertTest() {
//        long epoch = TimeGeneratorUtil.getCurrentEpochTime();
//
//        String date = TimeGeneratorUtil.convertEpochToDate(epoch);
//        long convertEpoch = TimeGeneratorUtil.convertDateToEpoch(date);
//        System.out.println("Epoch Time: " + epoch);
//        System.out.println("Converted Epoch Time: " + convertEpoch);
//        assertEquals(epoch, convertEpoch);
//    }

    @Test
    public void  dateTimeConvertTest() {
        String date = TimeGeneratorUtil.getCurrentTimestamp();

        long epoch = TimeGeneratorUtil.convertDateToEpoch(date);
        String convertDate = TimeGeneratorUtil.convertEpochToDate(epoch);

        System.out.println("Date Time: " + date);
        System.out.println("Converted Date Time: " + convertDate);
        assertEquals(date, convertDate);
    }
}
