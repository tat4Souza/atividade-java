package com.pratica4.application.gui;

import com.jfoenix.controls.JFXButton;
import com.pratica4.application.MainApplication;
import com.pratica4.application.dao.ClienteDAO;
import com.pratica4.application.models.Cliente;
import com.pratica4.application.models.Identificador;
import com.pratica4.application.utils.FieldsFormmaters;
import com.pratica4.application.utils.NavigationManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.Objects;

public abstract class BaseAlterarController<OBJ extends Identificador> {

    protected OBJ entity;


    protected final NavigationManager nm = new NavigationManager();
    private VBox container;

    @FXML protected Label title;
    @FXML protected Label errorLabel;
    @FXML protected JFXButton btnSubmmit;
    @FXML protected HBox buttonsContainer;

    public BaseAlterarController(OBJ entity) {
        this.entity = entity;
    }

    protected void initializeBase() {
        btnSubmmit.setText("Salvar Alterações");
        title.setText("Editar Informações sobre " + entity.getNome());

        JFXButton btnCancel = new JFXButton("Cancelar");
        btnCancel.getStyleClass().addAll("btn", "btn--cancel");
        btnCancel.setOnAction(event -> navigateToConsulta());
        buttonsContainer.getChildren().add(btnCancel);
    }

    @FXML
    public void handleSubmmit()  {
        try {
            if(!validateFields()) {
                return;
            }
            updateItemDAO();
            navigateToConsulta();

        } catch (Exception e) {
            errorLabel.setText("Erro ao salvar alterações " + e.getMessage());
            e.printStackTrace();
        }
    }

    protected abstract boolean validateFields();
    protected abstract void updateItemDAO() throws Exception;

    private void navigateToConsulta() {
        try {
            if (container == null) {
                container = (VBox) buttonsContainer.getScene().getRoot().lookup("#container");
            }
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(MainApplication.class.getResource("views/consulta-page.fxml")));
            nm.navigateToPage(container, loader.load());
        } catch(IOException e) {
            throw new RuntimeException("Erro ao navegar para consulta: " + e.getMessage());
        }
    }
}
