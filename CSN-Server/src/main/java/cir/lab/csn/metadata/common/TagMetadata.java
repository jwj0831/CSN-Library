package cir.lab.csn.metadata.common;

import java.util.Set;

public class TagMetadata {
    private Set<String> tags;

    public TagMetadata() {
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "TagMetadata{" +
                "tags=" + tags +
                '}';
    }
}
