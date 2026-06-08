module com.pratica4.application {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.jfoenix;

    opens com.pratica4.application to javafx.graphics, javafx.fxml;
    opens com.pratica4.application.gui to javafx.fxml;
    exports com.pratica4.application;
    exports com.pratica4.application.gui;
    exports com.pratica4.application.utils;
    exports com.pratica4.application.dao;
    exports com.pratica4.application.models;
}