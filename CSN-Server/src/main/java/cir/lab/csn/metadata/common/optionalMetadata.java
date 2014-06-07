package cir.lab.csn.metadata.common;

import java.util.Map;

public class optionalMetadata {
    private Map<String,String> elmts;

    public optionalMetadata() {
    }

    public Map<String, String> getElmts() {
        return elmts;
    }

    public void setElmts(Map<String, String> elmts) {
        this.elmts = elmts;
    }

    @Override
    public String toString() {
        return "AdditionalMetadata{" +
                "elmts=" + elmts +
                '}';
    }
}
