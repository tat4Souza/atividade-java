package com.pratica4.application.dao;

import com.pratica4.application.factory.ConnectionFactory;
import com.pratica4.application.models.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private Connection conn;

    public ClienteDAO() {
        this.conn = new ConnectionFactory().getConnection();
    }

    public boolean addClient(Cliente cliente) {
        String sql = "INSERT INTO cliente(nome, cpf, data_nascimento, telefone, endereco, bairro, cidade, estado, cep, status) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, cliente.getData_nascimento());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEndereco());
            stmt.setString(6, cliente.getBairro());
            stmt.setString(7, cliente.getCidade());
            stmt.setString(8, cliente.getEstado());
            stmt.setString(9, cliente.getCep());
            stmt.setBoolean(10, true);

            stmt.execute();
            stmt.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    public List<Cliente> viewClients() {
        String sql = "SELECT * FROM cliente";
        List<Cliente> clients = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String cpf = rs.getString("cpf");
                Date data_nasc = rs.getDate("data_nascimento");
                String telefone = rs.getString("telefone");
                String endereco = rs.getString("endereco");
                String bairro = rs.getString("bairro");
                String cidade = rs.getString("cidade");
                String estado = rs.getString("estado");
                String cep = rs.getString("cep");
                Boolean status = rs.getBoolean("status");

                Cliente cliente = new Cliente(nome, cpf, data_nasc, telefone, endereco, bairro, cidade, estado, cep, status);
                clients.add(cliente);
                cliente.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar clientes: " + e.getMessage());
        }

        return clients;
    }

    public void alterClient(Cliente cliente, Integer id) {
        String sql = "UPDATE cliente SET nome = ?, cpf = ?, data_nascimento = ?, telefone = ?, endereco = ?, bairro = ?, cidade = ?, estado = ?, cep = ? WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setDate(3, cliente.getData_nascimento());
            stmt.setString(4, cliente.getTelefone());
            stmt.setString(5, cliente.getEndereco());
            stmt.setString(6, cliente.getBairro());
            stmt.setString(7, cliente.getCidade());
            stmt.setString(8, cliente.getEstado());
            stmt.setString(9, cliente.getCep());
            stmt.setInt(10, id);

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar cliente: " + e.getMessage());
        }
    }

    public void deleteClient(Integer id) {
        String sql = "UPDATE cliente SET status = false WHERE id = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desativar cliente: " + e.getMessage());
        }
    }

}
