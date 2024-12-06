package models;

import java.sql.Date;

public class Reservation {
    private int idReservation;
    private int idUser;
    private int idEvent;
    private int idSalle;
    private int idTerrain;
    private Date dateReservation;

    // Constructor
    public Reservation(int idUser, int idEvent, int idSalle, int idTerrain, Date dateReservation) {
        this.idUser = idUser;
        this.idEvent = idEvent;
        this.idSalle = idSalle;
        this.idTerrain = idTerrain;
        this.dateReservation = dateReservation;
    }

    // Getters and Setters
    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdEvent() {
        return idEvent;
    }

    public void setIdEvent(int idEvent) {
        this.idEvent = idEvent;
    }

    public int getIdSalle() {
        return idSalle;
    }

    public void setIdSalle(int idSalle) {
        this.idSalle = idSalle;
    }

    public int getIdTerrain() {
        return idTerrain;
    }

    public void setIdTerrain(int idTerrain) {
        this.idTerrain = idTerrain;
    }

    public Date getDateReservation() {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation) {
        this.dateReservation = dateReservation;
    }
}
