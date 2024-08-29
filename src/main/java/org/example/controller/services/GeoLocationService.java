package org.example.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.example.model.GeoLocation;

import java.math.BigDecimal;

public class GeoLocationService extends Service {

    private GeoLocation geoLocation;

    public GeoLocationService(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    System.out.println("uruchomiono task");
                    System.out.println("API_KEY" + APIKey.getApiKey());
                    System.out.println("GEO: " + geoLocation.getCity() + " " + geoLocation.getCountry());
                    BigDecimal lat = new BigDecimal("50.80") ;
                    geoLocation.setLatitude(lat);
                    BigDecimal lon = new BigDecimal("15.30") ;
                    geoLocation.setLongitude(lon);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
}
