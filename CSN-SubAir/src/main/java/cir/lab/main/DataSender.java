package cir.lab.main;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import cir.lab.dao.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.eclipse.paho.client.mqttv3.*;

public class DataSender implements MqttCallback {
    MqttClient myClient;
    MqttConnectOptions connOpt;
    private ObjectMapper jsonMapper;

    static final String BROKER_URL = "tcp://117.16.146.55:1883";
    //private static int MAX_EVENT_NUM = 50;
    static final String TOPIC_NAME = "CSN/CENTRAL/DATA";

    private int startID = 105697;
    //private int endID = 105710;
    private int endID = 244831;

    public static void main(String args[]) throws ClassNotFoundException, SQLException {
        DataSender sender = new DataSender();

        try {
            sender.publishSubAirData();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void publishSubAirData() throws ClassNotFoundException, SQLException, JsonProcessingException {
        SubAirDataDAO dao = new DAOFactory().subAirDataDAO();
//        String beforeSNSRDate = "";
//        String currentSNSRDate = "";
        int waitTime = 0;

        connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(true);
        connOpt.setKeepAliveInterval(30);

        // Connect to Broker
        try {
            myClient = new MqttClient( BROKER_URL, "SubAir");
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        for(int i=startID; i<=endID; i++){
            SubAirData subAirData = dao.get(i);
//            currentSNSRDate = subAirData.getInput_date();

//            if(i != 1)
//                waitTime = getDelayTime(currentSNSRDate, beforeSNSRDate);
            waitTime = 1;
            if(waitTime > 0) {
                System.out.println("Wait for " + waitTime + " Sec...");
                try {Thread.sleep(waitTime * 500);} catch (InterruptedException e) { }
            }

            Calendar calendar = Calendar.getInstance();
            java.util.Date date = calendar.getTime();
            String today = (new SimpleDateFormat("yyyyMMddHHmmss").format(date));
            subAirData.setTime(today);



            subAirData.setId(convertCSNID(subAirData.getId()));
            jsonMapper = new ObjectMapper();
            String jsonStr = jsonMapper.writeValueAsString(subAirData);
            System.out.println("ID: "+i);
            System.out.println("Pub MSG: " + jsonStr);

            int pubQoS = 0;
            MqttMessage message = new MqttMessage(jsonStr.getBytes());
            message.setQos(pubQoS);
            message.setRetained(false);

            MqttDeliveryToken token = null;
            try {
                MqttTopic mqttTopic = myClient.getTopic(TOPIC_NAME);
                // publish message to broker
                token = mqttTopic.publish(message);
                // Wait until the message has been delivered to the broker
                token.waitForCompletion();
            } catch (Exception e) {
                e.printStackTrace();
            }



//            beforeSNSRDate = currentSNSRDate;
        }

        // disconnect
        try {
            myClient.disconnect();
            System.out.println("Close Connection");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static String convertCSNID(String id) {
        if (id.equals("2000214"))
            return "2000214-1409407609684";
        else if (id.equals("2000215"))
            return "2000215-1409407627706";
        else if (id.equals("2000216"))
            return "2000216-1409407631080";
        else if (id.equals("2000217"))
            return "2000217-1409407634669";
        else if (id.equals("2000218"))
            return "2000218-1409407638504";
        else if (id.equals("2000300"))
            return "2000300-1409407773911";
        else if (id.equals("2000301"))
            return "2000301-1409407782400";
        else if (id.equals("2000302"))
            return "2000302-1409407787380";
        else if (id.equals("2000303"))
            return "2000303-1409407791472";
        else if (id.equals("2000304"))
            return "2000304-1409407795675";
        else if (id.equals("2000305"))
            return "2000305-1409407802204";
        else if (id.equals("2000306"))
            return "2000306-1409407810288";
        else if (id.equals("2000610"))
            return "2000610-1409407831256";
        else if (id.equals("2000611"))
            return "2000611-1409407837763";
        else if (id.equals("2000612"))
            return "2000612-1409407843948";
        else if (id.equals("2000700"))
            return "2000700-1409408247379";
        else if (id.equals("2000701"))
            return "2000701-1409408259248";
        else if (id.equals("2000702"))
            return "2000702-1409408298246";
        else if (id.equals("2000703"))
            return "2000703-1409408683358";
        else if (id.equals("2000704"))
            return "2000704-1409408690767";
        else if (id.equals("2000801"))
            return "2000801-1409408713664";
        else if (id.equals("2000802"))
            return "2000802-1409408747194";
        else if (id.equals("2000811"))
            return "2000811-1409408775675";
        else if (id.equals("2000812"))
            return "2000812-1409408787109";
        else if (id.equals("2000813"))
            return "2000813-1409408801603";
        else if (id.equals("2000901"))
            return "2000901-1409408824476";
        else if (id.equals("2000902"))
            return "2000902-1409408848857";
        else if (id.equals("2000911"))
            return "2000911-1409408866652";
        else if (id.equals("2000912"))
            return "2000912-1409408883728";
        else if (id.equals("2000913"))
            return "2000913-1409408905527";
        else if (id.equals("2001001"))
            return "2001001-1409408933580";
        else if (id.equals("2001002"))
            return "2001002-1409408941823";
        else if (id.equals("2001011"))
            return "2001011-1409408956625";
        else if (id.equals("2001012"))
            return "2001012-1409408978783";
        else if (id.equals("2001013"))
            return "2001013-1409408991204";
        else if (id.equals("2030401"))
            return "2030401-1409409011115";
        else if (id.equals("2030402"))
            return "2030402-1409409061129";
        else if (id.equals("2030411"))
            return "2030411-1409409081268";
        else if (id.equals("2030412"))
            return "2030412-1409409098134";
        else if (id.equals("2030413"))
            return "2030413-1409409114401";
        else if (id.equals("2030501"))
            return "2030501-1409409142631";
        else if (id.equals("2030601"))
            return "2030601-1409409249213";
        else if (id.equals("2030602"))
            return "2030602-1409409258231";
        else if (id.equals("2030611"))
            return "2030611-1409409281532";
        else if (id.equals("2030612"))
            return "2030612-1409409291070";
        else if (id.equals("2030613"))
            return "2030613-1409409315676";
        else if (id.equals("2030701"))
            return "2030701-1409409343918";
        else if (id.equals("2110501"))
            return "2110501-1409409372267";
        else if (id.equals("2110502"))
            return "2110502-1409409389715";
        else if (id.equals("2110511"))
            return "2110511-1409409418183";
        else if (id.equals("2110512"))
            return "2110512-1409409433122";
        else if (id.equals("2110513"))
            return "2110513-1409409440608";
        else if (id.equals("2110601"))
            return "2110601-1409409455147";
        else if (id.equals("2120100"))
            return "2120100-1409409520279";
        else if (id.equals("2190501"))
            return "2190501-1409409534891";
        else if (id.equals("2190502"))
            return "2190502-1409409544730";
        else if (id.equals("2190511"))
            return "2190511-1409409557645";
        else if (id.equals("2190512"))
            return "2190512-1409409566165";
        else if (id.equals("2190513"))
            return "2190513-1409409573668";
        else if (id.equals("2190601"))
            return "2190601-1409409597737";
        else if (id.equals("2200501"))
            return "2200501-1409409613788";
        else if (id.equals("2200502"))
            return "2200502-1409409625423";
        else if (id.equals("2200511"))
            return "2200511-1409409642722";
        else if (id.equals("2200512"))
            return "2200512-1409409651958";
        else if (id.equals("2200513"))
            return "2200513-1409409675028";
        else if (id.equals("2200601"))
            return "2200601-1409409689739";
        else
            return "Test";
    }

    public static int getDelayTime(String currentSNSRDate, String beforeSNSRDate) {
        int currentDateHour = Integer.parseInt(currentSNSRDate.substring(8, 10));
        int currentDateMin = Integer.parseInt(currentSNSRDate.substring(10, 12));
        int currentDateSec = Integer.parseInt(currentSNSRDate.substring(12, 14));
        int beforeDateHour = Integer.parseInt(beforeSNSRDate.substring(8, 10));
        int beforeDateMin = Integer.parseInt(beforeSNSRDate.substring(10, 12));
        int beforeDateSec = Integer.parseInt(beforeSNSRDate.substring(12, 14));

        if(currentDateSec > beforeDateSec){
            return currentDateSec - beforeDateSec;
        }
        else{
            int minDiff = currentDateMin - beforeDateMin;

            if(minDiff < 0){
                int hourDiff = currentDateHour - beforeDateHour;
                if(hourDiff < 0){
                    return -1;
                }
                else if(hourDiff >= 1){
                    return (hourDiff-1)*3600 + ((currentDateHour-1) + (60-beforeDateMin))*60 + currentDateSec + (60-beforeDateSec);
                }
            }
            else if(minDiff >= 1) {
                return (minDiff-1)*60 + currentDateSec + (60-beforeDateSec);
            }
            else {
                return -1;
            }
        }
        return -1;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
