
package com.easycode.spocom.java.controllers;

import com.easycode.spocom.java.main.Launch;
import com.jfoenix.controls.JFXDialog;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccueilController implements Initializable {

    @FXML // All Window
    private VBox root;
    
    @FXML
    private StackPane parentStack;
    
    @FXML // Bottom Window (without header)
    private AnchorPane parent;
    
    public static JFXDialog aboutDialog;
    
    // This two variables using to make stage Drageable
    private double xOffset = 0;
    private double yOffset = 0;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        makeStageDrageable();
        
        try {
            AnchorPane aboutPane = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/About.fxml"));
            aboutDialog = new JFXDialog(parentStack, aboutPane, JFXDialog.DialogTransition.CENTER);
        } catch (IOException ex) {
            System.out.println("Error msg : " + ex.getMessage());
        }
    }    
    
    private void makeStageDrageable() {
        root.setOnMousePressed((MouseEvent event) -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        root.setOnMouseDragged((MouseEvent event) -> {
            Launch.stage.setX(event.getScreenX() - xOffset);
            Launch.stage.setY(event.getScreenY() - yOffset);
            Launch.stage.setOpacity(0.7f);
        });
        root.setOnDragDone((e) -> {
            Launch.stage.setOpacity(1.0f);
        });
        root.setOnMouseReleased((e) -> {
            Launch.stage.setOpacity(1.0f);
        });

    }

    /* Start Header Part */
    
    @FXML
    private void close() {
        System.exit(0);
    }

    @FXML
    private void minimize(MouseEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setIconified(true);
    }
    
    /* End Header Part */
    
    /* Start Accuiel Part */
    
    @FXML
    private void boxNewComp() {

    }
    
    @FXML
    private void boxEditComp() {

    }

    @FXML
    private void boxGoToComp() {

    }

    @FXML
    private void boxViewResult() {

    }

    @FXML
    private void btnSettings() {

    }

    @FXML
    private void btnAbout() {
        if (aboutDialog.isVisible()) {
            return;
        }

        aboutDialog.show();
    }
    
    /* End Accuiel Part */
}
