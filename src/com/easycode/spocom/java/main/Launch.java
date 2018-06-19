
package com.easycode.spocom.java.main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launch extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/Accueil.fxml"));
        } catch (IOException ex) {
            System.out.println("Error msg: " + ex.getMessage());
        }
        Scene scene = new Scene(root);
        
        primaryStage.setTitle("SpoCom App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
