package Controllers;

import entities.Produit;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import services.ServiceProduit;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class back_admin_products_controller {

    @FXML
    private TableView<Produit> tablerecettes;

    @FXML
    private TableColumn<Produit, Integer> PrdID;

    @FXML
    private TableColumn<Produit, String> Nom;

    @FXML
    private TableColumn<Produit, String> Descr;

    @FXML
    private TableColumn<Produit, Float> Prix;

    @FXML
    private TableColumn<Produit, Integer> StockQty;

    @FXML
    private TableColumn<Produit, ImageView> Image;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtDescr;

    @FXML
    private TextField txtPrix;

    @FXML
    private TextField txtStockQty;

    @FXML
    private ImageView imgView;

    private final ServiceProduit serviceProduit = new ServiceProduit();

    public void initialize() {
        PrdID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getPrdID()).asObject());
        Nom.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNom()));
        Descr.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescr()));
        Prix.setCellValueFactory(cellData -> new SimpleFloatProperty(cellData.getValue().getPrix()).asObject());
        StockQty.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStockQty()).asObject());
        Image.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(new ImageView(convertToJavaFXImage(cellData.getValue().getImage()))));



        // Charger les produits au démarrage
        try {
            chargerProduits();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    private Image convertToJavaFXImage(byte[] raw) {
        ByteArrayInputStream bis = new ByteArrayInputStream(raw);
        return new Image(bis);
    }
    private void chargerProduits() throws SQLException {
        List<Produit> produits = serviceProduit.afficher();
        ObservableList<Produit> produitObservableList = FXCollections.observableArrayList(produits);
        tablerecettes.setItems(produitObservableList);
    }
    @FXML
    void handleClicks(ActionEvent event) throws IOException {
        RouterController.switchTo("/fxml/Admin/ProductsCrud.fxml");
    }

    @FXML
    void ajouter(ActionEvent event) throws IOException {
       RouterController.switchTo("/fxml/Admin/AddProduct.fxml");
    }

    @FXML
    void modifier(ActionEvent event) {
        Produit selectedProduit = tablerecettes.getSelectionModel().getSelectedItem();
        RouterController.switchTo("/fxml/Admin/ModifyProduct.fxml",selectedProduit);
    }

    @FXML
    void supprimer(ActionEvent event) {
        Produit selectedProduit = tablerecettes.getSelectionModel().getSelectedItem();
        if (selectedProduit != null) {
            try {
                serviceProduit.supprimer(selectedProduit);
                chargerProduits();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Aucune sélection");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un produit à supprimer.");
            alert.showAndWait();
        }
    }



    private byte[] getImageBytes() {
        return new byte[0];
    }
    private void clearFields() {
        txtNom.clear();
        txtDescr.clear();
        txtPrix.clear();
        txtStockQty.clear();
        imgView.setImage(null);
    }

    public void goToCommandes(ActionEvent actionEvent) throws IOException {
        RouterController.switchTo("/fxml/Admin/OrdersCrud.fxml");
    }
}
