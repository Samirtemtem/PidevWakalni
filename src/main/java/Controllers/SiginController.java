package Controllers;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import services.ServiceUser;
import utils.MyDB;

public class SiginController implements Initializable {
    @FXML
    private Label TravelMe;
    @FXML
    private TextField email_signin;
    @FXML
    private PasswordField password_signin;
    @FXML
    private Button login_btn;
    @FXML
    private Hyperlink create_acc;
    @FXML
    private Label TravelMe2;
    @FXML
    private TextField cin;
    @FXML
    private TextField username;
    @FXML
    private Button signup_btn;
    @FXML
    private Hyperlink login_acc;
    @FXML
    private AnchorPane signup_form;
    @FXML
    private AnchorPane login_form;
    @FXML
    private PasswordField confirm_password;
    @FXML
    private TextField numero;
    @FXML
    private PasswordField password_signup;
    @FXML
    private TextField adresse;
    @FXML
    private TextField email_signup;

    @FXML
    private Hyperlink mdp_oub;
    private Connection cnx;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    public SiginController() {
    }

    private Connection connect;

    public void exit() {
        System.exit(0);
    }

    public void textfieldDesign() {
        if (this.email_signin.isFocused()) {
            this.email_signin.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.password_signin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.password_signin.isFocused()) {
            this.email_signin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signin.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
        }

    }

    public void textfieldDesign1() {
        if (this.email_signup.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.password_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.cin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.username.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.adresse.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.numero.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.confirm_password.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.password_signup.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signup.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.cin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.username.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.adresse.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.numero.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.confirm_password.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.cin.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.cin.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.username.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.adresse.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.numero.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.confirm_password.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.username.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.cin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.username.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.adresse.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.numero.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.confirm_password.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.adresse.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.cin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.username.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.adresse.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.numero.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.confirm_password.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.numero.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.cin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.username.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.adresse.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.numero.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
            this.confirm_password.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
        } else if (this.confirm_password.isFocused()) {
            this.email_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.password_signup.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.cin.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.username.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.adresse.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.numero.setStyle("-fx-background-color:transparent;-fx-border-width:1px");
            this.confirm_password.setStyle("-fx-background-color:#fff;-fx-border-width:2px");
        }

    }

    public void dropShadowEffect() {
        DropShadow original = new DropShadow(20.0, Color.valueOf("#ae44a5"));
        original.setRadius(30.0);
        this.TravelMe.setEffect(original);
        this.TravelMe2.setEffect(original);
        this.TravelMe.setOnMouseEntered((event) -> {
            DropShadow shadow = new DropShadow(60.0, Color.valueOf("#ae44a5"));
            this.TravelMe.setCursor(Cursor.HAND);
            this.TravelMe.setStyle("-fx-text-fill:#517ab5");
            this.TravelMe.setEffect(shadow);
        });
        this.TravelMe.setOnMouseExited((event) -> {
            DropShadow shadow = new DropShadow(20.0, Color.valueOf("#ae44a5"));
            shadow.setRadius(30.0);
            this.TravelMe.setStyle("-fx-text-fill:#000");
            this.TravelMe.setEffect(shadow);
        });
        this.TravelMe2.setOnMouseEntered((event) -> {
            DropShadow shadow = new DropShadow(60.0, Color.valueOf("#ae44a5"));
            this.TravelMe2.setCursor(Cursor.HAND);
            this.TravelMe2.setStyle("-fx-text-fill:#517ab5");
            this.TravelMe2.setEffect(shadow);
        });
        this.TravelMe2.setOnMouseExited((event) -> {
            DropShadow shadow = new DropShadow(20.0, Color.valueOf("#ae44a5"));
            shadow.setRadius(30.0);
            this.TravelMe2.setStyle("-fx-text-fill:#000");
            this.TravelMe2.setEffect(shadow);
        });
    }

    public void changeForm(ActionEvent event) {
        if (event.getSource() == this.create_acc) {
            this.signup_form.setVisible(true);
            this.login_form.setVisible(false);
        } else if (event.getSource() == this.login_acc) {
            this.login_form.setVisible(true);
            this.signup_form.setVisible(false);
        }

    }

    public boolean ValidationEmail() {
        Pattern pattern = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9._]+([.][a-zA-Z0-9]+)+");
        Matcher match = pattern.matcher(this.email_signup.getText());
        if (match.find() && match.group().equals(this.email_signup.getText())) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Errore message");
            alert.setHeaderText((String) null);
            alert.setContentText("Invalid Email");
            alert.showAndWait();
            return false;
        }
    }

    public void login() throws IOException, SQLException {
        String email = this.email_signin.getText();
        String password = this.password_signin.getText();

        // Remplacez les conditions actuelles par des vérifications du statut de l'utilisateur dans votre table
        ServiceUser su = new ServiceUser();
        User user = su.login(email, password);
        System.out.println(user.getStatut());
        int statut = user.getStatut();

        if (statut == 0) {
            showAdminInterface();
        } else if (statut == 1) {
            showChefInterface();
        } else if (statut == 2) {
            showNutInterface();
        } else if (statut == 3) {
            showfrontface();
        } else {
            System.out.println("Statut non reconnu pour l'utilisateur : " + email);
        }
    }

    private void showAdminInterface() throws IOException {
        RouterController.switchTo("/fxml/Admin/ProductsCrud.fxml");
    }
    private void showChefInterface()
    {

    }
    private void showfrontface()
    {

    }
    private void showNutInterface()
    {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
// Méthode pour récupérer le statut de l'utilisateur depuis la base de données
