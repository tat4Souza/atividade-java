package com.pratica4.application.dao;

import com.pratica4.application.factory.ConnectionFactory;
import com.pratica4.application.models.Raca;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RacaDAO {
    private Connection conn;

    public RacaDAO() {
        this.conn = new ConnectionFactory().getConnection();
    }

    public boolean addRaca(Raca raca) {
        String sql = "INSERT INTO raca(Nome_Raca, Tipo_Animal, Status) VALUES(?,?, ?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, raca.getNome());
            stmt.setString(2, raca.getTipo());
            stmt.setBoolean(3, true);

            stmt.execute();
            stmt.close();

            return true;
        } catch(SQLException e) {
            e.printStackTrace();

            return false;
        }
    }

    public List<Raca> viewRaca() {
        String sql = "SELECT * FROM raca";
        List<Raca> races = new ArrayList<>();

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("ID_Raca");
                String nome = rs.getString("Nome_Raca");
                String tipo = rs.getString("Tipo_Animal");
                Boolean status = rs.getBoolean("Status");

                Raca race = new Raca( nome, tipo, status);
                races.add(race);
                race.setId(id);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao carregar raças: " + e.getMessage());
        }

        return races;
    }

    public void alterRaca(Raca raca, Integer id) {
        String sql = "UPDATE raca SET Nome_Raca = ?, Tipo_Animal = ? WHERE ID_Raca = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, raca.getNome());
            stmt.setString(2, raca.getTipo());
            stmt.setInt(3, id);

            stmt.execute();
            stmt.close();
        } catch(SQLException e) {
            throw new RuntimeException("Erro ao editar raça: " + e.getMessage());
        }
    }

    public void deleteRaca(Integer id) {
        String sql = "UPDATE raca SET status = false WHERE ID_Raca = ?";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);

            stmt.executeUpdate();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao desativar raça: " + e.getMessage());
        }
    }

}
