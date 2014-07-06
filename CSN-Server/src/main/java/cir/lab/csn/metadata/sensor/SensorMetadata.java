package cir.lab.csn.metadata.sensor;

import java.util.Map;
import java.util.Set;

public class SensorMetadata {
    private DefaultMetadata defMeta;
    private Map<String, String> optMeta;
    private Set<String> snsrTags;



    public SensorMetadata() {
    }

    public DefaultMetadata getDefMeta() {
        return defMeta;
    }

    public void setDefMeta(DefaultMetadata defMeta) {
        this.defMeta = defMeta;
    }

    public Map<String, String> getOptMeta() {
        return optMeta;
    }

    public void setOptMeta(Map<String, String> optMeta) {
        this.optMeta = optMeta;
    }

    public Set<String> getSnsrTags() {
        return snsrTags;
    }

    public void setSnsrTags(Set<String> snsrTags) {
        this.snsrTags = snsrTags;
    }

    @Override
    public String toString() {
        return "SensorMetadata{" +
                "defMeta=" + defMeta +
                ", optMeta=" + optMeta +
                ", snsrTags=" + snsrTags +
                '}';
    }
}
