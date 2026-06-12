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
        // 1. Verifica se tem campos vazios
        if (nameField.getText().isEmpty() || neightbourhoodField.getText().isEmpty() || cepField.getText().isEmpty() || cityField.getText().isEmpty() || cpfField.getText().isEmpty() || addressField.getText().isEmpty() || stateField.getText().isEmpty() || telephoneField.getText().isEmpty() || dateField.getValue() == null) {
            errorLabel.setText("Por favor, preencha todos os campos antes de enviar o formulário!");
            return;
        }

        // 2. Verifica se o CPF é matematicamente válido
        if (!isCpfValido(cpfField.getText())) {
            errorLabel.setText("O CPF digitado é INVÁLIDO!");
            return; // Interrompe o cadastro
        }

        // 3. Se tudo estiver certo, prossegue com a criação
        Cliente cliente = new Cliente(nameField.getText(), cpfField.getText(), java.sql.Date.valueOf(dateField.getValue()), telephoneField.getText(), addressField.getText(), neightbourhoodField.getText(), cityField.getText(), stateField.getText(), cepField.getText(), true);

        ClienteDAO dao = new ClienteDAO();

        if (dao.addClient(cliente)) {
            errorLabel.setText("Cliente cadastrado com sucesso!");
            clearFields();
        } else {
            errorLabel.setText("Erro ao adicionar cliente no banco de dados!");
        }
    }

    // --- NOVO MÉTODO: Validação de CPF ---
    private boolean isCpfValido(String cpf) {
        // Remove tudo que não for número (pontos e traços da máscara)
        cpf = cpf.replaceAll("\\D", "");

        // CPFs devem ter 11 dígitos. CPFs com números repetidos passam no cálculo, então precisamos bloqueá-los aqui
        if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
            return false;
        }

        try {
            int soma = 0;
            int peso = 10;

            // Cálculo do primeiro dígito verificador
            for (int i = 0; i < 9; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
            }

            int resto = 11 - (soma % 11);
            int primeiroDigito = (resto == 10 || resto == 11) ? 0 : resto;

            if (primeiroDigito != Character.getNumericValue(cpf.charAt(9))) {
                return false;
            }

            // Cálculo do segundo dígito verificador
            soma = 0;
            peso = 11;
            for (int i = 0; i < 10; i++) {
                soma += Character.getNumericValue(cpf.charAt(i)) * peso--;
            }

            resto = 11 - (soma % 11);
            int segundoDigito = (resto == 10 || resto == 11) ? 0 : resto;

            return segundoDigito == Character.getNumericValue(cpf.charAt(10));

        } catch (Exception e) {
            return false;
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