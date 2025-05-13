package com.example.greengeeksweatherapp.Domains;

public class Hourly {
    private String Hour;
    private String temp;

    //private String tempType;

    private String picPath;


    public Hourly(String hour, String temp, String picPath) {
        Hour = hour;
        this.temp = temp;
        this.picPath = picPath;
    }

    public String getHour() {
        return Hour;
    }

    public void setHour(String hour) {
        Hour = hour;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public String getPicPath() {
        return picPath;
    }

    public void setPicPath(String picPath) {
        this.picPath = picPath;
    }
}
