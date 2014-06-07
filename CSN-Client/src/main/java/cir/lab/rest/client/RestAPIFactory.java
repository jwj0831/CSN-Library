package cir.lab.rest.client;

import cir.lab.rest.csn.CSNAPIImpl;
import cir.lab.rest.network.SensorNetworkAPIImpl;
import cir.lab.rest.sensor.SensorAPIImpl;

public class RestAPIFactory {
    public CSNAPI csnAPI() {
        return new CSNAPIImpl();
    }

    public SensorAPI sensorAPI() {
        return new SensorAPIImpl();
    }

    public SensorNetworkAPI sensorNetworkAPI() {
        return new SensorNetworkAPIImpl();
    }
}
