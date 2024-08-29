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

public class GeoLocationService extends Service {

    private GeoLocation geoLocation;
    private long msSleep;

    public GeoLocationService(GeoLocation geoLocation, long msSleep) {
        this.geoLocation = geoLocation;
        this.msSleep = msSleep;
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    getGeoLocation(geoLocation, APIKey.getApiKey(), msSleep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private void getGeoLocation(GeoLocation geoLocation, String apiKey, long msSleep) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            Thread.sleep(msSleep);

            String url = "https://api.openweathermap.org/geo/1.0/direct?q=" + geoLocation.getCity() + "," + geoLocation.getCountry() + "&appid=" + apiKey;
            ClassicHttpRequest httpGet = ClassicRequestBuilder.get(url)
                    .build();

            httpclient.execute(httpGet, response -> {
                System.out.println("GET: " + response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity = response.getEntity();

                String jsonString = EntityUtils.toString(entity);

                JSONArray jsonResultArray = new JSONArray(jsonString);
                JSONObject jsonGeoObject = jsonResultArray.getJSONObject(0);
                geoLocation.setLatitude(jsonGeoObject.getBigDecimal("lat"));
                geoLocation.setLongitude(jsonGeoObject.getBigDecimal("lon"));

                EntityUtils.consume(entity);
                return null;
            });

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
