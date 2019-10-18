
package com.lear7.showcase.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hour {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("wea")
    @Expose
    private String wea;
    @SerializedName("tem")
    @Expose
    private String tem;
    @SerializedName("win")
    @Expose
    private String win;
    @SerializedName("win_speed")
    @Expose
    private String winSpeed;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWinSpeed() {
        return winSpeed;
    }

    public void setWinSpeed(String winSpeed) {
        this.winSpeed = winSpeed;
    }

}
