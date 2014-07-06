package cir.lab.csn.metadata.csn;

import cir.lab.csn.util.TimeGeneratorUtil;

/**
 * Created by nfm on 2014. 5. 20..
 */
// TODO Add more configuration information
public class CSNConfigMetadata {
    private String csnName;
    private String creationTime;
    private String adminName;
    private String adminEmail;

    public CSNConfigMetadata() {
    }

    public CSNConfigMetadata(String csnName) {
        this.csnName = csnName;
        this.creationTime = TimeGeneratorUtil.getCurrentTimestamp();
        this.adminName = "";
        this.adminEmail = "";
    }

    public CSNConfigMetadata(String csnName, String adminName, String adminEmail) {
        this.csnName = csnName;
        this.creationTime = TimeGeneratorUtil.getCurrentTimestamp();
        this.adminName = adminName;
        this.adminEmail = adminEmail;
    }

    public String getCsnName() {
        return csnName;
    }

    public void setCsnName(String csnName) {
        this.csnName = csnName;
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
                "csnName='" + csnName + '\'' +
                ", creationTime='" + creationTime + '\'' +
                ", adminName='" + adminName + '\'' +
                ", adminEmail='" + adminEmail + '\'' +
                '}';
    }
}