package services;

import entities.Produit;
import utils.MyDB;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduit implements IService<Produit> {
    private Connection connection;

    public ServiceProduit(){
        connection= MyDB.getInstance().getConnection();
    }

    @Override
    public void ajouter(Produit produit) throws SQLException {
        String req = "INSERT INTO produit (Nom, Prix, Descr, image,StockQty) VALUES (?, ?, ?, ?,?)";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, produit.getNom());
        pre.setFloat(2, produit.getPrix());
        pre.setString(3, produit.getDescr());
        pre.setBytes(4, produit.getImage());
        pre.setInt(5,produit.getStockQty());

        pre.executeUpdate();
    }

    @Override
    public void modifier(Produit produit) throws SQLException {
        String req = "UPDATE produit SET Nom=?, Prix=?, Descr=?, image=?, StockQty=? WHERE PrdID=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setString(1, produit.getNom());
        pre.setFloat(2, produit.getPrix());
        pre.setString(3, produit.getDescr());
        pre.setBytes(4, produit.getImage());
        pre.setInt(5,produit.getStockQty());
        pre.setInt(6, produit.getPrdID());

        pre.executeUpdate();
    }

    @Override
    public void supprimer(Produit produit) throws SQLException {
        String req="DELETE FROM produit WHERE PrdID=?";
        PreparedStatement pre = connection.prepareStatement(req);
        pre.setInt(1, produit.getPrdID());
        pre.executeUpdate();
    }

    @Override
    public List<Produit> afficher() throws SQLException {
        String req="SELECT * FROM produit";
        Statement ste=connection.createStatement();
        ResultSet res= ste.executeQuery(req);
        List<Produit> list=new ArrayList<>();
        while (res.next()) {
            Produit p = new Produit();
            p.setPrdID(res.getInt("PrdID"));
            p.setNom(res.getString("Nom"));
            p.setPrix(res.getFloat("Prix"));
            p.setDescr(res.getString("Descr"));
            p.setImage(res.getBytes("image"));
            p.setStockQty(res.getInt("StockQty"));

            list.add(p);
        }
        return list;
    }

    public Produit getProduitById(int idProduit) throws SQLException {
        Produit produit = null;
        String query = "SELECT * FROM produit WHERE PrdID = ?";
         PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, idProduit);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Produit p = new Produit();
                p.setPrdID(resultSet.getInt("PrdID"));
                p.setNom(resultSet.getString("Nom"));
                p.setPrix(resultSet.getFloat("Prix"));
                p.setDescr(resultSet.getString("Descr"));
                p.setImage(resultSet.getBytes("image"));
                p.setStockQty(resultSet.getInt("StockQty"));
                return p;
            }
        return new Produit();
    }
}
