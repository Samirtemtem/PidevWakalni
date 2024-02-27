package Controllers;

import Controllers.ControlledScreen;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.SQLException;

public class RouterController {

    private static Stage primaryStage;
    private double x;
    private double y;

    public RouterController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void init() {
        //set stage borderless
        primaryStage.initStyle(StageStyle.UNDECORATED);

        //drag it here
        primaryStage.getScene().getRoot().setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
        });
        primaryStage.getScene().getRoot().setOnMouseDragged(event -> {
            primaryStage.setX(event.getScreenX() - x);
            primaryStage.setY(event.getScreenY() - y);
        });
    }

    public static void switchTo(String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(RouterController.class.getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void switchTo(String fxmlPath, Object data) {
        try {
            FXMLLoader loader = new FXMLLoader(RouterController.class.getResource(fxmlPath));
            Parent root = loader.load();

            // Access the controller of the new scene
            ControlledScreen controller = loader.getController();

            // Pass the data to the controller if it implements ControlledScreen
            if (controller != null) {
                controller.setData(data);
            }

            // Set the new scene on the main stage
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


