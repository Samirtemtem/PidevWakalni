package Controllers;

import entities.Commande;
import entities.Produit;
import entities.User;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import services.ServiceCommande;
import services.ServiceProduit;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ModifierCommande implements ControlledScreen {

    @FXML
    private ComboBox<String> cmbModePaiement;

    @FXML
    private ComboBox<Produit> cmbProduit;

    @FXML
    private ComboBox<User> cmbClient;

    @FXML
    private TextField txtAdresse;

    @FXML
    private Button btnAjouter;

    private final ServiceProduit serviceProduit = new ServiceProduit();
    private final ServiceUser serviceClient = new ServiceUser();

    private Commande commandetomodify;

    @FXML
    public void initialize() {
        initializeComboBoxes();
    }

    private void initializeComboBoxes() {
        try {
            List<Produit> produits = serviceProduit.afficher();
            cmbProduit.setItems(FXCollections.observableArrayList(produits));

            List<User> clients = serviceClient.afficher();
            cmbClient.setItems(FXCollections.observableArrayList(clients));

            cmbModePaiement.setItems(FXCollections.observableArrayList("ESPECES", "CHEQUE", "CARTE_BANCAIRE"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @FXML
    public void modifier(ActionEvent actionEvent) {
        String modePaiement = cmbModePaiement.getValue();
        Produit selectedProduit = cmbProduit.getValue();
        User selectedClient = cmbClient.getValue();
        String adresse = txtAdresse.getText();
        LocalDate currentDate = LocalDate.now();

        // Create a formatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        String formattedDate = currentDate.format(formatter);

        if (modePaiement != null && selectedProduit != null && selectedClient != null && !adresse.isEmpty()) {
            Commande newCommande = new Commande(commandetomodify.getId_commande(),selectedProduit.getPrdID(), selectedProduit.getPrix(), selectedClient.getID(),formattedDate, modePaiement, adresse);
            try {
                ServiceCommande sc=new ServiceCommande();
                 sc.modifier(newCommande);
                showAlert(Alert.AlertType.ERROR, "Erreur", "Commande ajoutée avec succès:.");


            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Remplir tout les champs.");
        }
    }

    @FXML
    public void retour(ActionEvent actionEvent) throws IOException {
        RouterController.switchTo("/fxml/Admin/OrdersCrud.fxml");
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @Override
    public void setData(Object data) throws SQLException {
        if (data instanceof Commande) {
            this.commandetomodify = (Commande) data;
            if (commandetomodify != null) {
              initializedata();
            }
        }
    }

    private void initializedata() throws SQLException {
        // Set the values of fields and ComboBoxes
        cmbModePaiement.setValue(commandetomodify.getModePaiement());
        // Assuming you have a method to get Produit and Client objects based on IDs
        Produit produit = serviceProduit.getProduitById(commandetomodify.getId_produit());
        cmbProduit.setValue(produit);
        ServiceUser su=new ServiceUser();
        User client = su.getClientById(commandetomodify.getClient_id());
        cmbClient.setValue(client);
        txtAdresse.setText(commandetomodify.getAdresse());
    }
}
