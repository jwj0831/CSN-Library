package cir.lab.csn.metadata;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static org.junit.Assert.assertEquals;

public class JacksonJSONTest {
    private ObjectMapper jsonMapper;

    @Before
    public void setUp() {
        jsonMapper = new ObjectMapper();
    }

    @Test
    public void parseJSONTest() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        String today = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
        Random oRandom = new Random();
        int randNum = oRandom.nextInt(999) + 1;

        SensorData sensorData = new SensorData("Test", today, Integer.toString(randNum));
        String jsonStr = null;
        try {
            jsonStr = jsonMapper.writeValueAsString(sensorData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        System.out.println(jsonStr);

        SensorData parsedData = null;
        try {
            parsedData = jsonMapper.readValue(jsonStr, SensorData.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        assertEquals(sensorData.getUri(), parsedData.getUri());

    }

    @After
    public void tearDown() {
        jsonMapper = null;
    }

}
