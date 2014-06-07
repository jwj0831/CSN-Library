//package cir.lab.csn;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.eclipse.paho.client.mqttv3.*;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.text.SimpleDateFormat;
//import java.util.*;
//
///**
// * Created by nfm on 2014. 5. 29..
// */
//public class Connection implements MqttCallback  {
//    /*
//    사전에 가지고 있는 정보: 센서 URI, 센서 키, CSN 호스트 주소
//    필요정보: 싱글 센서네트워크 토픽주소
//    중앙 모드
//        바로 데이터 전송
//    분산 모드
//        자신의 소속 센서네트워크 리스트 업데이트 확인 요청
//        데이터 변경 시 요청
//        리스트 업데이트
//        데이터 전송
//     */
//
//    Logger logger = LoggerFactory.getLogger(Connection.class);
//
//    MqttClient myClient;
//    MqttConnectOptions connOpt;
//    private ObjectMapper jsonMapper;
//
//    private static final String TEMP_SNSR_URI = "/N4/1401343323346";
//    private static final String TEMP_SNSR_KEY = "";
//    private static final String TEMP_CSN_HOST_ADDR = "localhost";
//    private static final int PORT = 1883;
//    private static final String TEMP_CSN_URL = "tcp://" + TEMP_CSN_HOST_ADDR + ":" + PORT;
//    private static final String TEMP_SINGLE_TOPIC = "CSN/CENTRAL/DATA";
//    private Set<String> TOPIC_SET;
//
//    static final int MSG_NUM = 10;
//
//    public static void main(String[] args) {
//        Connection publisher = new Connection();
//
//        try {
//            publisher.publishSensorData();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void connectionLost(Throwable throwable) {
//
//    }
//
//    @Override
//    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
//
//    }
//
//    @Override
//    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
//
//    }
//
//    public void publishSensorData() throws JsonProcessingException {
//        // Setup MQTT Client
//        connOpt = new MqttConnectOptions();
//        connOpt.setCleanSession(true);
//        connOpt.setKeepAliveInterval(30);
//
//        // Connect to Broker
//        try {
//            logger.info("Connecting to {}", TEMP_CSN_URL);
//            myClient = new MqttClient(TEMP_CSN_URL, TEMP_SNSR_URI);
//            myClient.setCallback(this);
//            myClient.connect(connOpt);
//        } catch (MqttException e) {
//            e.printStackTrace();
//            System.exit(-1);
//        }
//
//        System.out.println("Connected to " + TEMP_CSN_URL);
//
//        // Setup topic
//        String myTopic = TEMP_SINGLE_TOPIC;
//        MqttTopic topic = myClient.getTopic(myTopic);
//        int randNum = 0;
//        jsonMapper = new ObjectMapper();
//
//        for (int i=1; i<=MSG_NUM; i++) {
//            Calendar calendar = Calendar.getInstance();
//            Date date = calendar.getTime();
//            String today = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
//
//            Random oRandom = new Random();
//
//            // 1~10까지의 정수를 랜덤하게 출력
//            randNum = oRandom.nextInt(999) + 1;
//
//            Map dummyData = new HashMap();
//            dummyData.put("val", Integer.toString(randNum));
//            dummyData.put("time", today);
//            dummyData.put("uri", TEMP_SNSR_URI);
//
//            String jsonStr = jsonMapper.writeValueAsString(dummyData);
//            System.out.println("Pub MSG: " + jsonStr);
//
//            int pubQoS = 0;
//            MqttMessage message = new MqttMessage(jsonStr.getBytes());
//            message.setQos(pubQoS);
//            message.setRetained(false);
//
//            // Publish the message
//            //System.out.println("Publishing to topic \"" + topic + "\" qos " + pubQoS);
//            MqttDeliveryToken token = null;
//            try {
//                // publish message to broker
//                token = topic.publish(message);
//                // Wait until the message has been delivered to the broker
//                token.waitForCompletion();
//                Thread.sleep(500);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        // disconnect
//        try {
//            myClient.disconnect();
//            System.out.println("Close Connection");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
