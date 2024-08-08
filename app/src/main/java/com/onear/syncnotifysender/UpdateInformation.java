package com.onear.syncnotifysender;




public class UpdateInformation{
    public UpdateInformation() {
    }

    public UpdateInformation(int internalVersion) {
        this.internalVersion = internalVersion;
    }

    public UpdateInformation(String targetURI) {
        this.targetURI = targetURI;
    }

    public UpdateInformation(int internalVersion, String targetURI) {
        this.internalVersion = internalVersion;
        this.targetURI = targetURI;
    }

    public void setInternalVersion(int internalVersion) {
        this.internalVersion = internalVersion;
    }

    public int getInternalVersion() {
        return internalVersion;
    }

    private int internalVersion = 0;

    public String getTargetURI() {
        return targetURI;
    }

    public void setTargetURI(String targetURI) {
        this.targetURI = targetURI;
    }

    private String targetURI;

}
