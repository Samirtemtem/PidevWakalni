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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.ServiceProduit;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class ajouter_produit {
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

    @FXML
    void ajouter(ActionEvent event) {
        try {
            String nom = txtNom.getText();
            String description = txtDescription.getText();
            String prixText = txtPrix.getText();
            String stockQtyText = txtStockQty.getText();

            if (nom.isEmpty() || description.isEmpty() || prixText.isEmpty() || stockQtyText.isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez remplir tous les champs.");
                return;
            }

            float prix;
            int stockQty;

            try {
                prix = Float.parseFloat(prixText);
                stockQty = Integer.parseInt(stockQtyText);
            } catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Veuillez entrer des valeurs numériques valides pour Prix et Quantité en stock.");
                return;
            }

            if (prix <= 0 || stockQty <= 0) {
                showAlert(Alert.AlertType.ERROR, "Erreur", "Les valeurs de Prix et Quantité en stock doivent être supérieures à zéro.");
                return;
            }

            Image image = imageView.getImage();
            byte[] imageData = null;
            if (image != null) {
                imageData = convertImageToByteArray(image);
            }

            Produit nouveauProduit = new Produit(nom, description, prix, stockQty, imageData);

            ServiceProduit sp = new ServiceProduit();
            sp.ajouter(nouveauProduit);

            txtNom.clear();
            txtDescription.clear();
            txtPrix.clear();
            txtStockQty.clear();
            imageView.setImage(null);
            showAlert(Alert.AlertType.INFORMATION, "Succès", "Produit ajouté avec succès.");
            ActionEvent e = null;
            RouterController.switchTo("/fxml/Admin/ProductsCrud.fxml");
        } catch (SQLException e) {
            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ajout du produit.");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void handleImportImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            imageView.setImage(image);
        }
    }

    private byte[] convertImageToByteArray(Image image) {
        try {
            FileInputStream fis = new FileInputStream(image.getUrl().substring(5));
            byte[] buffer = new byte[(int) image.getHeight() * (int) image.getWidth()];
            fis.read(buffer);
            fis.close();
            return buffer;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @FXML
    void retour(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Admin/ProductsCrud.fxml"));
            Parent root = loader.load();

            Scene scene = new Scene(root);

            // Obtenir la référence à la scène actuelle
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène sur la fenêtre principale
            stage.setScene(scene);

        } catch (IOException e) {
            e.printStackTrace(); // Gérer les erreurs de chargement du FXML
        }    }

}
