package cir.lab.csn.metadata.sensor;

public class SeedMetadata {
    private String name;
    private String measure;

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
