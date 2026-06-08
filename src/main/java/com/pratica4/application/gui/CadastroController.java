package com.pratica4.application.gui;

import com.pratica4.application.dao.ClienteDAO;
import com.pratica4.application.models.Cliente;
import com.pratica4.application.utils.FieldsFormmaters;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.function.UnaryOperator;

public class CadastroController {
    @FXML private TextField addressField;
    @FXML private TextField cepField;
    @FXML private TextField cityField;
    @FXML private TextField cpfField;
    @FXML private DatePicker dateField;
    @FXML private TextField nameField;
    @FXML private TextField neightbourhoodField;
    @FXML private TextField stateField;
    @FXML private TextField telephoneField;
    @FXML private Label errorLabel;
    @FXML private Label title;

    @FXML
    public void initialize() {
        FieldsFormmaters fm = new FieldsFormmaters();
        // CPF FORMATTER
        fm.textFormatter(cpfField, 11, fm::applyCPFMask);

        // CEP FORMATTER
        fm.textFormatter(cepField, 8, fm::applyCEPMask);

        // PHONE FORMATTER
        fm.textFormatter(telephoneField, 11, fm::applyPhoneMask);

        fm.onlyText(addressField);
        fm.onlyText(nameField);
        fm.onlyText(neightbourhoodField);
        fm.onlyText(cityField);
        fm.stateFormmater(stateField);

        title.setText("Cadastrar Cliente");
    }

    public void handleSubmmit() {
        createClient();
    }

    private void createClient() {
        if (nameField.getText().isEmpty() || neightbourhoodField.getText().isEmpty() || cepField.getText().isEmpty() || cityField.getText().isEmpty() || cpfField.getText().isEmpty() || addressField.getText().isEmpty() || stateField.getText().isEmpty() || telephoneField.getText().isEmpty() || dateField.getValue() == null) {
            errorLabel.setText("Por favor, preencha todos os campos antes de enviar o formulário!");
            return;
        }

        Cliente cliente = new Cliente(nameField.getText(), cpfField.getText(), java.sql.Date.valueOf(dateField.getValue()), telephoneField.getText(), addressField.getText(), neightbourhoodField.getText(), cityField.getText(), stateField.getText(), cepField.getText(), true);

        ClienteDAO dao = new ClienteDAO();

        if (dao.addClient(cliente)) {
            errorLabel.setText("Cliente cadastrado com sucesso!");
            clearFields();
        } else {
            errorLabel.setText("Erro ao adicionar cliente no banco de dados!");
        }


    }

    private void clearFields() {
        addressField.setText("");
        cepField.setText("");
        cityField.setText("");
        cpfField.setText("");
        dateField.setValue(null);
        nameField.setText("");
        neightbourhoodField.setText("");
        stateField.setText("");
        telephoneField.setText("");
    }

}
