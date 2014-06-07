package cir.lab.csn.metadata.network;

import java.util.Set;

public class SeedMetadata {
    private String name;
    private Set<String> members;

    public SeedMetadata() {
    }

    public SeedMetadata(String name, Set<String> members) {
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getMembers() {
        return members;
    }

    public void setMembers(Set<String> members) {
        this.members = members;
    }

    @Override
    public String toString() {
        return "SeedMetadata{" +
                "name='" + name + '\'' +
                ", members=" + members +
                '}';
    }
}
