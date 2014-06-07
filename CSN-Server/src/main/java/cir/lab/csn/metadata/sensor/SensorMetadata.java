package cir.lab.csn.metadata.sensor;

import cir.lab.csn.metadata.common.OptionalMetadata;
import cir.lab.csn.metadata.common.TagMetadata;

public class SensorMetadata {
    private DefaultMetadata def_meta;
    private OptionalMetadata opt_meta = new OptionalMetadata();;
    private TagMetadata snsr_tag = new TagMetadata();



    public SensorMetadata() {
    }

    public DefaultMetadata getDef_meta() {
        return def_meta;
    }

    public void setDef_meta(DefaultMetadata def_meta) {
        this.def_meta = def_meta;
    }

    public OptionalMetadata getOpt_meta() {
        return opt_meta;
    }

    public void setOpt_meta(OptionalMetadata opt_meta) {
        this.opt_meta = opt_meta;
    }

    public TagMetadata getSnsr_tag() {
        return snsr_tag;
    }

    public void setSnsr_tag(TagMetadata snsr_tag) {
        this.snsr_tag = snsr_tag;
    }

    @Override
    public String toString() {
        return "SensorMetadata{" +
                "def_meta=" + def_meta +
                ", opt_meta=" + opt_meta +
                ", snsr_tag=" + snsr_tag +
                '}';
    }
}
