package com.pratica4.application.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.pratica4.application.factory.ConnectionFactory;

public class RelatorioDAO {
    
    private Connection conn;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public RelatorioDAO() {
        this.conn = new ConnectionFactory().getConnection();
    }

    public String generateClientsAndAnimalsReport() {
        String sql = "SELECT c.nome, c.cpf, a.nome AS nome_animal, r.nome_raca, a.data_nascimento " +
                     "FROM cliente c " +
                     "JOIN animal a ON c.id_cliente = a.fk_id_cliente " +
                     "JOIN raca r ON a.fk_animal_raca = r.id_raca " +
                     "ORDER BY c.nome, a.nome";

        StringBuilder sb = new StringBuilder();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            sb.append("=================================================================================\n");
            sb.append("                     RELATORIO: CLIENTES E SEUS ANIMAIS                          \n");
            sb.append("=================================================================================\n");
            sb.append(String.format("%-25s | %-14s | %-20s | %-15s | %-12s\n", "CLIENTE", "CPF", "ANIMAL", "RACA", "NASC. ANIMAL"));
            sb.append("---------------------------------------------------------------------------------\n");

            while (rs.next()) {
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String nomeAnimal = rs.getString("nome_animal");
                String nomeRaca = rs.getString("nome_raca");
                
                LocalDate dataNascAnimal = rs.getObject("data_nascimento", LocalDate.class);
                String dataFormatada = (dataNascAnimal != null) ? dataNascAnimal.format(fmt) : "Não informada";

                sb.append(String.format("%-25s | %-14s | %-20s | %-15s | %-12s\n", nome, cpf, nomeAnimal, nomeRaca, dataFormatada));
            }

            stmt.close();
            return sb.toString();

        } catch (SQLException e) {
            throw new RuntimeException("Erro banco de dados (Clientes e Animais): " + e.getMessage());
        }
    }

    public String generateAnimalBirthdaysReport(Integer month, Integer year) {
        String sql = "SELECT a.nome AS animal, c.nome AS cliente, c.telefone, a.data_nascimento " +
                     "FROM animal a " +
                     "JOIN cliente c ON a.fk_id_cliente = c.id_cliente " +
                     "WHERE MONTH(a.data_nascimento) = ? AND YEAR(a.data_nascimento) = ? " +
                     "ORDER BY DAY(a.data_nascimento)";

        StringBuilder sb = new StringBuilder();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            
            ResultSet rs = stmt.executeQuery();

            sb.append("========================================================================\n");
            sb.append(String.format("             RELATORIO: ANIMAIS ANIVERSARIANTES (%02d/%d)            \n", month, year));
            sb.append("========================================================================\n");
            sb.append(String.format("%-20s | %-25s | %-15s | %-12s\n", "ANIMAL", "TUTOR (CLIENTE)", "TELEFONE", "DATA NASC."));
            sb.append("------------------------------------------------------------------------\n");

            while (rs.next()) {
                String animal = rs.getString("animal");
                String cliente = rs.getString("cliente");
                String telefone = rs.getString("telefone") != null ? rs.getString("telefone") : "Sem telefone";
                
                LocalDate dataNascAnimal = rs.getObject("data_nascimento", LocalDate.class);
                String dataFormatada = (dataNascAnimal != null) ? dataNascAnimal.format(fmt) : "Não informada";

                sb.append(String.format("%-20s | %-25s | %-15s | %-12s\n", animal, cliente, telefone, dataFormatada));
            }

            stmt.close();
            return sb.toString();

        } catch (SQLException e) {
            throw new RuntimeException("Erro banco de dados (Aniversários Animais): " + e.getMessage());
        }
    }

    public String generateClientBirthdaysReport(Integer month, Integer year) {
        String sql = "SELECT nome, cpf, data_nascimento, telefone " +
                     "FROM cliente " +
                     "WHERE MONTH(data_nascimento) = ? AND YEAR(data_nascimento) = ? " +
                     "ORDER BY DAY(data_nascimento)";

        StringBuilder sb = new StringBuilder();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, month);
            stmt.setInt(2, year);
            
            ResultSet rs = stmt.executeQuery();

            sb.append("========================================================================\n");
            sb.append(String.format("            RELATORIO: CLIENTES ANIVERSARIANTES (%02d/%d)            \n", month, year));
            sb.append("========================================================================\n");
            sb.append(String.format("%-25s | %-14s | %-12s | %-15s\n", "CLIENTE", "CPF", "DATA NASC.", "TELEFONE"));
            sb.append("------------------------------------------------------------------------\n");

            while (rs.next()) {
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                String telefone = rs.getString("telefone") != null ? rs.getString("telefone") : "Sem telefone";
                
                LocalDate dataNascCliente = rs.getObject("data_nascimento", LocalDate.class);
                String dataFormatada = (dataNascCliente != null) ? dataNascCliente.format(fmt) : "Não informada";

                sb.append(String.format("%-25s | %-14s | %-12s | %-15s\n", nome, cpf, dataFormatada, telefone));
            }

            stmt.close();
            return sb.toString();

        } catch (SQLException e) {
            throw new RuntimeException("Erro banco de dados (Aniversários Clientes): " + e.getMessage());
        }
    }
}