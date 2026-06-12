package com.pratica4.application.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.pratica4.application.factory.ConnectionFactory;
import com.pratica4.application.models.Animal;
import com.pratica4.application.models.Cliente;
import com.pratica4.application.models.Raca;

public class AnimalDAO {
    private Connection conn;

    public AnimalDAO() {
        this.conn = new ConnectionFactory().getConnection();
    }

    public boolean addAnimal(Animal animal) {
        String sql = "INSERT INTO animal(nome, data_nascimento, sexo, cor, observacoes, fk_id_cliente, fk_animal_raca, status) VALUES(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, animal.getNome());
            stmt.setDate(2, animal.getData_nascimento());
            stmt.setString(3, animal.getSexo());
            stmt.setString(4, animal.getCor());
            stmt.setString(5, animal.getObservacoes());
            stmt.setInt(6, animal.getId_cliente());
            stmt.setInt(7, animal.getRaca().getId());
            stmt.setBoolean(8, true);

            stmt.execute();
            stmt.close();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
public List<Animal> viewAnimals() {
        // 1. Query alterada: Adicionado o INNER JOIN com a tabela cliente
        String sql = "SELECT a.*, r.nome_raca, r.tipo_animal, c.nome AS nome_cliente, c.cpf AS cpf_cliente " +
                     "FROM animal a " +
                     "INNER JOIN raca r ON a.fk_animal_raca = r.id_raca " +
                     "INNER JOIN cliente c ON a.fk_id_cliente = c.id_cliente";
        
        List<Animal> animals = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_animal");
                String nome = rs.getString("nome");
                Date data_nasc = rs.getDate("data_nascimento");
                String sexo = rs.getString("sexo");
                String cor = rs.getString("cor");
                String obs = rs.getString("observacoes");
                int id_cliente = rs.getInt("fk_id_cliente");
                Boolean status = rs.getBoolean("status");

                int id_raca = rs.getInt("fk_animal_raca");
                String nome_raca = rs.getString("nome_raca");
                String tipo_raca = rs.getString("tipo_animal");
                // Removida a linha duplicada 'Boolean status_raca = rs.getBoolean("status");' que poderia causar conflitos.

                Raca raca = new Raca(nome_raca, tipo_raca, true);
                raca.setId(id_raca);

                Animal animal = new Animal(nome, data_nasc, sexo, cor, obs, id_cliente, raca, status);
                animal.setId(id);

                // 2. Criação do cliente e injeção no animal
                // Usando o construtor do Cliente com nulls para os dados desnecessários na pesquisa
                Cliente cliente = new Cliente(
                    rs.getString("nome_cliente"), 
                    rs.getString("cpf_cliente"), 
                    null, null, null, null, null, null, null, true
                );
                cliente.setId(id_cliente);
                
                animal.setCliente(cliente); // A MÁGICA ACONTECE AQUI

                animals.add(animal);
            }

        } catch(SQLException e) {
            throw new RuntimeException("Erro ao carregar animais: " + e.getMessage());
        }

        return animals;
    }

    public void alterAnimal(Animal animal, Integer id) {
        String sql = "UPDATE animal SET nome = ?, data_nascimento = ?, sexo = ?, cor = ?, observacoes = ?, fk_id_cliente = ?, fk_animal_raca = ? WHERE id_animal = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, animal.getNome());
            stmt.setDate(2, animal.getData_nascimento());
            stmt.setString(3, animal.getSexo());
            stmt.setString(4, animal.getCor());
            stmt.setString(5, animal.getObservacoes());
            stmt.setInt(6, animal.getId_cliente());
            stmt.setInt(7, animal.getRaca().getId());
            stmt.setInt(8, id);

            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao editar animal: " + e.getMessage());
        }
    }

    public void deleteAnimal(Integer id) {
        String sql = "UPDATE animal SET status = false WHERE id_animal = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desativar animal: " + e.getMessage());
        }
    }

    

}
