package cir.lab.dao;

public class SubAirData {
    private String id;
    private String time;
    private String val;

    public SubAirData() {

    }

    public SubAirData (String id, String time, String val) {
        this.id = id;
        this.time = time;
        this.val = val;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", val='" + val + '\'' +
                '}';
    }

}
