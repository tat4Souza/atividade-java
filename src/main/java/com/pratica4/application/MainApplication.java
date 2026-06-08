package com.pratica4.application;

import com.pratica4.application.factory.ConnectionFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;

public class MainApplication extends Application {
    public static String GLOBAL_CSS;

    @Override
    public void init() {
        GLOBAL_CSS = Objects.requireNonNull(getClass().getResource("style/main.css")).toExternalForm();
        Region warm_upRegion = new Region();
        Scene warm_upScene = new Scene(warm_upRegion);
        warm_upScene.getStylesheets().add(GLOBAL_CSS);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader root = new FXMLLoader(MainApplication.class.getResource("views/layout-page.fxml"));

        Scene scene = new Scene(root.load(), 1280, 720);
        stage.setScene(scene);
        scene.getStylesheets().add(GLOBAL_CSS);
        stage.setTitle("CRUD de Clientes");
        Image icon = new Image(Objects.requireNonNull(MainApplication.class.getResourceAsStream("img/luna-logo.png")));
        stage.getIcons().add(icon);
        stage.show();
        System.out.println("Olá, professora! Curiosidade: o ícone é minha gata!");
    }

    public static void main(String[] args) throws SQLException {
        Application.launch(MainApplication.class, args);
    }
}
