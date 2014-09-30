package cir.lab.rest.network;

import cir.lab.rest.client.SensorNetworkAPI;
import cir.lab.rest.common.RESTMethodAPI;
import cir.lab.rest.common.ServerConnInfo;

public class SensorNetworkAPIImpl implements SensorNetworkAPI {
    private RESTMethodAPI api = new RESTMethodAPI();
    String defaultURL = ServerConnInfo.URL + ServerConnInfo.SN_URL_NM;

    @Override
    public String postSensorNetworkMetadata(String input) {
        return api.postMethod(defaultURL, input);
    }

    @Override
    public String getAllSensorNetworkMetadata() {
        return api.getMethod(defaultURL);
    }

    @Override
    public String deleteAllSensorNetworkMetadata() {
        return api.deleteMethod(defaultURL);
    }

    @Override
    public String getAllSensorNetworkIDList() {
        String idsURL = defaultURL + "/ids";
        return api.getMethod(idsURL);
    }

    @Override
    public String getAllSensorNetworkTopicNameList() {
        String topicListURL = defaultURL + "/topics";
        return api.getMethod(topicListURL);
    }

    @Override
    public String getAllSensorNetworkMembersList() {
        String membersURL = defaultURL + "/members";
        return api.getMethod(membersURL);
    }

    @Override
    public String getSensorNetworkMetadata(String id) {
        String url = defaultURL + "/" + id;
        return api.getMethod(url);
    }

    @Override
    public String deleteSensorNetworkMetadata(String id) {
        String url = defaultURL + "/" + id;
        return api.deleteMethod(url);
    }

    @Override
    public String getSensorNetworkMembers(String id) {
        String url = defaultURL + "/" + id + "/members";
        return api.getMethod(url);
    }

    @Override
    public String getSensorNetworkTopicName(String id) {
        String url = defaultURL + "/" + id + "/topic";
        return api.getMethod(url);
    }

    @Override
    public String postSensorNetworkAllOptionalMetadata(String id, String input) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM;
        return api.postMethod(url, input);
    }

    @Override
    public String getSensorNetworkAllOptionalMetadata(String id) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM;
        return api.getMethod(url);
    }

    @Override
    public String updateSensorNetworkAllOptionalMetadata(String id, String input) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM;
        return api.putMethod(url, input);
    }

    @Override
    public String deleteSensorNetworkAllOptionalMetadata(String id) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM;
        return api.deleteMethod(url);
    }

    @Override
    public String getSensorNetworkOptionalMetadata(String id, String optName) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM + "/" + optName;
        return api.getMethod(url);
    }

    @Override
    public String updateSensorNetworkOptionalMetadata(String id, String optName, String optVal) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM + "/" + optName;
        String input = "{\"opt_meta\":{\"elmts\":{\"" + optName + "\":\""+ optVal +"\"}}}";
        return api.putMethod(url, input);
    }

    @Override
    public String deleteSensorNetworkOptionalMetadata(String id, String optName) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM + "/" + optName;
        return api.deleteMethod(url);
    }

    @Override
    public String postSensorNetworkAllTagMetadata(String id, String input) {
        String url = defaultURL +"/" + id + ServerConnInfo.TAG_URL_NM;
        return api.postMethod(url, input);
    }

    @Override
    public String getSensorNetworkAllTagMetadata(String id) {
        String url = defaultURL +"/" + id + ServerConnInfo.TAG_URL_NM;
        return api.getMethod(url);
    }

    @Override
    public String deleteSensorNetworkAllTagMetadata(String id) {
        String url = defaultURL +"/" + id + ServerConnInfo.TAG_URL_NM;
        return api.deleteMethod(url);
    }

    @Override
    public String updateSensorNetworkTagMetadata(String id, String tag, String newTag) {
        String url = defaultURL +"/" + id + ServerConnInfo.TAG_URL_NM + "/" + tag;
        String input = "{\"tag_meta\":{\"tags\":[\"" + newTag + "\"]}}";
        return api.putMethod(url, input);
    }

    @Override
    public String deleteSensorNetworkTagMetadata(String id, String tag) {
        String url = defaultURL +"/" + id + ServerConnInfo.TAG_URL_NM + "/" + tag;
        return api.deleteMethod(url);
    }
}
