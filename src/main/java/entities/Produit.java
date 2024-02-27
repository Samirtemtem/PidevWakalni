package entities;

import java.util.Objects;

public class Produit {

    int PrdID;
    String nom , Descr;
    float Prix;
    int StockQty;

    public Produit(String nom, String description, float prix, int stockQty, byte[] imageData) {
        this.nom=nom;
        this.Descr=description;
        this.Prix=prix;
        this.StockQty=stockQty;
        this.image=imageData;

    }

    public int getStockQty() {
        return StockQty;
    }

    public void setStockQty(int stockQty) {
        StockQty = stockQty;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }


    byte[] image; // New attribute to store image data as a blob
    public Produit(int prdID, String nom, String descr, float prix, int stockQty, byte[] image) {
        PrdID = prdID;
        this.nom = nom;
        Descr = descr;
        Prix = prix;
        StockQty = stockQty;
        this.image = image;
    }


    public Produit() {

    }

    public int getPrdID() {
        return PrdID;
    }

    public void setPrdID(int prdID) {
        PrdID = prdID;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescr() {
        return Descr;
    }

    public void setDescr(String descr) {
        Descr = descr;
    }



    public float getPrix() {
        return Prix;
    }

    public void setPrix(float prix) {
        Prix = prix;
    }


    @Override
    public int hashCode() {
        return Objects.hash(getPrdID(), getNom(), getDescr(), getPrix());
    }

    @Override
    public String toString() {
        return nom;
    }

    public Produit(int prdID, String nom, String descr, float prix) {
        PrdID = prdID;
        this.nom = nom;
        Descr = descr;
        Prix = prix;
    }
}
