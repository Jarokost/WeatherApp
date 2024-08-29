package org.example.controller.services;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpEntity;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.support.ClassicRequestBuilder;
import org.example.model.GeoLocation;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
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
//                    System.out.println("uruchomiono task");
//                    System.out.println("API_KEY" + APIKey.getApiKey());
//                    System.out.println("GEO: " + geoLocation.getCity() + " " + geoLocation.getCountry());
//                    BigDecimal lat = new BigDecimal("50.80") ;
//                    geoLocation.setLatitude(lat);
//                    BigDecimal lon = new BigDecimal("15.30") ;
//                    geoLocation.setLongitude(lon);
                    getGeoLocation(geoLocation, APIKey.getApiKey());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private void getGeoLocation(GeoLocation geoLocation, String apiKey) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            String url = "https://api.openweathermap.org/geo/1.0/direct?q=" + geoLocation.getCity() + "," + geoLocation.getCountry() + "&appid=" + apiKey;
            ClassicHttpRequest httpGet = ClassicRequestBuilder.get(url)
                    .build();

            httpclient.execute(httpGet, response -> {
                System.out.println("GET: " + response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity1 = response.getEntity();

                String retSrc = EntityUtils.toString(entity1);

                JSONArray result1 = new JSONArray(retSrc);
                JSONObject result2 = result1.getJSONObject(0);
                geoLocation.setLatitude(result2.getBigDecimal("lat"));
                geoLocation.setLongitude(result2.getBigDecimal("lon"));

                EntityUtils.consume(entity1);
                return null;
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
