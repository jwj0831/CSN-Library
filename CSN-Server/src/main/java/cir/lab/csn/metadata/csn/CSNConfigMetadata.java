package cir.lab.csn.metadata.csn;

import cir.lab.csn.util.TimeGeneratorUtil;

/**
 * Created by nfm on 2014. 5. 20..
 */
// TODO Add more configuration information
public class CSNConfigMetadata {
    private String name;
    private String creationTime;
    private String adminName;
    private String adminEmail;

    public CSNConfigMetadata() {
    }

    public CSNConfigMetadata(String name) {
        this.name = name;
        this.creationTime = TimeGeneratorUtil.getCurrentTimestamp();
        this.adminName = "";
        this.adminEmail = "";
    }

    public CSNConfigMetadata(String name, String adminName, String adminEmail) {
        this.name = name;
        this.creationTime = TimeGeneratorUtil.getCurrentTimestamp();
        this.adminName = adminName;
        this.adminEmail = adminEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    @Override
    public String toString() {
        return "CSNConfigMetadata{" +
                "name='" + name + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                '}';
    }
}
