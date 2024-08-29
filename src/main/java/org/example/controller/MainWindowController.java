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
import org.example.model.GeoLocation;
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
        GeoLocationService geoLocationService = new GeoLocationService(geoLocation, 1000);
        geoLocationService.start();
        geoLocationService.setOnSucceeded(e -> {
                System.out.println("lattitude: " + geoLocation.getLatitude() + " longitude: " + geoLocation.getLongitude());
                System.out.println("event zakonczyl sie pomyslnie!");
            }
        );
    }

    @FXML
    void clickButtonRefreshRight(ActionEvent event) {
        GeoLocation geoLocation = new GeoLocation(cityRight.getText(), countryRight.getText());
        GeoLocationService geoLocationService = new GeoLocationService(geoLocation, 10);
        geoLocationService.start();
        geoLocationService.setOnSucceeded(e -> {
                    System.out.println("lattitude: " + geoLocation.getLatitude() + " longitude: " + geoLocation.getLongitude());
                    System.out.println("event zakonczyl sie pomyslnie!");
                }
        );
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryLeft.setText("Polska");
        cityLeft.setText("Poznan");
        countryRight.setText("Anglia");
        cityRight.setText("Londyn");
    }
}
