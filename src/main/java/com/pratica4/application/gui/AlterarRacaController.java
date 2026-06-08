package com.pratica4.application.gui;

import com.jfoenix.controls.JFXComboBox;
import com.pratica4.application.dao.AnimalDAO;
import com.pratica4.application.dao.RacaDAO;
import com.pratica4.application.models.Animal;
import com.pratica4.application.models.Raca;
import com.pratica4.application.utils.FieldsFormmaters;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AlterarRacaController extends BaseAlterarController<Raca> {
    @FXML private TextField nameField;
    @FXML private JFXComboBox<String> typeField;
    @FXML private Label errorLabel;

    public AlterarRacaController(Raca raca) {
        super(raca);
    }

    @FXML
    public void initialize() {
        FieldsFormmaters fm = new FieldsFormmaters();
        fm.onlyText(nameField);
        typeField.setItems(FXCollections.observableArrayList("Cachorro", "Gato"));

        nameField.setText(entity.getNome());
        typeField.getSelectionModel().select(entity.getTipo());

        super.initializeBase();
    }

    @Override
    protected boolean validateFields() {
        if (nameField.getText().isEmpty() || typeField.getValue() == null) {
            errorLabel.setText("Por favor, não deixe os campos de raça vazios!");
            return false;
        }
        return true;
    }

    @Override
    protected void updateItemDAO() {
        Raca racaAlterada = new Raca(
                nameField.getText(),
                typeField.getValue(),
                true
        );

        new RacaDAO().alterRaca(racaAlterada, entity.getId());
    }

}
