package cir.lab.rest.csn;

import cir.lab.rest.client.CSNAPI;
import cir.lab.rest.common.RESTMethodAPI;
import cir.lab.rest.common.ServerConnInfo;

// http://www.mkyong.com/webservices/jax-rs/restful-java-client-with-jersey-client/
public class CSNAPIImpl implements CSNAPI {
    private RESTMethodAPI api = new RESTMethodAPI();

    @Override
    public String setCSNConfiguration(String input) {
        String url = ServerConnInfo.URL + ServerConnInfo.CSN_URL_NM;
        return api.postMethod(url, input);
    }

    @Override
    public String getCSNConfiguration() {
        String url = ServerConnInfo.URL + ServerConnInfo.CSN_URL_NM;
        return api.getMethod(url);
    }

    @Override
    public String deleteCSNConfiguration() {
        String url = ServerConnInfo.URL + ServerConnInfo.CSN_URL_NM;
        return api.deleteMethod(url);
    }

    @Override
    public String startSystem(String input) {
        String url = ServerConnInfo.URL + ServerConnInfo.CSN_URL_NM + "/start";
        return api.postMethod(url, input);
    }

    @Override
    public String restartSystem(String input) {
        String url = ServerConnInfo.URL + ServerConnInfo.CSN_URL_NM + "/restart";
        return api.postMethod(url, input);
    }

    @Override
    public String stopSystem(String input) {
        String url = ServerConnInfo.URL + ServerConnInfo.CSN_URL_NM + "/stop";
        return api.postMethod(url, input);
    }
}
