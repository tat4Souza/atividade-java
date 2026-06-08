package com.pratica4.application.dao;

import com.pratica4.application.factory.ConnectionFactory;
import com.pratica4.application.models.Animal;
import com.pratica4.application.models.Raca;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalDAO {
    private Connection conn;

    public AnimalDAO() {
        this.conn = new ConnectionFactory().getConnection();
    }

    public boolean addAnimal(Animal animal) {
        String sql = "INSERT INTO animal(Nome, Data_Nascimento, Sexo, Cor, Observacoes, ID_Cliente, ID_Raca, Status) VALUES(?,?,?,?,?,?,?,?)";

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
        String sql = "SELECT a.*, r.Nome_Raca, r.Tipo_Animal FROM animal a INNER JOIN raca r ON a.ID_Raca = r.ID_Raca";
        List<Animal> animals = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID_Animal");
                String nome = rs.getString("Nome");
                Date data_nasc = rs.getDate("Data_Nascimento");
                String sexo = rs.getString("Sexo");
                String cor = rs.getString("Cor");
                String obs = rs.getString("Observacoes");
                int id_cliente = rs.getInt("ID_Cliente");
                Boolean status = rs.getBoolean("Status");

                int id_raca = rs.getInt("ID_Raca");
                String nome_raca = rs.getString("Nome_Raca");
                String tipo_raca = rs.getString("Tipo_Animal");
                Boolean status_raca = rs.getBoolean("Status");

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
        String sql = "UPDATE animal SET Nome = ?, Data_Nascimento = ?, Sexo = ?, Cor = ?, Observacoes = ?, ID_Cliente = ?, ID_Raca = ? WHERE ID_Animal = ?";

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
        String sql = "UPDATE animal SET status = false WHERE ID_Animal = ?";

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
