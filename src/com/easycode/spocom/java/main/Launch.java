
package com.easycode.spocom.java.main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launch extends Application {
    public static Stage stage = null;
    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/Accueil.fxml"));
        } catch (IOException ex) {
            System.out.println("Error msg: " + ex.getMessage());
        }
        Scene scene = new Scene(root);
        
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        Launch.stage = stage;
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
