package org.example.model;

import java.math.BigDecimal;

public class WeatherForecast {

    private GeoLocation geoLocation;
    private BigDecimal[] temperature = new BigDecimal[5];

    public WeatherForecast(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    public BigDecimal[] getTemperature() {
        return temperature;
    }

    public void setTemperature(BigDecimal[] temperature) {
        this.temperature = temperature;
    }
}
