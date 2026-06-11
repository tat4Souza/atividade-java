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
import com.pratica4.application.models.Raca;

public class AnimalDAO {
    private Connection conn;

    public AnimalDAO() {
        this.conn = new ConnectionFactory().getConnection();
    }

    public boolean addAnimal(Animal animal) {
        String sql = "INSERT INTO animal(nome, data_nascimento, sexo, cor, observacoes, id_cliente, id_raca, status) VALUES(?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, animal.getNome());
            stmt.setDate(2, animal.getData_nascimento());
            stmt.setString(3, animal.getSexo());
            stmt.setString(4, animal.getCor());
            stmt.setString(5, animal.getObservacoes());
            stmt.setInt(6, animal.getId_cliente());
            stmt.setInt(7, animal.getId_raca());
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
        String sql = "SELECT a.*, r.nome_raca, r.tipo_animal FROM animal a INNER JOIN raca r ON a.id_raca = r.id_raca";
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
                int id_cliente = rs.getInt("id_cliente");
                Boolean status = rs.getBoolean("status");

                int id_raca = rs.getInt("id_raca");
                String nome_raca = rs.getString("nome_raca");
                String tipo_raca = rs.getString("tipo_animal");
                Boolean status_raca = rs.getBoolean("status");

                Raca raca = new Raca(nome_raca, tipo_raca, true);
                raca.setId(id_raca);

                Animal animal = new Animal( nome, data_nasc, sexo, cor, obs, id_cliente, raca, status);
                animals.add(animal);
                animal.setId(id);

            }

        } catch(SQLException e) {
            throw new RuntimeException("Erro ao carregar animais: " + e.getMessage());
        }

        return animals;
    }

    public void alterAnimal(Animal animal, Integer id) {
        String sql = "UPDATE animal SET nome = ?, data_nascimento = ?, sexo = ?, cor = ?, observacoes = ?, id_cliente = ?, id_raca = ? WHERE id_animal = ?";

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
