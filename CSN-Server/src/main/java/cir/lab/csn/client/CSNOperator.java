package cir.lab.csn.client;

import cir.lab.csn.metadata.csn.CSNConfigMetadata;

/**
 * Created by nfm on 2014. 5. 20..
 */
public interface CSNOperator extends CSNLifeCycleInterface {

    public CSNConfigMetadata getCsnConfigMetadata();

    public SensorNetworkManager getSensorNetworkManager();

    public int getBrokerMessageNum();
}
