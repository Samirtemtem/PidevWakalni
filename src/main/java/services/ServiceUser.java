package services;

import entities.User;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceUser implements IService<User> {
    private Connection connection;

    public ServiceUser() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(User user) throws SQLException {
        String req = "INSERT INTO user (Nom, Prenom, Email, Statut, Adresse, Numtel, Image, Mdp, role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, user.getNom());
        pre.setString(2, user.getPrenom());
        pre.setString(3, user.getEmail());
        pre.setInt(4, user.getStatut());
        pre.setString(5, user.getAdresse());
        pre.setString(6, user.getNumtel());
        pre.setBytes(7, user.getImage());
        pre.setString(8, user.getMdp());
        pre.setString(9, user.getRole());

        pre.executeUpdate();
    }

    @Override
    public void modifier(User user) throws SQLException {
        String req = "UPDATE user SET Nom=?, Prenom=?, Email=?, Statut=?, Adresse=?, Numtel=?, Image=?, Mdp=?, role=? WHERE ID=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, user.getNom());
        pre.setString(2, user.getPrenom());
        pre.setString(3, user.getEmail());
        pre.setInt(4, user.getStatut());
        pre.setString(5, user.getAdresse());
        pre.setString(6, user.getNumtel());
        pre.setBytes(7, user.getImage());
        pre.setString(8, user.getMdp());
        pre.setString(9, user.getRole());
        pre.setInt(10, user.getID());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(User user) throws SQLException {
        String req = "DELETE FROM user WHERE ID=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, user.getID());
        pre.executeUpdate();
    }

    @Override
    public List<User> afficher() throws SQLException {
        String req = "SELECT * FROM user";
        Statement ste = connection.createStatement();
        ResultSet res = ste.executeQuery(req);
        List<User> list = new ArrayList<>();
        while (res.next()) {
            User u = new User();
            u.setID(res.getInt("ID"));
            u.setNom(res.getString("Nom"));
            u.setPrenom(res.getString("Prenom"));
            u.setEmail(res.getString("Email"));
            u.setStatut(res.getInt("Statut"));
            u.setAdresse(res.getString("Adresse"));
            u.setNumtel(res.getString("Numtel"));
            u.setImage(res.getBytes("Image"));
            u.setMdp(res.getString("Mdp"));
            u.setRole(res.getString("role"));
            list.add(u);
        }
        return list;
    }

    public User login(String email, String mdp) throws SQLException {
        String req = "SELECT * FROM user WHERE Email=? AND Mdp=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, email);
        pre.setString(2, mdp);
        ResultSet res = pre.executeQuery();
        if (res.next()) {
            User user = new User();
            user.setID(res.getInt("ID"));
            user.setNom(res.getString("Nom"));
            user.setPrenom(res.getString("Prenom"));
            user.setEmail(res.getString("Email"));
            user.setStatut(res.getInt("Statut"));
            user.setAdresse(res.getString("Adresse"));
            user.setNumtel(res.getString("Numtel"));
            user.setImage(res.getBytes("Image"));
            user.setMdp(res.getString("Mdp"));
            user.setRole(res.getString("role"));
            return user;
        }
        return null;
    }
    public User getClientById(int id) throws SQLException {
        User user = null;
        String req = "SELECT * FROM user WHERE ID=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, id);
        ResultSet res = pre.executeQuery();

        if (res.next()) {
            user = new User();
            user.setID(res.getInt("ID"));
            user.setNom(res.getString("Nom"));
            user.setPrenom(res.getString("Prenom"));
            user.setEmail(res.getString("Email"));
            user.setStatut(res.getInt("Statut"));
            user.setAdresse(res.getString("Adresse"));
            user.setNumtel(res.getString("Numtel"));
            user.setImage(res.getBytes("Image"));
            user.setMdp(res.getString("Mdp"));
            user.setRole(res.getString("role"));
        }

        return user;
    }

}
