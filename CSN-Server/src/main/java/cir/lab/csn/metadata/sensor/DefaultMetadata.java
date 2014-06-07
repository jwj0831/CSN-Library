package cir.lab.csn.metadata.sensor;

public class DefaultMetadata extends SeedMetadata {
    private String id;
    private String reg_time;

    public DefaultMetadata() {
    }

    public DefaultMetadata(String name, String measure, String id, String reg_time) {
        super(name, measure);
        this.id = id;
        this.reg_time = reg_time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    @Override
    public String toString() {
        return "DefaultMetadata{" +
                "id='" + id + '\'' +
                ", reg_time='" + reg_time + '\'' +
                '}';
    }
}
