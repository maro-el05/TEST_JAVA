package dao;

import models.Reservation;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO {
    private Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    public void addReservation(Reservation reservation) throws SQLException {
        String query = "INSERT INTO reservations (id_user, id_event, id_salle, id_terrain, date_reservation) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, reservation.getIdUser());
            stmt.setInt(2, reservation.getIdEvent());
            stmt.setInt(3, reservation.getIdSalle());
            stmt.setInt(4, reservation.getIdTerrain());
            stmt.setDate(5, reservation.getDateReservation());
            stmt.executeUpdate();
        }
    }

    public boolean checkAvailability(int idSalle, int idTerrain, Date dateReservation) throws SQLException {
        String query = "SELECT COUNT(*) FROM reservations WHERE (id_salle = ? OR id_terrain = ?) AND date_reservation = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idSalle);
            stmt.setInt(2, idTerrain);
            stmt.setDate(3, dateReservation);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) == 0; 
            }
        }
        return false;
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String query = "SELECT * FROM reservations";
        try (Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Reservation reservation = new Reservation(
                        rs.getInt("id_user"),
                        rs.getInt("id_event"),
                        rs.getInt("id_salle"),
                        rs.getInt("id_terrain"),
                        rs.getDate("date_reservation"));
                reservation.setIdReservation(rs.getInt("id_reservation"));
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    public Reservation getReservationById(int idReservation) throws SQLException {
        String query = "SELECT * FROM reservations WHERE id_reservation = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idReservation);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Reservation(
                            rs.getInt("id_user"),
                            rs.getInt("id_event"),
                            rs.getInt("id_salle"),
                            rs.getInt("id_terrain"),
                            rs.getDate("date_reservation"));
                }
            }
        }
        return null; 
    }

    public void updateReservation(Reservation reservation) throws SQLException {
        String query = "UPDATE reservations SET id_user = ?, id_event = ?, id_salle = ?, id_terrain = ?, date_reservation = ? WHERE id_reservation = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, reservation.getIdUser());
            stmt.setInt(2, reservation.getIdEvent());
            stmt.setInt(3, reservation.getIdSalle());
            stmt.setInt(4, reservation.getIdTerrain());
            stmt.setDate(5, reservation.getDateReservation());
            stmt.setInt(6, reservation.getIdReservation());
            stmt.executeUpdate();
        }
    }

    public void deleteReservation(int idReservation) throws SQLException {
        String query = "DELETE FROM reservations WHERE id_reservation = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idReservation);
            stmt.executeUpdate();
        }
    }
}
