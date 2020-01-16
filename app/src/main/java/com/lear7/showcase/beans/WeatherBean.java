
package com.lear7.showcase.beans;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherBean {

    @SerializedName("cityid")
    @Expose
    private String cityid;
    @SerializedName("update_time")
    @Expose
    private String updateTime;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("cityEn")
    @Expose
    private String cityEn;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("countryEn")
    @Expose
    private String countryEn;
    @SerializedName("data")
    @Expose
    private List<Datum> data = null;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryEn() {
        return countryEn;
    }

    public void setCountryEn(String countryEn) {
        this.countryEn = countryEn;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}
