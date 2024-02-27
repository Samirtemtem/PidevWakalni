package entities;

public class Commande {
    private int id_commande, id_produit, client_id;
    private String date_commande;
    private float montant_total;
    private String modePaiement; // Correct import for ModePaiement

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public Commande(int id_commande, int id_produit, float montant_total, int client_id, String date_commande, String mode_paiement,String adresse) {
        this.id_commande = id_commande;
        this.id_produit = id_produit;
        this.montant_total = montant_total;
        this.client_id = client_id;
        this.date_commande = date_commande;
        this.modePaiement = mode_paiement;
        this.setAdresse(adresse);
    }

    public Commande(int id_produit, float montant_total, int client_id, String date_commande, String mode_paiement) {
        this.id_produit = id_produit;
        this.montant_total = montant_total;
        this.client_id = client_id;
        this.date_commande = date_commande;
        this.modePaiement = mode_paiement;
    }

    public int getClient_id() {
        return client_id;
    }

    public void setClient_id(int client_id) {
        this.client_id = client_id;
    }

    public int getId_commande() {
        return id_commande;
    }

    public void setId_commande(int id_commande) {
        this.id_commande = id_commande;
    }

    public int getId_produit() {
        return id_produit;
    }

    public void setId_produit(int id_produit) {
        this.id_produit = id_produit;
    }

    public String getDate_commande() {
        return date_commande;
    }

    public void setDate_commande(String date_commande) {
        this.date_commande = date_commande;
    }

    public float getMontant_total() {
        return montant_total;
    }

    public void setMontant_total(float montant_total) {
        this.montant_total = montant_total;
    }



    public String Adresse;

    public Commande(int id_commande, int id_produit, int montant_total, int client_id, String date_commande, String mode_paiement, String adresse) {
        this.id_commande = id_commande;
        this.id_produit = id_produit;
        this.montant_total = montant_total;
        this.client_id = client_id;
        this.date_commande = date_commande;
        this.modePaiement = mode_paiement;
        Adresse = adresse;
    }
    public Commande( int id_produit, int montant_total, int client_id, String date_commande, String mode_paiement, String adresse) {
        this.id_produit = id_produit;
        this.montant_total = montant_total;
        this.client_id = client_id;
        this.date_commande = date_commande;
        this.modePaiement = mode_paiement;
        Adresse = adresse;
    }

    public String getAdresse() {
        return Adresse;
    }

    public void setAdresse(String adresse) {
        Adresse = adresse;
    }

    @Override
    public String toString() {
        return "Commande{" +
                "id_commande=" + id_commande +
                ", id_produit=" + id_produit +
                ", date_commande='" + date_commande + '\'' +
                ", montant_total=" + montant_total +
                ", mode_paiement=" + modePaiement +
                '}';
    }

    public Commande() {
    }

    public Commande(int id_commande, int id_produit, String date_commande, int montant_total, String mode_paiement) {
        this.id_commande = id_commande;
        this.id_produit = id_produit;
        this.date_commande = date_commande;
        this.montant_total = montant_total;
        this.modePaiement = mode_paiement;
    }

    public Commande(int id_produit, String date_commande, int montant_total, String mode_paiement) {
        this.id_produit = id_produit;
        this.date_commande = date_commande;
        this.montant_total = montant_total;
        this.modePaiement = mode_paiement;
    }



    public enum ModePaiement {
        ESPECES,
        CHEQUE,
        CARTE_BANCAIRE,
        VIREMENT
    }
}
