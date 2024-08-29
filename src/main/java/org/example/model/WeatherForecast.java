package org.example.model;

import java.math.BigDecimal;

public class WeatherForecast {

    private GeoLocation geoLocation;
    private BigDecimal[] temperatureDay = new BigDecimal[5];
    private BigDecimal[] temperatureNight = new BigDecimal[5];
    private String[] day = new String[5];

    public WeatherForecast(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public BigDecimal[] getTemperatureDay() {
        return temperatureDay;
    }

    public void setTemperatureDay(BigDecimal[] temperatureDay) {
        this.temperatureDay = temperatureDay;
    }

    public BigDecimal[] getTemperatureNight() {
        return temperatureNight;
    }

    public void setTemperatureNight(BigDecimal[] temperatureNight) {
        this.temperatureNight = temperatureNight;
    }

    public String[] getDay() {
        return day;
    }

    public void setDay(String[] day) {
        this.day = day;
    }
}
