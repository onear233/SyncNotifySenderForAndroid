package com.onear.syncnotifysender;


import android.net.Uri;

import java.net.URL;

public class UpdateInformation {
    public UpdateInformation() {
    }

    public UpdateInformation(int internalVersion) {
        this.internalVersion = internalVersion;
    }

    public UpdateInformation(Uri targetURI) {
        this.targetURI = targetURI;
    }

    public UpdateInformation(String updateLog) {
        this.updateLog = updateLog;
    }

    public UpdateInformation(int internalVersion, String targetURI) {
        this.internalVersion = internalVersion;
        this.targetURI = Uri.parse(targetURI);
    }


    public void setInternalVersion(int internalVersion) {
        this.internalVersion = internalVersion;
    }

    public int getInternalVersion() {
        return internalVersion;
    }

    private int internalVersion = 0;

    //更新日志属性设置
    public String getUpdateLog() {
        return updateLog;
    }

    public void setUpdateLog(String updateLog) {
        this.updateLog = updateLog;
    }

    private String updateLog = "";


    public Uri getTargetURI() {
        return targetURI;
    }

    public void setTargetURI(Uri targetURI) {
        this.targetURI = targetURI;
    }

    private Uri targetURI;

}
