package com.pratica4.application.gui;

import com.pratica4.application.dao.AnimalDAO;
import com.pratica4.application.dao.ClienteDAO;
import com.pratica4.application.dao.RacaDAO;
import com.pratica4.application.models.Animal;
import com.pratica4.application.models.Cliente;
import com.pratica4.application.models.Raca;
import com.pratica4.application.utils.FieldsFormmaters;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.util.List;

public class CadastroAnimalController {
    @FXML private DatePicker dateField;
    @FXML private TextField nameField;
    @FXML private TextField colorField;
    @FXML private ComboBox<String> sexField;
    @FXML private ComboBox<Cliente> clientField;
    @FXML private ComboBox<Raca> raceField;
    @FXML private TextArea obsField;
    @FXML private Label errorLabel;


    @FXML
    public void initialize() {
        FieldsFormmaters fm = new FieldsFormmaters();

        fm.onlyText(colorField);
        fm.onlyText(nameField);
        sexField.setItems(FXCollections.observableArrayList("M", "F"));

        clientField.setConverter(new StringConverter<>() {
            @Override
            public String toString(Cliente cliente) {
                return cliente != null ? cliente.getNome() : "";
            }
            @Override
            public Cliente fromString(String string) {
                return null;
            }
        });

        raceField.setConverter(new StringConverter<>() {
            @Override
            public String toString(Raca raca) {
                return raca != null ? raca.getNome() + " | " + raca.getTipo() : "";
            }
            @Override
            public Raca fromString(String string) {
                return null;
            }
        });

        loadComboBoxData();
    }

    public void handleSubmmit() {
        createAnimal();
    }

    private void loadComboBoxData() {
        Task<List<Cliente>> loadClientsTask = new Task<>() {
            @Override
            protected List<Cliente> call() throws Exception {
                return new ClienteDAO().viewClients();
            }
        };
        Task<List<Raca>> loadRacesTask = new Task<>() {
            @Override
            protected List<Raca> call() throws Exception {
                return new RacaDAO().viewRaca();
            }
        };

        loadClientsTask.setOnSucceeded(e -> Platform.runLater(() -> {
            clientField.setItems(FXCollections.observableArrayList(loadClientsTask.getValue()));
        }));

        loadRacesTask.setOnSucceeded(e -> Platform.runLater(() -> {
            raceField.setItems(FXCollections.observableArrayList(loadRacesTask.getValue()));
        }));

        new Thread(loadClientsTask).start();
        new Thread(loadRacesTask).start();
    }

    private void createAnimal() {
        if (nameField.getText().isEmpty() ||
                colorField.getText().isEmpty() ||
                dateField.getValue() == null ||
                sexField.getValue() == null ||
                clientField.getValue() == null) {

            errorLabel.setText("Por favor, preencha todos os campos obrigatórios!");
            return;
        }

        Cliente selectedClient = clientField.getValue();
        Raca selectedRace = raceField.getValue();
        String nome = nameField.getText();
        java.sql.Date dataNasc = java.sql.Date.valueOf(dateField.getValue());
        String sexo = sexField.getValue();
        String cor = colorField.getText();
        String obs = obsField.getText() != null ? obsField.getText() : "";
        int idCliente = selectedClient.getId();

        Animal animal = new Animal(nome, dataNasc, sexo, cor, obs, idCliente, selectedRace, true);

        AnimalDAO dao = new AnimalDAO();

        if (dao.addAnimal(animal)) {
            errorLabel.setText("Animal cadastrado com sucesso!");
            clearFields();
        } else {
            errorLabel.setText("Erro ao adicionar animal no banco de dados!");
        }
    }

    private void clearFields() {
        nameField.setText("");
        colorField.setText("");
        dateField.setValue(null);
        sexField.setValue(null);
        clientField.setValue(null);
        raceField.setValue(null);
        obsField.setText("");
    }


}
