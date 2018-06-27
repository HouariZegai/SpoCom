
package com.easycode.spocom.java.main;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Launch extends Application {
    @Override
    public void start(Stage stage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/ModifierCompetition.fxml"));
        } catch (IOException ex) {
            System.out.println("Error msg: " + ex.getMessage());
        }
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.setTitle("SpoCom App");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
