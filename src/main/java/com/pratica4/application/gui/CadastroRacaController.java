package com.pratica4.application.gui;

import com.jfoenix.controls.JFXComboBox;
import com.pratica4.application.dao.RacaDAO;
import com.pratica4.application.models.Raca;
import com.pratica4.application.utils.FieldsFormmaters;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CadastroRacaController {
    @FXML private TextField nameField;
    @FXML private JFXComboBox<String> typeField;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        FieldsFormmaters fm = new FieldsFormmaters();
        fm.onlyText(nameField);
        typeField.setItems(FXCollections.observableArrayList("Cachorro", "Gato"));
    }

    public void handleSubmmit() {
        createRace();
    }

    private void createRace() {
        if (nameField.getText().isEmpty() || typeField.getValue() == null) {
            errorLabel.setText("Por favor, preencha todos os campos antes de enviar o formulário!");
            return;
        }

        Raca raca = new Raca(nameField.getText(), typeField.getValue(), true);

        RacaDAO dao = new RacaDAO();
        if (dao.addRaca(raca)) {
            errorLabel.setText("Raça cadastrada com Sucesso!");
            clearFields();
        } else {
            errorLabel.setText("Erro ao adicionar raça ao banco de dados!");
        }
    }

    private void clearFields() {
        nameField.setText("");
        typeField.setValue(null);
    }

}
