package dao;

import models.Terrain;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TerrainDAO {
    private Connection connection;

    public TerrainDAO(Connection connection) {
        this.connection = connection;
    }

    public void addTerrain(Terrain Terrain) throws SQLException {
        String query = "INSERT INTO terrains (nom_terrain, type) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, Terrain.getNom());
            stmt.setString(2, Terrain.getType());
            stmt.executeUpdate();
        }
    }

    public List<Terrain> getAllTerrains() throws SQLException {
        List<Terrain> Terrains = new ArrayList<>();
        String query = "SELECT * FROM terrains";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Terrain Terrain = new Terrain(
                        rs.getString("nom_terrain"),
                        rs.getString("type"));
                Terrain.setId(rs.getInt("id_terrain"));
                Terrains.add(Terrain);
            }
        }
        return Terrains;
    }

    public void deleteTerrain(int id) throws SQLException {
        String query = "DELETE FROM terrains WHERE id_terrain = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
