package Controllers;

import entities.Commande;
import entities.Produit;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.ServiceCommande;
import services.ServiceProduit;
import services.ServiceUser;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class back_admin_orders_controller {

    @FXML
    public TableColumn<Commande, String> adresse;
    @FXML
    private TableView<Commande> tableCommandes;

    @FXML
    private TableColumn<Commande, Integer> id_commande;

    @FXML
    private TableColumn<Commande, String> id_produit;

    @FXML
    private TableColumn<Commande, Integer> montant_total;


    @FXML
    private TableColumn<Commande, String> client_id;

    @FXML
    private TableColumn<Commande, String> date_commande;

    @FXML
    private TableColumn<Commande, String> mode_paiement;

    @FXML
    private Button btnRefresh;

    private final ServiceCommande serviceCommande = new ServiceCommande();

    @FXML
    public void initialize() {
        // Initialize TableView columns
        id_commande.setCellValueFactory(new PropertyValueFactory<>("id_commande"));
        id_produit.setCellValueFactory(new PropertyValueFactory<>("id_produit"));
        montant_total.setCellValueFactory(new PropertyValueFactory<>("montant_total"));
        client_id.setCellValueFactory(new PropertyValueFactory<>("client_id"));
        date_commande.setCellValueFactory(new PropertyValueFactory<>("date_commande"));
        adresse.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        mode_paiement.setCellValueFactory(data -> {
            SimpleStringProperty property = new SimpleStringProperty();
            property.setValue(data.getValue().getModePaiement());
            return property;
        });
        client_id.setCellValueFactory(data -> {
            Commande commande = data.getValue();
            String clientName = null;
            try {
                clientName = getClientNameById(commande.getClient_id());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return new SimpleStringProperty(clientName);
        });

        id_produit.setCellValueFactory(data -> {
            Commande commande = data.getValue();
            String productName = null;
            try {
                productName = getProductNameById(commande.getId_produit());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return new SimpleStringProperty(productName);
        });


        // Populate TableView
        refreshTable();

    }

    private String getClientNameById(int clientId) throws SQLException {

        ServiceUser su=new ServiceUser();
        return su.getClientById(clientId).getNom();
    }

    // Method to get product name by ID
    private String getProductNameById(int productId) throws SQLException {

        ServiceProduit sc=new ServiceProduit();
        return sc.getProduitById(productId).getNom();
    }

    private void refreshTable() {
        try {
            List<Commande> commandes = serviceCommande.afficher();
            ObservableList<Commande> commandeList = FXCollections.observableArrayList(commandes);
            tableCommandes.setItems(commandeList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void handleClicks(ActionEvent event) throws IOException {
        RouterController.switchTo("/fxml/Admin/ProductsCrud.fxml");
    }
    public void supprimer(ActionEvent actionEvent) {
        Commande selectedcommande = tableCommandes.getSelectionModel().getSelectedItem();
        if (selectedcommande != null) {
            try {
                serviceCommande.supprimer(selectedcommande);
                refreshTable();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un commande à supprimer.");
            alert.showAndWait();
        }
    }

    public void modifier(ActionEvent actionEvent) {
        Commande selectedCommande = tableCommandes.getSelectionModel().getSelectedItem();
        RouterController.switchTo("/fxml/Admin/ModifyOrder.fxml",selectedCommande);
    }

    public void ajouter(ActionEvent actionEvent) throws IOException {
        RouterController.switchTo("/fxml/Admin/AddOrder.fxml");
    }

    public void goToCommandes(ActionEvent actionEvent) throws IOException {
        RouterController.switchTo("/fxml/Admin/OrdersCrud.fxml");
    }
}
