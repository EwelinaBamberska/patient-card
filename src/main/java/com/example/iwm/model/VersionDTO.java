package com.example.iwm.model;

public class VersionDTO {
    private String version;
    private String creationDate;
    private String id;

    public VersionDTO(String authoredOn, String versionIdPart, String id) {
        this.version = versionIdPart;
        this.creationDate = authoredOn;
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
