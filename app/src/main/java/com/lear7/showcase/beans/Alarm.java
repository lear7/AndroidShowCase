
package com.lear7.showcase.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alarm {

    @SerializedName("alarm_type")
    @Expose
    private String alarmType;
    @SerializedName("alarm_level")
    @Expose
    private String alarmLevel;
    @SerializedName("alarm_content")
    @Expose
    private String alarmContent;

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getAlarmLevel() {
        return alarmLevel;
    }

    public void setAlarmLevel(String alarmLevel) {
        this.alarmLevel = alarmLevel;
    }

    public String getAlarmContent() {
        return alarmContent;
    }

    public void setAlarmContent(String alarmContent) {
        this.alarmContent = alarmContent;
    }

}
