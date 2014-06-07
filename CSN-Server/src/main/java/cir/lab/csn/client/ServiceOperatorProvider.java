package cir.lab.csn.client;

import cir.lab.csn.component.CSNOperatorImpl;
import cir.lab.csn.metadata.csn.CSNConfigMetadata;

/**
 *
 * Created by nfm on 2014. 5. 20..
 */
public final class ServiceOperatorProvider {
    private static CSNOperatorImpl operatorImpl = null;

    public static CSNOperator getCSNCoreService(String CSN_Name) {
        if(operatorImpl == null)
            return new CSNOperatorImpl(CSN_Name);

        return operatorImpl;
    }

    public static CSNOperator getCSNCoreService(String CSN_Name, CSNConfigMetadata config) {
        if(operatorImpl == null)
            return new CSNOperatorImpl(CSN_Name, config);

        return operatorImpl;
    }
}
