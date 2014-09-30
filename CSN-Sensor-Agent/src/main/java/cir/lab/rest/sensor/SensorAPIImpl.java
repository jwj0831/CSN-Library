package cir.lab.rest.sensor;

import cir.lab.rest.client.SensorAPI;
import cir.lab.rest.common.RESTMethodAPI;
import cir.lab.rest.common.ServerConnInfo;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Set;


public class SensorAPIImpl implements SensorAPI {
    private RESTMethodAPI api = new RESTMethodAPI();

    String defaultURL = ServerConnInfo.URL + ServerConnInfo.SNSR_URL_NM;

    @Override
    public String postSensorMetadata(String input) {
        return api.postMethod(defaultURL, input);
    }

    @Override
    public String getAllSensorMetadata() {
        return api.getMethod(defaultURL);
    }

    @Override
    public String deleteAllSensorMetadata() {
        return api.deleteMethod(defaultURL);
    }

    @Override
    public String getAllSensorIDList() {
        String idsURL = defaultURL + "/ids";
        return api.getMethod(idsURL);
    }

    @Override
    public String getSensorMetadata(String id) {
        String url = defaultURL + "/" + id;
        return api.getMethod(url);
    }

    @Override
    public String deleteSensorMetadata(String id) {
        String url = defaultURL + "/" + id;
        return api.deleteMethod(url);
    }

    @Override
    public Set<String> getSensorNetworkIDs(String id) {
        String url = defaultURL + "/" + id + "/networks";
        String jsonStr = api.getMethod(url);
        ObjectMapper mapper = new ObjectMapper();
        Set<String> ids = null;
        try {
            ids = mapper.readValue(jsonStr, Set.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ids;
    }

    @Override
    public Set<String> getTopicNames(String id) {
        String url = defaultURL +"/" + id + "/networks/topics";
        String jsonStr = api.getMethod(url);
        ObjectMapper mapper = new ObjectMapper();
        Set<String> names = null;
        try {
            names = mapper.readValue(jsonStr, Set.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return names;
    }


    @Override
    public String postSensorAllOptionalMetadata(String id, String input) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM;
        return api.postMethod(url, input);
    }

    @Override
    public String getSensorAllOptionalMetadata(String id) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM;
        return api.getMethod(url);
    }

    @Override
    public String updateSensorAllOptionalMetadata(String id, String input) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM;
        return api.putMethod(url, input);
    }

    @Override
    public String deleteSensorAllOptionalMetadata(String id) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM;
        return api.deleteMethod(url);
    }

    @Override
    public String getSensorOptionalMetadata(String id, String optName) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM + "/" + optName;
        return api.getMethod(url);
    }

    @Override
    public String updateSensorOptionalMetadata(String id, String optName, String optVal) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM + "/" + optName;
        String input = "{\"opt_meta\":{\"elmts\":{\"" + optName + "\":\""+ optVal +"\"}}}";
        return api.putMethod(url, input);
    }

    @Override
    public String deleteSensorOptionalMetadata(String id, String optName) {
        String url = defaultURL +"/" + id + ServerConnInfo.OPT_URL_NM + "/" + optName;
        return api.deleteMethod(url);
    }

    @Override
    public String postSensorAllTagMetadata(String id, String input) {
        String url = defaultURL +"/" + id + ServerConnInfo.TAG_URL_NM;
        return api.postMethod(url, input);
    }

    @Override
    public String getSensorAllTagMetadata(String id) {
        String url = defaultURL +"/" + id + ServerConnInfo.TAG_URL_NM;
        return api.getMethod(url);
    }

    @Override
    public String deleteSensorAllTagMetadata(String id) {
        String url = defaultURL +"/" + id + ServerConnInfo.TAG_URL_NM;
        return api.deleteMethod(url);
    }

    @Override
    public String updateSensorTagMetadata(String id, String tag, String newTag) {
        String url = defaultURL +"/" + id + ServerConnInfo.TAG_URL_NM + "/" + tag;
        String input = "{\"tag_meta\":{\"tags\":[\"" + newTag + "\"]}}";
        return api.putMethod(url, input);
    }

    @Override
    public String deleteSensorTagMetadata(String id, String tag) {
        String url = defaultURL +"/" + id + ServerConnInfo.TAG_URL_NM + "/" + tag;
        return api.deleteMethod(url);
    }
}
