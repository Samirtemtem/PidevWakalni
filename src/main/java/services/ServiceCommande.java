package services;

import entities.Commande;
import entities.ModePaiement;
import utils.MyDB;

import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ServiceCommande implements IService<Commande> {
    private Connection connection;

    public ServiceCommande() {
        connection = MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Commande commande) throws SQLException {
        String req = "INSERT INTO commande (id_produit, montant_total, client_id, date_commande, mode_paiement,Adresse) VALUES (?, ?, ?, ?, ?,?)";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setInt(1, commande.getId_produit());
            pre.setFloat(2, commande.getMontant_total());
            pre.setInt(3, commande.getClient_id());
            pre.setString(4, commande.getDate_commande());
            pre.setString(5, commande.getModePaiement());
            pre.setString(6,commande.getAdresse());


            pre.executeUpdate();
        }
    }
    public static Date convertToDate(String dateString) {
        // Parse the String to LocalDate
        LocalDate localDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Convert LocalDate to java.sql.Date
        Date sqlDate = Date.valueOf(localDate);

        return sqlDate;
    }

    @Override
    public void modifier(Commande commande) throws SQLException {
        String req = "UPDATE commande SET id_produit=?, montant_total=?, client_id=?, date_commande=?, mode_paiement=?,adresse=? WHERE id_commande=?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setInt(1, commande.getId_produit());
            pre.setFloat(2, commande.getMontant_total());
            pre.setInt(3, commande.getClient_id());
            pre.setDate(4, convertToDate(commande.getDate_commande()));
            pre.setString(5, commande.getModePaiement());
            pre.setString(6,commande.getAdresse());
            System.out.println("ID Commande"+commande.getId_commande());
            pre.setInt(7, commande.getId_commande());

            pre.executeUpdate();
        }
    }

    @Override
    public void supprimer(Commande commande) throws SQLException {
        String req="DELETE FROM commande WHERE id_commande=?";
        try (PreparedStatement pre = connection.prepareStatement(req)) {
            pre.setInt(1, commande.getId_commande());
            pre.executeUpdate();
        }
    }

    @Override
    public List<Commande> afficher() throws SQLException {
        String req="SELECT * FROM commande";
        List<Commande> list = new ArrayList<>();
        try (Statement ste = connection.createStatement();
             ResultSet res = ste.executeQuery(req)) {

            while (res.next()) {
                Commande c = new Commande();
                c.setId_commande(res.getInt("id_commande"));
                c.setId_produit(res.getInt("id_produit"));
                c.setMontant_total(res.getInt("montant_total"));
                c.setClient_id(res.getInt("client_id"));
                c.setDate_commande(res.getString("date_commande"));
                c.setModePaiement(res.getString("mode_paiement"));
                c.setAdresse(res.getString("adresse"));
                System.out.println(c.getModePaiement());
                list.add(c);
            }
        }
        return list;
    }
}
