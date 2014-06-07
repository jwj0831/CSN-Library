package cir.lab.csn.metadata.sensor;

public class SeedMetadata {
    private String name;
    private String measure;

    public SeedMetadata() {
    }

    public SeedMetadata(String name, String measure) {
        this.name = name;
        this.measure = measure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    @Override
    public String toString() {
        return "Seed{" +
                "name='" + name + '\'' +
                ", measure='" + measure + '\'' +
                '}';
    }
}
