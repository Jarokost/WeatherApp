package org.example.controller;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.controller.services.APIKey;
import org.example.controller.services.GeoLocationService;
import org.example.controller.services.WeatherForecastService;
import org.example.model.GeoLocation;
import org.example.model.WeatherForecast;
import org.example.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private Button buttonRefreshLeft;

    @FXML
    private Button buttonRefreshRight;

    @FXML
    private TextField cityLeft;

    @FXML
    private TextField cityRight;

    @FXML
    private TextField countryLeft;

    @FXML
    private TextField countryRight;

    @FXML
    private Label labelLeftDay1;

    @FXML
    private Label labelLeftDay2;

    @FXML
    private Label labelLeftDay3;

    @FXML
    private Label labelLeftDay4;

    @FXML
    private Label labelLeftDay5;

    @FXML
    private Label labelLeftTemp1;

    @FXML
    private Label labelLeftTemp2;

    @FXML
    private Label labelLeftTemp3;

    @FXML
    private Label labelLeftTemp4;

    @FXML
    private Label labelLeftTemp5;

    @FXML
    private Label labelRightDay1;

    @FXML
    private Label labelRightDay2;

    @FXML
    private Label labelRightDay3;

    @FXML
    private Label labelRightDay4;

    @FXML
    private Label labelRightDay5;

    @FXML
    private Label labelRightTemp1;

    @FXML
    private Label labelRightTemp2;

    @FXML
    private Label labelRightTemp3;

    @FXML
    private Label labelRightTemp4;

    @FXML
    private Label labelRightTemp5;

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @FXML
    void clickButtonRefreshLeft(ActionEvent event) {
        GeoLocation geoLocation = new GeoLocation(cityLeft.getText(), countryLeft.getText());
        GeoLocationService geoLocationService = new GeoLocationService(geoLocation, 1);
        geoLocationService.start();
        geoLocationService.setOnSucceeded(geoLocationEvent -> {
                System.out.println("lattitude: " + geoLocation.getLatitude() + " longitude: " + geoLocation.getLongitude());
                WeatherForecast weatherForecast = new WeatherForecast(geoLocation);
                WeatherForecastService weatherForecastService = new WeatherForecastService(weatherForecast, 1);
                weatherForecastService.start();
                weatherForecastService.setOnSucceeded(weatherServiceEvent -> {
                    displayWeatherLeft(weatherForecast);
                });
        });
    }

    @FXML
    void clickButtonRefreshRight(ActionEvent event) {
        GeoLocation geoLocation = new GeoLocation(cityRight.getText(), countryRight.getText());
        GeoLocationService geoLocationService = new GeoLocationService(geoLocation, 1);
        geoLocationService.start();
        geoLocationService.setOnSucceeded(e -> {
                    System.out.println("lattitude: " + geoLocation.getLatitude() + " longitude: " + geoLocation.getLongitude());
                    WeatherForecast weatherForecast = new WeatherForecast(geoLocation);
                    WeatherForecastService weatherForecastService = new WeatherForecastService(weatherForecast, 1);
                    weatherForecastService.start();
                    weatherForecastService.setOnSucceeded(weatherServiceEvent -> {
                        displayWeatherRight(weatherForecast);
                    });
                }
        );
    }

    private void displayWeatherLeft(WeatherForecast weatherForecast) {
        labelLeftTemp1.setText(weatherForecast.getTemperature()[0].toString());
        labelLeftTemp2.setText(weatherForecast.getTemperature()[1].toString());
        labelLeftTemp3.setText(weatherForecast.getTemperature()[2].toString());
        labelLeftTemp4.setText(weatherForecast.getTemperature()[3].toString());
        labelLeftTemp5.setText(weatherForecast.getTemperature()[4].toString());
    }

    private void displayWeatherRight(WeatherForecast weatherForecast) {
        labelRightTemp1.setText(weatherForecast.getTemperature()[0].toString());
        labelRightTemp2.setText(weatherForecast.getTemperature()[1].toString());
        labelRightTemp3.setText(weatherForecast.getTemperature()[2].toString());
        labelRightTemp4.setText(weatherForecast.getTemperature()[3].toString());
        labelRightTemp5.setText(weatherForecast.getTemperature()[4].toString());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryLeft.setText("Polska");
        cityLeft.setText("Poznan");
        countryRight.setText("Anglia");
        cityRight.setText("Londyn");
    }
}
