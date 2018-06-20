package com.easycode.spocom.java.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class NouveauCompetitionController implements Initializable {

    @FXML // Line of progress of steps
    private Line line1, line2, line3;
    @FXML // Circle of progress of steps
    private Circle cir1, cir2, cir3;
    
    @FXML
    private VBox infoComPane, infoCategoryPane, infoAthletePane;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
    
    @FXML // action of button suivant of competition information
    private void suivantInfosCom() {
        infoComPane.setVisible(false);
        infoCategoryPane.setVisible(true);
        
        line1.setStyle("-fx-stroke: #2196f3");
        cir1.setStyle("-fx-fill: #2196f3");
        line2.setStyle("-fx-stroke: #FFF");
        cir2.setStyle("-fx-fill: #FFF");
        line3.setStyle("-fx-stroke: #FFF");
        cir3.setStyle("-fx-fill: #FFF");
    }
    
    @FXML // action of button suivant of Category information
    private void suivantInfosCategory() {
        //infoCategoryPane.setVisible(false);
        //infoAthletePane.setVisible(true);
        
        line1.setStyle("-fx-stroke: #2196f3");
        cir1.setStyle("-fx-fill: #2196f3");
        line2.setStyle("-fx-stroke: #2196f3");
        cir2.setStyle("-fx-fill: #2196f3");
        line3.setStyle("-fx-stroke: #FFF");
        cir3.setStyle("-fx-fill: #FFF");
    }
    
    @FXML // action of button suivant of Athele information
    private void suivantAthele() {
        
    }
}
