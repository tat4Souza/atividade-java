package com.pratica4.application.gui;

import com.jfoenix.controls.JFXButton;
import com.pratica4.application.MainApplication;
import com.pratica4.application.dao.ClienteDAO;
import com.pratica4.application.models.Cliente;
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

public class AlterarClienteController extends BaseAlterarController<Cliente> {
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
    @FXML private JFXButton btnSubmmit;
    @FXML private HBox buttonsContainer;
    @FXML private Label title;


    public AlterarClienteController(Cliente cliente) {
        super(cliente);
    }

    @FXML
    public void initialize() {
        FieldsFormmaters fm = new FieldsFormmaters();
        fm.textFormatter(cpfField, 11, fm::applyCPFMask);
        fm.textFormatter(cepField, 8, fm::applyCEPMask);
        fm.textFormatter(telephoneField, 11, fm::applyPhoneMask);

        nameField.setText(entity.getNome());
        cpfField.setText(entity.getCpf());
        dateField.setValue(entity.getData_nascimento().toLocalDate());
        telephoneField.setText(entity.getTelefone());
        addressField.setText(entity.getEndereco());
        neightbourhoodField.setText(entity.getBairro());
        cityField.setText(entity.getCidade());
        stateField.setText(entity.getEstado());
        cepField.setText(entity.getCep());

        super.initializeBase();
    }

    @Override
    protected boolean validateFields() {
        if (nameField.getText().isEmpty() || neightbourhoodField.getText().isEmpty() ||
                cepField.getText().isEmpty() || cityField.getText().isEmpty() ||
                cpfField.getText().isEmpty() || addressField.getText().isEmpty() ||
                stateField.getText().isEmpty() || telephoneField.getText().isEmpty() ||
                dateField.getValue() == null) {

            errorLabel.setText("Por favor, não deixe o cliente sem informações!");
            return false;
        }
        return true;
    }

    @Override
    protected void updateItemDAO() {
        Cliente clienteAlterado = new Cliente(
                nameField.getText(),
                cpfField.getText(),
                java.sql.Date.valueOf(dateField.getValue()),
                telephoneField.getText(),
                addressField.getText(),
                neightbourhoodField.getText(),
                cityField.getText(),
                stateField.getText(),
                cepField.getText(),
                true
        );

        new ClienteDAO().alterClient(clienteAlterado, entity.getId());
    }
}
