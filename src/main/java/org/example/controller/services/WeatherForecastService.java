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
import org.example.model.WeatherForecast;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;

public class WeatherForecastService extends Service {

    private WeatherForecast weatherForecast;
    private long msSleep;

    public WeatherForecastService(WeatherForecast weatherForecast, long msSleep) {
        this.weatherForecast = weatherForecast;
        this.msSleep = msSleep;
    }


    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    getWeatherForecast(weatherForecast, weatherForecast.getGeoLocation(), APIKey.getApiKey(), msSleep);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        };
    }

    private void getWeatherForecast(WeatherForecast weatherForecast, GeoLocation geoLocation, String apiKey, long msSleep){
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

            String url = "https://api.openweathermap.org/data/2.5/forecast?" +
                    "lat=" + geoLocation.getLongitude() +
                    "&lon=" + geoLocation.getLongitude() +
                    "&appid=" + apiKey +
                    "&units=" + "metric";
            ClassicHttpRequest httpGet = ClassicRequestBuilder.get(url).build();

            httpclient.execute(httpGet, response -> {
                System.out.println("GET: " + response.getCode() + " " + response.getReasonPhrase());
                final HttpEntity entity = response.getEntity();

                String retSrc = EntityUtils.toString(entity);

                JSONObject result = new JSONObject(retSrc);
                JSONArray tokenList = result.getJSONArray("list");
                BigDecimal[] temperatureDay = new BigDecimal[5];
                BigDecimal[] temperatureNight = new BigDecimal[5];
                String[] day = new String[5];
                for(int i = 0, j = 0; i<40; i++) {
                    JSONObject oj = tokenList.getJSONObject(i);
                    Integer token = oj.getInt("dt");
                    String date_time = oj.getString("dt_txt");
                    JSONObject oj1 = oj.getJSONObject("main");
                    BigDecimal temp = oj1.getBigDecimal("temp");
                    if(date_time.contains("12:00:00")) {
                        System.out.println("date_time: " + date_time + " temperature: " + temp);
                        temperatureDay[j] = temp;
                        day[j] = date_time.substring(5,10);
                        j++;
                    }
                    if(date_time.contains("03:00:00")) {
                        System.out.println("date_time: " + date_time + " temperature: " + temp);
                        temperatureNight[j] = temp;
                        day[j] = date_time.substring(5,10);
                    }
                }
                weatherForecast.setTemperatureDay(temperatureDay);
                weatherForecast.setTemperatureNight(temperatureNight);
                weatherForecast.setDay(day);

                EntityUtils.consume(entity);
                return null;
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
