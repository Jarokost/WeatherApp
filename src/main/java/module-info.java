module org.example {
    requires javafx.fxml;
    requires javafx.controls;
    requires javafx.graphics;

    opens org.example;
    opens org.example.controller;
    opens org.example.view;
}