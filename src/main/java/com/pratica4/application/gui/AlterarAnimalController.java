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

public class AlterarAnimalController extends BaseAlterarController<Animal>{
    @FXML private DatePicker dateField;
    @FXML private TextField nameField;
    @FXML private TextField colorField;
    @FXML private ComboBox<String> sexField;
    @FXML private ComboBox<Cliente> clientField;
    @FXML private ComboBox<Raca> raceField;
    @FXML private TextArea obsField;
    @FXML private Label errorLabel;

    public AlterarAnimalController(Animal animal) {
        super(animal);
    }

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

        nameField.setText(entity.getNome());
        dateField.setValue(entity.getData_nascimento().toLocalDate());
        sexField.getSelectionModel().select(entity.getSexo());
        colorField.setText(entity.getCor());
        obsField.setText(entity.getObservacoes());


        super.initializeBase();
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
            List<Cliente> clients = loadClientsTask.getValue();
            clientField.setItems(FXCollections.observableArrayList(loadClientsTask.getValue()));
            clients.stream()
                    .filter(c -> c.getId() == entity.getId_cliente())
                    .findFirst()
                    .ifPresent(clienteDoAnimal -> clientField.getSelectionModel().select(clienteDoAnimal));
        }));

        loadRacesTask.setOnSucceeded(e -> Platform.runLater(() -> {
            List<Raca> races = loadRacesTask.getValue();
            raceField.setItems(FXCollections.observableArrayList(loadRacesTask.getValue()));
            races.stream()
                    .filter(c -> c.getId() == entity.getRaca().getId())
                    .findFirst()
                    .ifPresent(racaDoAnimal -> raceField.getSelectionModel().select(racaDoAnimal));
        }));

        new Thread(loadClientsTask).start();
        new Thread(loadRacesTask).start();
    }

    @Override
    protected boolean validateFields() {
        if (nameField.getText().isEmpty() ||
                colorField.getText().isEmpty() ||
                dateField.getValue() == null ||
                sexField.getValue() == null ||
                clientField.getValue() == null) {

            errorLabel.setText("Por favor, preencha todos os campos obrigatórios!");
            return false;
        }
        return true;
    }

    @Override
    protected void updateItemDAO() {
        Animal animalAlterado = new Animal(
                nameField.getText(),
                java.sql.Date.valueOf(dateField.getValue()),
                sexField.getValue(),
                colorField.getText(),
                obsField.getText(),
                clientField.getValue().getId(),
                raceField.getValue(),
                true
        );

        new AnimalDAO().alterAnimal(animalAlterado, entity.getId());
    }

}
