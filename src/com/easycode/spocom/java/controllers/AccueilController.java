package com.easycode.spocom.java.controllers;

import com.jfoenix.controls.JFXDialog;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AccueilController implements Initializable {

    @FXML // Root componant
    private StackPane root;

    public static JFXDialog aboutDialog;

    private VBox nouveauCompetitionWindow;

    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        try {
            AnchorPane aboutPane = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/About.fxml"));
            nouveauCompetitionWindow = FXMLLoader.load(getClass().
                    getResource("/com/easycode/spocom/resources/views/NouveauCompetition.fxml"));
            aboutDialog = new JFXDialog(root, aboutPane, JFXDialog.DialogTransition.CENTER);
        } catch (IOException ex) {
            System.out.println("Error msg : " + ex.getMessage());
        }
    }

    @FXML
    private void boxNewComp() {
        stage = (Stage) root.getScene().getWindow();

        //create a new scene with root and set the stage
        Scene scene = new Scene(nouveauCompetitionWindow);
        stage.setScene(scene);
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
