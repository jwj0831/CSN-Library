package cir.lab.csn.metadata.network;

import java.util.Map;
import java.util.Set;

public class SensorNetworkMetadata {
    private SNDefaultMetadata defMeta;
    private Map<String,String> optMeta;
    private Set<String> snTags;

    public SensorNetworkMetadata() {
    }

    public SNDefaultMetadata getDefMeta() {
        return defMeta;
    }

    public void setDefMeta(SNDefaultMetadata defMeta) {
        this.defMeta = defMeta;
    }

    public Map<String,String> getOptMeta() {
        return optMeta;
    }

    public void setOptMeta(Map<String,String> optMeta) {
        this.optMeta = optMeta;
    }

    public Set<String> getSnTags() {
        return snTags;
    }

    public void setSnTags(Set<String> snTags) {
        this.snTags = snTags;
    }

    @Override
    public String toString() {
        return "SensorNetworkMetadata{" +
                "defMeta=" + defMeta +
                ", optMeta=" + optMeta +
                ", snTags=" + snTags +
                '}';
    }
}
