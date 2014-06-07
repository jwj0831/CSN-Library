package cir.lab.csn.metadata;

/**
 * Created by nfm on 2014. 5. 20..
 */
public class SensorData {
    private String uri;
    private String time;
    private String val;

    public SensorData() {

    }

    public SensorData (String uri, String time, String val) {
        this.uri = uri;
        this.time = time;
        this.val = val;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "SensorData{" +
                "uri='" + uri + '\'' +
                ", time='" + time + '\'' +
                ", val='" + val + '\'' +
                '}';
    }
}
