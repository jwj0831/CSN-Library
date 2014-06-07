package cir.lab.csn.data;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by nfm on 2014. 5. 20..
 */
public class SensorNetworkList {
    private static Map<String, Set<String>> sensorNetworkListMap = new HashMap<String, Set<String>>();;
    private static boolean updating = false;

    public SensorNetworkList() {
       // sensorNetworkListMap = new HashMap<String, Set<String>>();
    }

    public static int registerNewSensorNetwork(String snTopicName, Set<String> sensors) {
        updating = true;
        sensorNetworkListMap.put(snTopicName, sensors);
        updating = false;
        return 0;
    }

    public static Map<String, Set<String>> getSensorNetworkListMap() {
        return sensorNetworkListMap;
    }

    public static void setSensorNetworkListMap(Map<String, Set<String>> sensorNetworkListMap) {
        SensorNetworkList.sensorNetworkListMap = sensorNetworkListMap;
    }

    public static boolean isUpdating() {
        return updating;
    }

    public static void setUpdating(boolean updating) {
        SensorNetworkList.updating = updating;
    }
}
