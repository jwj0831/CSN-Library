package cir.lab.test4;

import org.eclipse.paho.client.mqttv3.*;
import sun.plugin2.message.Message;

/**
 * Created by nfm on 2014. 9. 29..
 */
public class CSNSubscriber implements MqttCallback {
    private MqttClient myClient;
    private static final boolean MQTT_CLEAN_SESSION_OPT = true;
    private static final int MQTT_KEEP_ALIVE_INTERVAL_OPT = 30;
    private static final int MQTT_QOS_OPT = 1;
    private MessageCallback callback;
    private String domain;
    private String appName;
    private String topicName;

    public void setConnection(String domain, String appName, String topicName) {
        this.domain = domain;
        this.appName = appName;
        this.topicName = topicName;
    }

    public void setMessageCallback(MessageCallback callback) {
        this.callback = callback;
    }

    public void subscribe() {
        // setup MQTT Client
        MqttConnectOptions connOpt = new MqttConnectOptions();
        connOpt.setCleanSession(MQTT_CLEAN_SESSION_OPT);
        connOpt.setKeepAliveInterval(MQTT_KEEP_ALIVE_INTERVAL_OPT);

        // Connect to Broker
        try {
            myClient = new MqttClient(domain, appName);
            myClient.setCallback(this);
            myClient.connect(connOpt);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        // Subscribe to topic if subscriber
        try {
            System.out.println("Start to subscribe to " + topicName);
            myClient.subscribe(topicName, MQTT_QOS_OPT);
       }  catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void unsubscribe() {
        try {
            myClient.disconnect();
            System.out.println("Disconneted to " + topicName);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        String data = message.toString();
        callback.messageArrived(data);
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

}
