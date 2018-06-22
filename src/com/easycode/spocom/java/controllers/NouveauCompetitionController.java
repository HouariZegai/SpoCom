package com.easycode.spocom.java.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NouveauCompetitionController implements Initializable {

    @FXML
    private StackPane root;
    
    @FXML
    private VBox infoComPane, infoCategoryPane, infoAthletePane;

    @FXML // Line of progress of steps
    private Line line1, line2, line3;
    @FXML // Circle of progress of steps
    private Circle cir1, cir2, cir3;

    @FXML
    private Label titleStep;

    /* Start Athlete Part */
    @FXML
    private JFXTextField searchAthleteField;
    @FXML
    private JFXComboBox comboSearchAthlete;

    @FXML
    private JFXTreeTableView tableAthlete;

    private JFXTreeTableColumn<Athlete, String> nDosCol, nomCol, prenomCol, dateNaissCol, sexeCol, clubCol, codeWilayaCol,
            observationCol;

    public static ObservableList<Athlete> dataTableAthlete;

    public static JFXDialog addAthleteDialog;
    VBox addAthletePane;
    
    /* End Athlete Part */
    
    /* Start Infos Part */
    
    @FXML
    private JFXTextField edittionField;

    /* End Infos Part */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataTableAthlete = FXCollections.observableArrayList();
        initializeTableAthlete();
        comboSearchAthlete.getItems().addAll("N°Dos", "Nom", "Prenom", "Date Naissance", "Sexe", "Club", "Code Wilaya", "Eq/Ind");

        addAthletePane = null;
        try {
            addAthletePane = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/form/AddAthlete.fxml"));
        } catch (IOException ex) {
            System.out.println("Error msg(IOException): " + ex.getMessage());
        }
        addAthleteDialog = new JFXDialog(root, addAthletePane, JFXDialog.DialogTransition.CENTER);
    }

    @FXML
    private void goToInfosCom() {
        infoComPane.setVisible(true);
        infoCategoryPane.setVisible(false);
        infoAthletePane.setVisible(false);

        line1.setStyle("-fx-stroke: #2196f3");
        cir1.setStyle("-fx-fill: #FFF");
        line2.setStyle("-fx-stroke: #FFF");
        cir2.setStyle("-fx-fill: #FFF");
        line3.setStyle("-fx-stroke: #FFF");
        cir3.setStyle("-fx-fill: #FFF");
        titleStep.setText("Information Competition");
    }

    @FXML // action of button suivant of Category information
    private void goToInfoCategorie() {

        infoComPane.setVisible(false);
        infoCategoryPane.setVisible(true);
        infoAthletePane.setVisible(false);

        line1.setStyle("-fx-stroke: #2196f3");
        cir1.setStyle("-fx-fill: #2196f3");
        line2.setStyle("-fx-stroke: #2196f3");
        cir2.setStyle("-fx-fill: #FFF");
        line3.setStyle("-fx-stroke: #FFF");
        cir3.setStyle("-fx-fill: #FFF");
        titleStep.setText("Information Catégorie");
    }

    @FXML // action of button suivant of Athele information
    private void goToAthlete() {
        infoAthletePane.setVisible(false);
        infoCategoryPane.setVisible(false);
        infoAthletePane.setVisible(true);

        line1.setStyle("-fx-stroke: #2196f3");
        cir1.setStyle("-fx-fill: #2196f3");
        line2.setStyle("-fx-stroke: #2196f3");
        cir2.setStyle("-fx-fill: #2196f3");
        line3.setStyle("-fx-stroke: #2196f3");
        cir3.setStyle("-fx-fill: #FFF");
        titleStep.setText("Information Athlète");
    }

    /* Start Athlete Part */
    
    public class Athlete extends RecursiveTreeObject<Athlete> {

        private StringProperty nDos;
        private StringProperty nom;
        private StringProperty prenom;
        private StringProperty dateNaiss;
        private StringProperty sexe;
        private StringProperty club;
        private StringProperty codeWilaya;
        private StringProperty observation;

        public Athlete() {
            
        }
        
        public Athlete(String nDos, String nom, String prenom, String dataNaiss, boolean sexe, String club, 
                String codeWilaya, boolean observation) {
            this.nDos = new SimpleStringProperty(String.valueOf(nDos));
            this.nom = new SimpleStringProperty(nom);
            this.prenom = new SimpleStringProperty(prenom);
            this.dateNaiss = new SimpleStringProperty(dataNaiss);
            this.sexe = new SimpleStringProperty((sexe)? "Homme" : "Femme");
            this.club = new SimpleStringProperty(club);
            this.codeWilaya = new SimpleStringProperty(codeWilaya);
            this.observation = new SimpleStringProperty((observation)? "Ind" : "Eq");
        }
        
        public void setNDos(String nDos) {
            this.nDos = new SimpleStringProperty(nDos);
        }

        public void setNom(String nom) {
            this.nom = new SimpleStringProperty(nom);
        }

        public void setPrenom(String prenom) {
            this.prenom = new SimpleStringProperty(prenom);
        }

        public void setDateNaiss(String dateNaiss) {
            this.dateNaiss = new SimpleStringProperty(dateNaiss);
        }

        public void setSexe(boolean sexe) {
            this.sexe = new SimpleStringProperty((sexe)? "Homme" : "Femme");
        }

        public void setClub(String club) {
            this.club = new SimpleStringProperty(club);
        }

        public void setCodeWilaya(String codeWilaya) {
            this.codeWilaya = new SimpleStringProperty(codeWilaya);
        }

        public void setObservation(boolean observation) {
            this.observation = new SimpleStringProperty((observation)? "Ind" : "Eq");
        }
        
    }

    private void initializeTableAthlete() {
        nDosCol = new JFXTreeTableColumn<>("N°Dos");
        nDosCol.setPrefWidth(100);
        nDosCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Athlete, String> param) -> param.getValue().getValue().nDos);

        nomCol = new JFXTreeTableColumn<>("Nom");
        nomCol.setPrefWidth(130);
        nomCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Athlete, String> param) -> param.getValue().getValue().nom);

        prenomCol = new JFXTreeTableColumn<>("Prenom");
        prenomCol.setPrefWidth(140);
        prenomCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Athlete, String> param) -> param.getValue().getValue().prenom);

        dateNaissCol = new JFXTreeTableColumn<>("Date Naissance");
        dateNaissCol.setPrefWidth(130);
        dateNaissCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Athlete, String> param) -> param.getValue().getValue().dateNaiss);

        sexeCol = new JFXTreeTableColumn<>("Sexe");
        sexeCol.setPrefWidth(100);
        sexeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Athlete, String> param) -> param.getValue().getValue().sexe);

        clubCol = new JFXTreeTableColumn<>("Club");
        clubCol.setPrefWidth(150);
        clubCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Athlete, String> param) -> param.getValue().getValue().club);

        codeWilayaCol = new JFXTreeTableColumn<>("Code Wilaya");
        codeWilayaCol.setPrefWidth(100);
        codeWilayaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Athlete, String> param) -> param.getValue().getValue().codeWilaya);

        observationCol = new JFXTreeTableColumn<>("Eq/Ind");
        observationCol.setPrefWidth(80);
        observationCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<Athlete, String> param) -> param.getValue().getValue().observation);

        tableAthlete.getColumns().addAll(nDosCol, nomCol, prenomCol, dateNaissCol, sexeCol, clubCol, codeWilayaCol, observationCol);
        tableAthlete.setShowRoot(false);

        final TreeItem<Athlete> treeItem = new RecursiveTreeItem<>(dataTableAthlete, RecursiveTreeObject::getChildren);
        tableAthlete.setRoot(treeItem);

        searchAthleteField.textProperty().addListener(e -> {
            filterSearchTable();
        });

        comboSearchAthlete.setOnAction(e -> {
            filterSearchTable();
        });
    }

    private void filterSearchTable() {
        tableAthlete.setPredicate(new Predicate<TreeItem<Athlete>>() {
            @Override
            public boolean test(TreeItem<Athlete> athlete) {
                switch (comboSearchAthlete.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        return athlete.getValue().nDos.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 1:
                        return athlete.getValue().nom.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 2:
                        return athlete.getValue().prenom.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 3:
                        return athlete.getValue().dateNaiss.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 4:
                        return athlete.getValue().sexe.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 5:
                        return athlete.getValue().club.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 6:
                        return athlete.getValue().codeWilaya.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 7:
                        return athlete.getValue().observation.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    default:
                        return athlete.getValue().nDos.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase())
                                || athlete.getValue().nom.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase())
                                || athlete.getValue().prenom.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase())
                                || athlete.getValue().dateNaiss.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase())
                                || athlete.getValue().sexe.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase())
                                || athlete.getValue().club.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase())
                                || athlete.getValue().codeWilaya.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase())
                                || athlete.getValue().observation.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                }
            }
        });
    }

    @FXML
    private void btnAddAthlete() {
        addAthleteDialog.show();
    }

    @FXML
    private void btnEditAthlete() {

    }

    @FXML
    private void btnRemoveAthlete() {
        int index = tableAthlete.getSelectionModel().getSelectedIndex();
        if (index  < 0) {
            System.out.println("Index is null !");
            return;
        }
        
        JFXDialogLayout content = new JFXDialogLayout();
        Text headerText = new Text("Confirmation");
        Text contentText = new Text("Vous voulez supprimer cette Athlète ?");
        headerText.setStyle("-fx-font-size: 19px");
        contentText.setStyle("-fx-font-size: 18px");

        content.setHeading(headerText);
        content.setBody(contentText);

        JFXDialog dialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);

        JFXButton btnOk = new JFXButton("Oui");
        JFXButton btnNo = new JFXButton("Non");
        btnOk.getStyleClass().add("btn-dialog");
        btnNo.getStyleClass().add("btn-dialog");
        
        btnOk.setOnAction(e -> {
            dataTableAthlete.remove(index);
            Notifications notification = Notifications.create()
                        .title("You Successfuly removed Athlete !")
                        .graphic(new ImageView(new Image("/com/easycode/spocom/resources/images/icons/valid.png")))
                        .hideAfter(Duration.millis(2000))
                        .position(Pos.BOTTOM_RIGHT);
                notification.darkStyle();
                notification.show();
            dialog.close();
        });
        
        btnNo.setOnAction(e -> {
            dialog.close();
        });


        content.setActions(btnOk, btnNo);
        StackPane stackpane = new StackPane();

        dialog.getStylesheets().add("/com/easycode/spocom/resources/css/main.css");
        dialog.show();
        
        // This line for disable removing after delete until new selection
        tableAthlete.getSelectionModel().select(null);
    }

    @FXML
    private void btnDeleteAllAthlete() {
        ObservableList o = FXCollections.observableArrayList(dataTableAthlete);
        o.forEach(dataTableAthlete::remove);
    }
    
    /* End Athlete Part */
}
