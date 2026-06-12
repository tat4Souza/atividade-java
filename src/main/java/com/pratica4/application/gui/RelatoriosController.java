package com.pratica4.application.gui;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.io.IOException;

public class RelatoriosController {
    @FXML private JFXTextArea reportsView;
    @FXML private JFXButton btnSaveReport;
    @FXML private JFXButton btnBirthDayReport;
    @FXML private VBox reportsContainer;
    @FXML private Label messageLabel;
    @FXML private HBox entriesContainer;

    @FXML private TextField monthField;
    @FXML private TextField yearField;

    String result = "sucesso";


    public void initialize() {
        setVisibilityFalse(reportsView);
        setVisibilityFalse(btnSaveReport);
        setVisibilityFalse(entriesContainer);

        messageLabel.setText("Nenhum relatório gerado ainda.");
        reportsView.setEditable(false);
    }


    public void handleClientsAnimalsReport() {
        setVisibilityFalse(entriesContainer);


        if (result.equalsIgnoreCase("sucesso")) {
            setVisibilityFalse(messageLabel);

            // Colcoar o texto gerado para o relatório aqui
            reportsView.setText("--- Relatório: Todos os clientes e seus animais ---");

            setVisibilityTrue(reportsView);
            setVisibilityTrue(btnSaveReport);
        } else {
            messageLabel.setText("Erro ao gerar relatório");
            setVisibilityTrue(messageLabel);
            setVisibilityFalse(reportsView);
            setVisibilityFalse(btnSaveReport);
        }

        System.out.println("Todos os clientes e seus animais");
    }

    public void handleAnimalsMonthReport() {
        setVisibilityFalse(reportsView);
        setVisibilityFalse(btnSaveReport);
        messageLabel.setText("Insira o mês e ano e clique em Gerar.");
        setVisibilityTrue(messageLabel);

        setVisibilityTrue(entriesContainer);
        btnBirthDayReport.setOnAction(this::handleAnimalsMonth);

        System.out.println("Preparando filtro: Animais aniversariantes por mês");
    }

    private void handleAnimalsMonth(ActionEvent actionEvent) {
        if (result.equalsIgnoreCase("sucesso")) {
            setVisibilityFalse(messageLabel);

            reportsView.setText("--- Relatório: Animais aniversariantes do mês " + monthField.getText() + " ---");

            setVisibilityTrue(reportsView);
            setVisibilityTrue(btnSaveReport);
        } else {
            messageLabel.setText("Erro ao gerar relatório");
            setVisibilityTrue(messageLabel);
        }
    }

    public void handleClientsMonthReport() {
        setVisibilityFalse(reportsView);
        setVisibilityFalse(btnSaveReport);
        messageLabel.setText("Insira o mês e ano e clique em Gerar.");
        setVisibilityTrue(messageLabel);

        setVisibilityTrue(entriesContainer);
        btnBirthDayReport.setOnAction(this::handleClientsMonth);

        System.out.println("Preparando filtro: Clientes aniversariantes por mês");
    }

    private void handleClientsMonth(ActionEvent actionEvent) {
        if (result.equalsIgnoreCase("sucesso")) {
            setVisibilityFalse(messageLabel);

            reportsView.setText("--- Relatório: Clientes aniversariantes do mês " + monthField.getText() + " ---");

            setVisibilityTrue(reportsView);
            setVisibilityTrue(btnSaveReport);
        } else {
            messageLabel.setText("Erro ao gerar relatório");
            setVisibilityTrue(messageLabel);
        }
    }

    private void setVisibilityFalse(Node node) {
        node.setVisible(false);
        node.setManaged(false);
    }

    private void setVisibilityTrue(Node node) {
        node.setVisible(true);
        node.setManaged(true);
    }

    @FXML
    public void handleSaveReport() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Relatório");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivos de Texto (*.txt)", "*.txt"));
        fileChooser.setInitialFileName("relatorio.txt");

        Stage stage = (Stage) reportsView.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.println(reportsView.getText());
                messageLabel.setText("Relatório salvo com sucesso!");
                setVisibilityTrue(messageLabel);
            } catch (IOException ex) {
                messageLabel.setText("Erro ao salvar o arquivo no dispositivo.");
                setVisibilityTrue(messageLabel);
            }
        }
    }
}