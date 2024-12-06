package dao;

import models.Event;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventDAO {
    private Connection connection;

    public EventDAO(Connection connection) {
        this.connection = connection;
    }

    public void addEvent(Event Event) throws SQLException {
        String query = "INSERT INTO Events (nom_event, date_event, description, id_user) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, Event.getNomEvent());
            stmt.setDate(2, Event.getDateEvent());
            stmt.setString(3, Event.getDescription());
            stmt.setInt(4, Event.getIdUser());
            stmt.executeUpdate();
        }
    }

    public List<Event> getAllEvents() throws SQLException {
        List<Event> Events = new ArrayList<>();
        String query = "SELECT * FROM Events";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Event Event = new Event(
                        rs.getString("nom_event"),
                        rs.getDate("date_event"),
                        rs.getString("description"),
                        rs.getInt("id_user"));
                Event.setIdEvent(rs.getInt("id_event"));
                Events.add(Event);
            }
        }
        return Events;
    }

    public Event getEventById(int idEvent) throws SQLException {
        String query = "SELECT * FROM Events WHERE id_event = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idEvent);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Event(
                            rs.getString("nom_event"),
                            rs.getDate("date_event"),
                            rs.getString("description"),
                            rs.getInt("id_user"));
                }
            }
        }
        return null; 
    }

    public void updateEvent(Event Event) throws SQLException {
        String query = "UPDATE Events SET nom_event = ?, date_event = ?, description = ?, id_user = ? WHERE id_event = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, Event.getNomEvent());
            stmt.setDate(2, Event.getDateEvent());
            stmt.setString(3, Event.getDescription());
            stmt.setInt(4, Event.getIdUser());
            stmt.setInt(5, Event.getIdEvent());
            stmt.executeUpdate();
        }
    }

    public void deleteEvent(int idEvent) throws SQLException {
        String query = "DELETE FROM Events WHERE id_event = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idEvent);
            stmt.executeUpdate();
        }
    }
}
