package Controllers;

import entities.Commande;
import entities.Produit;
import entities.ModePaiement;
import entities.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import services.ServiceCommande;
import services.ServiceProduit;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class AjouterCommande {

    @FXML
    private ComboBox<ModePaiement> cmbModePaiement;

    @FXML
    private ComboBox<Produit> cmbProduit;

    @FXML
    private ComboBox<User> cmbClient;

    @FXML
    private TextField txtAdresse;

    private ServiceCommande serviceCommande = new ServiceCommande();
    private ServiceProduit serviceProduit = new ServiceProduit();
    private ServiceUser serviceClient = new ServiceUser();

    @FXML
    public void initialize() {
        // Populate ModePaiement ComboBox
        cmbModePaiement.setItems(FXCollections.observableArrayList(ModePaiement.values()));

        // Populate Produit ComboBox
        try {
            List<Produit> produits = serviceProduit.afficher();
            cmbProduit.setItems(FXCollections.observableArrayList(produits));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        // Populate Client ComboBox
        try {
            List<User> clients = serviceClient.afficher();
            cmbClient.setItems(FXCollections.observableArrayList(clients));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void ajouter(ActionEvent actionEvent) {
        // Retrieve selected values from ComboBoxes and TextField
        ModePaiement modePaiement = cmbModePaiement.getValue();
        Produit produit = cmbProduit.getValue();
        User client = cmbClient.getValue();
        String adresseLivraison = txtAdresse.getText();

        // Check if any field is empty
        if (modePaiement == null || produit == null || client == null || Objects.equals(adresseLivraison, "")) {
            showAlert("Error", "Remplir tout les champs svp!");
            return;
        }

        String todayDate = java.time.LocalDate.now().toString();

        float montantTotal = produit.getPrix();

        Commande nouvelleCommande = new Commande();
        nouvelleCommande.setId_produit(produit.getPrdID());
        nouvelleCommande.setMontant_total(montantTotal);
        nouvelleCommande.setClient_id(client.getID());
        nouvelleCommande.setDate_commande(todayDate);

        nouvelleCommande.setModePaiement(modePaiement.name());

        nouvelleCommande.setAdresse(adresseLivraison);

        try {
            if(produit.getStockQty()<0) {
                showAlert("Erreur", "Stock epuisée");
                return;
            }
            serviceCommande.ajouter(nouvelleCommande);
            // Métier Stock
            ServiceProduit sp=new ServiceProduit();
            produit.setStockQty(produit.getStockQty()-1);
            sp.modifier(produit);

            showAlertSuccess("Success", "Commande ajoutée avec succès!");

            RouterController.switchTo("/fxml/Admin/OrdersCrud.fxml");
        } catch (SQLException e) {
            showAlert("Error", "Erreur SQL");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private void showAlertSuccess(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    public void retour(ActionEvent actionEvent) throws IOException {
        RouterController.switchTo("/fxml/Admin/OrdersCrud.fxml");
    }

    @FXML
    public void handleImportImage(ActionEvent actionEvent) {
        // Handle importing image
    }
}
