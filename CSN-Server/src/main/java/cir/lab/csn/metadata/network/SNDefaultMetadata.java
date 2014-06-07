package cir.lab.csn.metadata.network;

import java.util.Set;

public class SNDefaultMetadata extends SeedMetadata {
    private String id;
    private String creationTime;
    private String removalTime;
    private String topicName;
    private int alive;

    public SNDefaultMetadata() {
    }

    public SNDefaultMetadata(String name, Set<String> members, String id, String creationTime, String removalTime, String topicName, int alive) {
        super(name, members);
        this.id = id;
        this.creationTime = creationTime;
        this.removalTime = removalTime;
        this.topicName = topicName;
        this.alive = alive;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getRemovalTime() {
        return removalTime;
    }

    public void setRemovalTime(String removalTime) {
        this.removalTime = removalTime;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }

    public int getAlive() {
        return alive;
    }

    public void setAlive(int alive) {
        this.alive = alive;
    }

    @Override
    public String toString() {
        return "DefaultMetadata{" +
                "id='" + id + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", removalTime='" + removalTime + '\'' +
                ", topicName='" + topicName + '\'' +
                ", alive=" + alive +
                '}';
    }
}
