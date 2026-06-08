package com.pratica4.application.gui;

import com.jfoenix.controls.JFXButton;
import com.pratica4.application.MainApplication;
import com.pratica4.application.utils.NavigationManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;
import java.util.function.UnaryOperator;

public class LayoutController {
    @FXML VBox container;
    @FXML JFXButton btnCadastro;
    @FXML JFXButton btnConsulta;
    @FXML HBox navContainer;

    NavigationManager nm = new NavigationManager();

    @FXML
    public void initialize() throws IOException {
        handleSignPage();
    }

    @FXML
    private void handleSignPage() throws IOException {
        FXMLLoader loader =  new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource("views/cadastro-cliente-page.fxml")));
        CadastroController controller = new CadastroController();
        loader.setController(controller);

        Parent root = loader.load();
        nm.navigateToPage(container, root);
        btnCadastro.getStyleClass().add("btn__nav--active");
        btnConsulta.getStyleClass().remove("btn__nav--active");

        navContainer.setVisible(true);
        navContainer.setManaged(true);

    }

    @FXML
    private void handleClientPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                    MainApplication.class.getResource("views/cadastro-cliente-page.fxml")));
            CadastroController controller = new CadastroController();
            loader.setController(controller);

            Parent root = loader.load();
            nm.navigateToPage(container, root);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleAnimalPage() {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                    MainApplication.class.getResource("views/cadastro-animal-page.fxml")));

            CadastroAnimalController controller = new CadastroAnimalController();
            loader.setController(controller);

            Parent root = loader.load();
            nm.navigateToPage(container, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleRacePage() {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(
                    MainApplication.class.getResource("views/cadastro-raca-page.fxml")));

            CadastroRacaController controller = new CadastroRacaController();
            loader.setController(controller);

            Parent root = loader.load();
            nm.navigateToPage(container, root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private void handleConsultPage() throws IOException {
        FXMLLoader loader =  new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource("views/consulta-page.fxml")));
        nm.navigateToPage(container, loader.load());
        btnConsulta.getStyleClass().add("btn__nav--active");
        btnCadastro.getStyleClass().remove("btn__nav--active");
        navContainer.setVisible(false);
        navContainer.setManaged(false);
    }


}
