package Controllers;

import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceProduit;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class modifier_produit implements ControlledScreen {

    private Produit ProductToModify;

    @FXML
    private TextField txtNom;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtPrix;

    @FXML
    private TextField txtStockQty;

    @FXML
    private ImageView imageView;

    private byte[] imageData;

    private ServiceProduit serviceProduit = new ServiceProduit();


    public void setProductToModify(Produit selectedProduit) {
        this.ProductToModify = selectedProduit;
        loadData();
    }

    private void loadData() {
        if (ProductToModify != null) {
            txtNom.setText(ProductToModify.getNom());
            txtDescription.setText(ProductToModify.getDescr());
            txtPrix.setText(String.valueOf(ProductToModify.getPrix()));
            txtStockQty.setText(String.valueOf(ProductToModify.getStockQty()));

            imageData = ProductToModify.getImage();
            if (imageData != null && imageData.length > 0) {
                imageView.setImage(new Image(new ByteArrayInputStream(imageData)));
            }
        }
    }

    @FXML
    void modifier(ActionEvent event) {
        try {
            String nom = txtNom.getText();
            String description = txtDescription.getText();
            float prix = Float.parseFloat(txtPrix.getText());
            int stockQty = Integer.parseInt(txtStockQty.getText());
            if (nom.isEmpty() || description.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
                return;
            }
            if (prix <= 0 || stockQty <= 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Les valeurs de Prix et Quantité en stock doivent être supérieures à zéro.");
                return;
            }
            // Update the product
            Produit updatedProduct;
            if (imageData != null) {
                updatedProduct = new Produit(ProductToModify.getPrdID(), nom, description, prix, stockQty, imageData);
            } else {
                updatedProduct = new Produit(ProductToModify.getPrdID(), nom, description, prix, stockQty, ProductToModify.getImage());
            }

            serviceProduit.modifier(updatedProduct);

           // clearFields();
            showAlert(Alert.AlertType.INFORMATION, "Success", "Produit modifié avec succès.");
            //MouseEvent fakeMouseEvent = new MouseEvent(MouseEvent.MOUSE_CLICKED, 0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true, true, null);
            //retour(fakeMouseEvent);
        } catch (NumberFormatException | SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Une erreur s'est produite lors de la modification du produit.");
        }
    }

    @FXML
    void handleImportImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif")
        );
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            try {
                imageData = new byte[(int) file.length()];
                FileInputStream fis = new FileInputStream(file);
                fis.read(imageData);
                fis.close();
                imageView.setImage(new Image(new ByteArrayInputStream(imageData)));
            } catch (IOException e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Une erreur s'est produite lors du chargement de l'image.");
            }
        }
    }

    private void clearFields() {
        txtNom.clear();
        txtDescription.clear();
        txtPrix.clear();
        txtStockQty.clear();
        imageView.setImage(null);
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void retour(MouseEvent event) {
        System.out.println("Button Clicked");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/ProductsCrud.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Get the reference to the current stage from the event source
            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();

            System.out.println("Changing Scene");

            // Set the new scene on the main window
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void setData(Object data) {

        if (data instanceof Produit) {
            ProductToModify = (Produit) data;
        }
        loadData();
    }
}
