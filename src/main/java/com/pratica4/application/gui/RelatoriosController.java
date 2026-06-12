package com.pratica4.application.gui;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import com.pratica4.application.dao.RelatorioDAO; // Importação do seu DAO

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class RelatoriosController {
    @FXML private JFXTextArea reportsView;
    @FXML private JFXButton btnSaveReport;
    @FXML private JFXButton btnBirthDayReport;
    @FXML private VBox reportsContainer;
    @FXML private Label messageLabel;
    @FXML private HBox entriesContainer;

    @FXML private TextField monthField;
    @FXML private TextField yearField;

    // Instância do DAO para buscar os dados
    private RelatorioDAO relatorioDAO;

    public void initialize() {
        relatorioDAO = new RelatorioDAO(); // Inicializa a conexão com o banco

        setVisibilityFalse(reportsView);
        setVisibilityFalse(btnSaveReport);
        setVisibilityFalse(entriesContainer);

        messageLabel.setText("Nenhum relatório gerado ainda.");
        reportsView.setEditable(false);
    }

    // 7.1 - Todos os clientes e seus animais
    public void handleClientsAnimalsReport() {
        setVisibilityFalse(entriesContainer);

        try {
            // Busca o texto formatado direto do banco
            String reportText = relatorioDAO.generateClientsAndAnimalsReport();
            
            reportsView.setText(reportText);
            setVisibilityFalse(messageLabel);
            setVisibilityTrue(reportsView);
            setVisibilityTrue(btnSaveReport);
            
            System.out.println("Relatório de todos os clientes e animais gerado.");
        } catch (Exception e) {
            messageLabel.setText("Erro ao gerar relatório: " + e.getMessage());
            setVisibilityTrue(messageLabel);
            setVisibilityFalse(reportsView);
            setVisibilityFalse(btnSaveReport);
        }
    }

    // 7.2 - Animais aniversariantes
    public void handleAnimalsMonthReport() {
        setVisibilityFalse(reportsView);
        setVisibilityFalse(btnSaveReport);
        messageLabel.setText("Insira o mês e ano e clique em Gerar.");
        setVisibilityTrue(messageLabel);

        setVisibilityTrue(entriesContainer);
        
        // Define qual ação o botão 'Gerar' vai ter quando essa opção for clicada
        btnBirthDayReport.setOnAction(this::handleAnimalsMonth);

        System.out.println("Preparando filtro: Animais aniversariantes por mês");
    }

    private void handleAnimalsMonth(ActionEvent actionEvent) {
        try {
            // Valida se o usuário digitou números
            int month = Integer.parseInt(monthField.getText().trim());
            int year = Integer.parseInt(yearField.getText().trim());

            String reportText = relatorioDAO.generateAnimalBirthdaysReport(month, year);
            
            reportsView.setText(reportText);
            setVisibilityFalse(messageLabel);
            setVisibilityTrue(reportsView);
            setVisibilityTrue(btnSaveReport);

        } catch (NumberFormatException e) {
            messageLabel.setText("Erro: Mês e Ano devem ser números inteiros.");
            setVisibilityTrue(messageLabel);
        } catch (Exception e) {
            messageLabel.setText("Erro ao gerar relatório: " + e.getMessage());
            setVisibilityTrue(messageLabel);
        }
    }

    // 7.3 - Clientes aniversariantes
    public void handleClientsMonthReport() {
        setVisibilityFalse(reportsView);
        setVisibilityFalse(btnSaveReport);
        messageLabel.setText("Insira o mês e ano e clique em Gerar.");
        setVisibilityTrue(messageLabel);

        setVisibilityTrue(entriesContainer);
        
        // Define qual ação o botão 'Gerar' vai ter quando essa opção for clicada
        btnBirthDayReport.setOnAction(this::handleClientsMonth);

        System.out.println("Preparando filtro: Clientes aniversariantes por mês");
    }

    private void handleClientsMonth(ActionEvent actionEvent) {
        try {
            // Valida se o usuário digitou números
            int month = Integer.parseInt(monthField.getText().trim());
            int year = Integer.parseInt(yearField.getText().trim());

            String reportText = relatorioDAO.generateClientBirthdaysReport(month, year);
            
            reportsView.setText(reportText);
            setVisibilityFalse(messageLabel);
            setVisibilityTrue(reportsView);
            setVisibilityTrue(btnSaveReport);

        } catch (NumberFormatException e) {
            messageLabel.setText("Erro: Mês e Ano devem ser números inteiros.");
            setVisibilityTrue(messageLabel);
        } catch (Exception e) {
            messageLabel.setText("Erro ao gerar relatório: " + e.getMessage());
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

    // Ação do Botão "Salvar Relatório" que grava o que está no JFXTextArea para o TXT
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