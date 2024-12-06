import dao.*;
import models.*;
import models.Utilisateur;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/gestion_evenements";
        String user = "root";
        String password = "";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            UtilisateurDAO utilisateurDAO = new UtilisateurDAO(connection);
            EventDAO EventDAO = new EventDAO(connection);
            SalleDAO salleDAO = new SalleDAO(connection);
            TerrainDAO terrainDAO = new TerrainDAO(connection);
            ReservationDAO reservationDAO = new ReservationDAO(connection);

            Scanner scanner = new Scanner(System.in);
            boolean running = true;

            while (running) {
                System.out.println("\n----- Menu -----");
                System.out.println("1. Utilisateur CRUD");
                System.out.println("2. evenement CRUD");
                System.out.println("3. Salle CRUD");
                System.out.println("4. Terrain CRUD");
                System.out.println("5. Reservation CRUD");
                System.out.println("0. Quitter");

                int choix = scanner.nextInt();
                scanner.nextLine();

                switch (choix) {
                    case 1:
                        utilisateurCRUD(utilisateurDAO, scanner);
                        break;
                    case 2:
                        EventCRUD(EventDAO, scanner);
                        break;
                    case 3:
                        salleCRUD(salleDAO, scanner);
                        break;
                    case 4:
                        terrainCRUD(terrainDAO, scanner);
                        break;
                    case 5:
                        reservationCRUD(reservationDAO, scanner);
                        break;
                    case 0:
                        running = false;
                        break;
                    default:
                        System.out.println("Choix invalide !");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void utilisateurCRUD(UtilisateurDAO dao, Scanner scanner) throws SQLException {
        System.out.println("\n1. Ajouter un Utilisateur");
        System.out.println("2. Afficher les Utilisateurs");
        System.out.println("4. Supprimer un Utilisateur");
        System.out.print("Choix: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Nom: ");
                String nom = scanner.nextLine();
                System.out.print("Prenom: ");
                String prenom = scanner.nextLine();
                System.out.print("Email: ");
                String email = scanner.nextLine();
                System.out.print("Type (ETUDIANT/PROFESSEUR): ");
                String type = scanner.nextLine();
                Utilisateur utilisateur = new Utilisateur(nom, prenom, email, type);
                dao.ajouterUtilisateur(utilisateur);
                System.out.println("Utilisateur ajoute !");
                break;
            case 2:
                List<Utilisateur> utilisateurs = dao.afficherUtilisateurs();
                for (Utilisateur u : utilisateurs) {
                    System.out.println("ID: " + u.getId() + ", Nom: " + u.getNom() + ", Prenom: " + u.getPrenom()
                            + ", Email: " + u.getEmail() + ", Type: " + u.getType());
                }
                break;

            case 3:
                System.out.print("ID de l'utilisateur a supprimer: ");
                int idSupp = scanner.nextInt();
                scanner.nextLine();
                dao.supprimerUtilisateur(idSupp);
                System.out.println("Utilisateur supprime !");
                break;
        }
    }

    private static void EventCRUD(EventDAO dao, Scanner scanner) throws SQLException {
        System.out.println("\n1. Ajouter un evenement");
        System.out.println("2. Afficher les evenements");
        System.out.println("3. Modifier un evenement");
        System.out.println("4. Supprimer un evenement");
        System.out.print("Choix: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Nom de l'evenement: ");
                String nomEvent = scanner.nextLine();
                System.out.print("Date de l'evenement (YYYY-MM-DD): ");
                Date dateEvent = Date.valueOf(scanner.nextLine());
                System.out.print("Description: ");
                String description = scanner.nextLine();
                System.out.print("ID Utilisateur (responsable): ");
                int idUserEvent = scanner.nextInt();
                scanner.nextLine();
                Event Event = new Event(nomEvent, dateEvent, description, idUserEvent);
                dao.addEvent(Event);
                System.out.println("evenement ajoute !");
                break;
            case 2:
                List<Event> Events = dao.getAllEvents();
                for (Event e : Events) {
                    System.out.println(
                            "ID: " + e.getIdEvent() + ", Nom: " + e.getNomEvent() + ", Date: " + e.getDateEvent()
                                    + ", Description: " + e.getDescription() + ", Utilisateur ID: " + e.getIdUser());
                }
                break;
            case 3:
                System.out.print("ID de l'evenement a modifier: ");
                int idModifEvent = scanner.nextInt();
                scanner.nextLine();
                Event eModif = dao.getEventById(idModifEvent);
                if (eModif != null) {
                    System.out.print("Nouveau Nom de l'evenement: ");
                    eModif.setNomEvent(scanner.nextLine());
                    System.out.print("Nouvelle Date (YYYY-MM-DD): ");
                    eModif.setDateEvent(Date.valueOf(scanner.nextLine()));
                    System.out.print("Nouvelle Description: ");
                    eModif.setDescription(scanner.nextLine());
                    dao.updateEvent(eModif);
                    System.out.println("evenement modifie !");
                } else {
                    System.out.println("evenement non trouve !");
                }
                break;
            case 4:
                System.out.print("ID de l'evenement a supprimer: ");
                int idSuppEvent = scanner.nextInt();
                scanner.nextLine();
                dao.deleteEvent(idSuppEvent);
                System.out.println("evenement supprime !");
                break;
        }
    }

    private static void salleCRUD(SalleDAO dao, Scanner scanner) throws SQLException {
        System.out.println("\n1. Ajouter une Salle");
        System.out.println("2. Afficher les Salles");
        System.out.println("3. Supprimer une Salle");
        System.out.print("Choix: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Nom de la Salle: ");
                String nomSalle = scanner.nextLine();
                System.out.print("Capacite: ");
                int capacite = scanner.nextInt();
                scanner.nextLine();
                Salle salle = new Salle(nomSalle, capacite);
                dao.addSalle(salle);
                System.out.println("Salle ajoutee !");
                break;
            case 2:
                List<Salle> salles = dao.getAllSalles();
                for (Salle s : salles) {
                    System.out.println("ID: " + s.getId() + ", Nom: " + s.getNom() + ", Capacite: " + s.getCapacite());
                }
                break;
            case 3:
                System.out.print("ID de la Salle a supprimer: ");
                int idSuppSalle = scanner.nextInt();
                scanner.nextLine();
                dao.deleteSalle(idSuppSalle);
                System.out.println("Salle supprimee !");
                break;
        }
    }

    private static void terrainCRUD(TerrainDAO dao, Scanner scanner) throws SQLException {
        System.out.println("\n1. Ajouter un Terrain");
        System.out.println("2. Afficher les Terrains");
        System.out.println("3. Supprimer un Terrain");
        System.out.print("Choix: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("Nom du Terrain: ");
                String nomTerrain = scanner.nextLine();
                System.out.print("Type (Par exemple: Football, Basket): ");
                String typeTerrain = scanner.nextLine();
                Terrain terrain = new Terrain(nomTerrain, typeTerrain);
                dao.addTerrain(terrain);
                System.out.println("Terrain ajoute !");
                break;
            case 2:
                List<Terrain> terrains = dao.getAllTerrains();
                for (Terrain t : terrains) {
                    System.out.println("ID: " + t.getId() + ", Nom: " + t.getNom() + ", Type: " + t.getType());
                }
                break;
            case 3:
                System.out.print("ID du Terrain a supprimer: ");
                int idSuppTerrain = scanner.nextInt();
                scanner.nextLine();
                dao.deleteTerrain(idSuppTerrain);
                System.out.println("Terrain supprime !");
                break;
        }
    }

    private static void reservationCRUD(ReservationDAO dao, Scanner scanner) throws SQLException {
        System.out.println("\n1. Ajouter une Reservation");
        System.out.println("2. Afficher les Reservations");
        System.out.println("3. Supprimer une Reservation");
        System.out.print("Choix: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                System.out.print("ID Utilisateur: ");
                int idUtilisateur = scanner.nextInt();
                scanner.nextLine();
                System.out.print("ID evenement: ");
                int idEvent = scanner.nextInt();
                scanner.nextLine();
                System.out.print("ID Salle: ");
                int idSalle = scanner.nextInt();
                scanner.nextLine();
                System.out.print("ID Terrain: ");
                int idTerrain = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Date de Reservation (YYYY-MM-DD): ");
                Date dateReservation = Date.valueOf(scanner.nextLine());
                Reservation reservation = new Reservation(idUtilisateur, idEvent, idSalle, idTerrain, dateReservation);
                dao.addReservation(reservation);
                System.out.println("Reservation ajoutee !");
                break;
            case 2:
                List<Reservation> reservations = dao.getAllReservations();
                for (Reservation r : reservations) {
                    System.out.println("ID: " + r.getIdReservation() + ", Utilisateur ID: " + r.getIdUser()
                            + ", evenement ID: " + r.getIdEvent() + ", Salle ID: " + r.getIdSalle() + ", Terrain ID: "
                            + r.getIdTerrain() + ", Date: " + r.getDateReservation());
                }
                break;
            case 3:
                System.out.print("ID de la Reservation a supprimer: ");
                int idSuppRes = scanner.nextInt();
                scanner.nextLine();
                dao.deleteReservation(idSuppRes);
                System.out.println("Reservation supprimee !");
                break;
        }
    }
}
