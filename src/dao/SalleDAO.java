package dao;

import models.Salle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleDAO {
    private Connection connection;

    public SalleDAO(Connection connection) {
        this.connection = connection;
    }

    public void addSalle(Salle Salle) throws SQLException {
        String query = "INSERT INTO salles (nom_salle, capacite) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, Salle.getNom());
            stmt.setInt(2, Salle.getCapacite());
            stmt.executeUpdate();
        }
    }

    public List<Salle> getAllSalles() throws SQLException {
        List<Salle> Salles = new ArrayList<>();
        String query = "SELECT * FROM salles";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Salle Salle = new Salle(
                        rs.getString("nom_salle"),
                        rs.getInt("capacite"));
                Salle.setId(rs.getInt("id_salle"));
                Salles.add(Salle);
            }
        }
        return Salles;
    }

    public void deleteSalle(int id) throws SQLException {
        String query = "DELETE FROM salles WHERE id_salle = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
