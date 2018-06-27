package com.easycode.spocom.java.controllers;

import com.easycode.spocom.java.dao.db.CompetitionDao;
import com.easycode.spocom.java.dao.vo.Athlete;
import com.easycode.spocom.java.dao.vo.CompetitionInfo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AccederCompetitionController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private VBox selectPane, saveComPane;

    private JFXDialog backToHomeDialog;

    /* Start select competition */
    
    @FXML
    private Label lblEdition, lblTypeCom, lblDate, lblLieu;
    @FXML
    private JFXComboBox<String> comboSelectCom;

    /* End select competition */

    /* Start Classement competition part */
    
    @FXML
    private HBox boxDos;
    
    // Information of available competition
    private List<CompetitionInfo> availableCompetition;
    private CompetitionInfo selectedCompetition;
    private Map<Integer, Athlete> athleteOfCompetition;

    @FXML
    private JFXComboBox<String> comboSelectCategory;
    @FXML
    private JFXToggleButton toggleSexe;

    @FXML
    private JFXTextField dosScoreField;

    @FXML
    private JFXTreeTableView tableClassement;

    private JFXTreeTableColumn<TableAthlete, String> nomCol, prenomCol, dateNaissCol, clubCol, codeWilayaCol,
            observationCol;

    private ObservableList<TableAthlete> dataBengemineH, dataBengemineF, dataMinimeH, dataMinimeF, dataCadetH, dataCadetF,
            dataJuniorH, dataJuniorF, dataSeniorH, dataSeniorF;

    /* End Classement competition part */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        availableCompetition = new CompetitionDao().getAvailableCompetition();
        
        initializeDialog();
        initializeCombo();
        initializeTableAthlete();
        initializeDataOfTable();
        
        selectPane.setOnKeyReleased(e -> {
            if(e.getCode().equals(KeyCode.ENTER)) {
                btnSuivant();
            }
        });
        
        dosScoreField.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                addScore();
            }
        });
        
        toggleSexe.setOnAction(e -> {
            toggleSexe.setText((toggleSexe.getText().equals("Homme")) ? "Femme" : "Homme");
        });
    }

    private void initializeCombo() {
        // Initialize ComboBox

        if (availableCompetition != null) {
            availableCompetition.forEach((item) -> {
                comboSelectCom.getItems().add(item.getType());
            });
        }

        comboSelectCom.setOnAction(e -> {
            availableCompetition.forEach((CompetitionInfo item) -> {
                if (comboSelectCom.getSelectionModel().getSelectedItem().equalsIgnoreCase(item.getType())) {
                    lblTypeCom.setText(item.getType());
                    lblEdition.setText(item.getEdition());
                    lblLieu.setText(item.getLieu());
                    lblDate.setText(item.getDate().toString());

                    selectedCompetition = item;
                    return;
                }
            });
        });

        comboSelectCategory.getItems().addAll("Bengemine", "Minime", "Cadet", "Junior", "Senior");
        comboSelectCategory.setOnAction(e -> {
            loadDataToTable();
            boxDos.setVisible(true);
        });
        toggleSexe.setOnAction(e -> {
            loadDataToTable();
        });
    }

    private void loadDataToTable() {
        // This function change to data of the table (Change category or sexe)

        final TreeItem<TableAthlete> treeItem;
        if (toggleSexe.isSelected()) {
            switch (comboSelectCategory.getSelectionModel().getSelectedItem()) {
                case "Bengemine":
                    treeItem = new RecursiveTreeItem<>(dataBengemineH, RecursiveTreeObject::getChildren);
                    tableClassement.setRoot(treeItem);
                    break;
                case "Minime":
                    treeItem = new RecursiveTreeItem<>(dataMinimeH, RecursiveTreeObject::getChildren);
                    tableClassement.setRoot(treeItem);
                    break;
                case "Cadet":
                    treeItem = new RecursiveTreeItem<>(dataCadetH, RecursiveTreeObject::getChildren);
                    tableClassement.setRoot(treeItem);
                    break;
                case "Junior":
                    treeItem = new RecursiveTreeItem<>(dataJuniorH, RecursiveTreeObject::getChildren);
                    tableClassement.setRoot(treeItem);
                    break;
                case "Senior":
                    treeItem = new RecursiveTreeItem<>(dataSeniorH, RecursiveTreeObject::getChildren);
                    tableClassement.setRoot(treeItem);
                    break;
            }
        } else {
            switch (comboSelectCategory.getSelectionModel().getSelectedItem()) {
                case "Bengemine":
                    treeItem = new RecursiveTreeItem<>(dataBengemineF, RecursiveTreeObject::getChildren);
                    tableClassement.setRoot(treeItem);
                    break;
                case "Minime":
                    treeItem = new RecursiveTreeItem<>(dataMinimeF, RecursiveTreeObject::getChildren);
                    tableClassement.setRoot(treeItem);
                    break;
                case "Cadet":
                    treeItem = new RecursiveTreeItem<>(dataCadetF, RecursiveTreeObject::getChildren);
                    tableClassement.setRoot(treeItem);
                    break;
                case "Junior":
                    treeItem = new RecursiveTreeItem<>(dataJuniorF, RecursiveTreeObject::getChildren);
                    tableClassement.setRoot(treeItem);
                    break;
                case "Senior":
                    treeItem = new RecursiveTreeItem<>(dataSeniorF, RecursiveTreeObject::getChildren);
                    tableClassement.setRoot(treeItem);
                    break;
            }
        }

    }
    
    private void initializeDataOfTable() {
        dataBengemineH = FXCollections.observableArrayList();
        dataBengemineF = FXCollections.observableArrayList();
        dataMinimeH = FXCollections.observableArrayList();
        dataMinimeF = FXCollections.observableArrayList();
        dataCadetH = FXCollections.observableArrayList();
        dataCadetF = FXCollections.observableArrayList();
        dataJuniorH = FXCollections.observableArrayList();
        dataJuniorF = FXCollections.observableArrayList();
        dataSeniorH = FXCollections.observableArrayList();
        dataSeniorF = FXCollections.observableArrayList();
    }
    
    private void initializeDialog() {
        JFXDialogLayout content = new JFXDialogLayout();
        Text headerText = new Text("Confirmation");
        Text contentText = new Text("Si vous avez quitter cette partie tout les traitement sera annuler ?");
        headerText.setStyle("-fx-font-size: 19px");
        contentText.setStyle("-fx-font-size: 18px");

        content.setHeading(headerText);
        content.setBody(contentText);

        backToHomeDialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);

        JFXButton btnOk = new JFXButton("Ok");
        JFXButton btnNo = new JFXButton("Non");
        btnOk.getStyleClass().add("btn-dialog");
        btnNo.getStyleClass().add("btn-dialog");

        btnOk.setOnAction(e -> {
            try {
                StackPane accueilPane = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/Accueil.fxml"));
                Stage stage = (Stage) root.getScene().getWindow();

                //create a new scene with root and set the stage
                Scene scene = new Scene(accueilPane);
                stage.setScene(scene);

            } catch (IOException ioe) {
                System.out.println("Error msg(IOException): " + ioe.getMessage());
            }
            backToHomeDialog.close();
        });

        btnNo.setOnAction(e -> {
            backToHomeDialog.close();
        });

        content.setActions(btnOk, btnNo);

        backToHomeDialog.getStylesheets().add("/com/easycode/spocom/resources/css/main.css");
    }

    @FXML
    private void btnSuivant() {
        if (comboSelectCom.getSelectionModel().getSelectedItem() == null) {
            return;
        }

        athleteOfCompetition = new CompetitionDao().getAthleteOfCompetition(selectedCompetition);

        selectPane.setVisible(false);
        saveComPane.setVisible(true);
    }

    @FXML
    private void btnBackToHome() {
        if (backToHomeDialog.isVisible()) {
            return;
        }

        backToHomeDialog.show();
    }

    /* Start Classement competition part */

    private void initializeTableAthlete() {

        nomCol = new JFXTreeTableColumn<>("Nom");
        nomCol.setPrefWidth(130);
        nomCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().nom);

        prenomCol = new JFXTreeTableColumn<>("Prenom");
        prenomCol.setPrefWidth(140);
        prenomCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().prenom);

        dateNaissCol = new JFXTreeTableColumn<>("Date Naissance");
        dateNaissCol.setPrefWidth(130);
        dateNaissCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().dateNaiss);

        clubCol = new JFXTreeTableColumn<>("Club");
        clubCol.setPrefWidth(150);
        clubCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().club);

        codeWilayaCol = new JFXTreeTableColumn<>("Code Wilaya");
        codeWilayaCol.setPrefWidth(100);
        codeWilayaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().codeWilaya);

        observationCol = new JFXTreeTableColumn<>("Eq/Ind");
        observationCol.setPrefWidth(80);
        observationCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().observation);

        tableClassement.getColumns().addAll(nomCol, prenomCol, dateNaissCol, clubCol, codeWilayaCol, observationCol);
        tableClassement.setShowRoot(false);

    }

    @FXML
    private void addScore() {
        if (dosScoreField.getText().trim().isEmpty()) {
            return;
        }

        TableAthlete athleteArrive = new TableAthlete();
        // Missing to add information about athlete arrive

        if (toggleSexe.isSelected()) {
            switch (comboSelectCategory.getSelectionModel().getSelectedItem()) {
                case "Bengemine":
                    dataBengemineH.add(athleteArrive);
                    break;
                case "Minime":
                    dataMinimeH.add(athleteArrive);
                    break;
                case "Cadet":
                    dataCadetH.add(athleteArrive);
                    break;
                case "Junior":
                    dataJuniorH.add(athleteArrive);
                    break;
                case "Senior":
                    dataSeniorH.add(athleteArrive);
                    break;
            }
        } else {
            switch (comboSelectCategory.getSelectionModel().getSelectedItem()) {
                case "Bengemine":
                    dataBengemineF.add(athleteArrive);
                    break;
                case "Minime":
                    dataMinimeF.add(athleteArrive);
                    break;
                case "Cadet":
                    dataCadetF.add(athleteArrive);
                    break;
                case "Junior":
                    dataJuniorF.add(athleteArrive);
                    break;
                case "Senior":
                    dataSeniorF.add(athleteArrive);
                    break;
            }
        }
        
        dosScoreField.setText("");
    }

    /* End Classement competition part */
    
}
