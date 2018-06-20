
package com.easycode.spocom.java.controllers;

import com.jfoenix.controls.JFXDialog;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

public class AccueilController implements Initializable {

    @FXML // Root componant
    private StackPane root;
    
    public static JFXDialog aboutDialog;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            AnchorPane aboutPane = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/About.fxml"));
            aboutDialog = new JFXDialog(root, aboutPane, JFXDialog.DialogTransition.CENTER);
        } catch (IOException ex) {
            System.out.println("Error msg : " + ex.getMessage());
        }
    }    
        
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
    
}
