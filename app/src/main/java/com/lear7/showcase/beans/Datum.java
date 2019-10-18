
package com.lear7.showcase.beans;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("week")
    @Expose
    private String week;
    @SerializedName("wea")
    @Expose
    private String wea;
    @SerializedName("wea_img")
    @Expose
    private String weaImg;
    @SerializedName("air")
    @Expose
    private Integer air;
    @SerializedName("humidity")
    @Expose
    private Integer humidity;
    @SerializedName("air_level")
    @Expose
    private String airLevel;
    @SerializedName("air_tips")
    @Expose
    private String airTips;
    @SerializedName("alarm")
    @Expose
    private Alarm alarm;
    @SerializedName("tem1")
    @Expose
    private String tem1;
    @SerializedName("tem2")
    @Expose
    private String tem2;
    @SerializedName("tem")
    @Expose
    private String tem;
    @SerializedName("win")
    @Expose
    private List<String> win = null;
    @SerializedName("win_speed")
    @Expose
    private String winSpeed;
    @SerializedName("hours")
    @Expose
    private List<Hour> hours = null;
    @SerializedName("index")
    @Expose
    private List<Index> index = null;

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWeaImg() {
        return weaImg;
    }

    public void setWeaImg(String weaImg) {
        this.weaImg = weaImg;
    }

    public Integer getAir() {
        return air;
    }

    public void setAir(Integer air) {
        this.air = air;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public String getAirLevel() {
        return airLevel;
    }

    public void setAirLevel(String airLevel) {
        this.airLevel = airLevel;
    }

    public String getAirTips() {
        return airTips;
    }

    public void setAirTips(String airTips) {
        this.airTips = airTips;
    }

    public Alarm getAlarm() {
        return alarm;
    }

    public void setAlarm(Alarm alarm) {
        this.alarm = alarm;
    }

    public String getTem1() {
        return tem1;
    }

    public void setTem1(String tem1) {
        this.tem1 = tem1;
    }

    public String getTem2() {
        return tem2;
    }

    public void setTem2(String tem2) {
        this.tem2 = tem2;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public List<String> getWin() {
        return win;
    }

    public void setWin(List<String> win) {
        this.win = win;
    }

    public String getWinSpeed() {
        return winSpeed;
    }

    public void setWinSpeed(String winSpeed) {
        this.winSpeed = winSpeed;
    }

    public List<Hour> getHours() {
        return hours;
    }

    public void setHours(List<Hour> hours) {
        this.hours = hours;
    }

    public List<Index> getIndex() {
        return index;
    }

    public void setIndex(List<Index> index) {
        this.index = index;
    }

}
