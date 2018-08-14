package com.easycode.spocom.java.controllers;

import com.easycode.spocom.java.dao.db.CompetitionDao;
import com.easycode.spocom.java.dao.vo.Athlete;
import com.easycode.spocom.java.dao.vo.Competition;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
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

    private JFXSnackbar toastMsg;
    
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
    private List<Competition> availableCompetition;
    
    // id of selected competition
    private int selectedCompetitionId;
    private Map<Integer, Athlete> athleteOfCompetition;
    
    @FXML
    private JFXTextField dosScoreField;

    @FXML
    private JFXTreeTableView tableClassement;
    // rows (data) of table
    TreeItem<TableAthlete> treeItem = null;
    // Data of classement table
    private ObservableList<TableAthlete> dataAthlete;

    private JFXTreeTableColumn<TableAthlete, String> nDosCol, nomCol, prenomCol, dateNaissCol, clubCol, codeWilayaCol,
            observationCol;

    /* End Classement competition part */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        availableCompetition = new CompetitionDao().getAvailableCompetition();
        
        initializeDialog();
        initializeCombo();
        initializeTableAthlete();
        
        dataAthlete = FXCollections.observableArrayList();
        
        toastMsg = new JFXSnackbar(root);
        toastMsg.getStylesheets().add("/com/easycode/spocom/resources/css/main.css");

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
        
    }

    private void initializeCombo() {
        // Initialize ComboBox

        if (availableCompetition != null) {
            availableCompetition.forEach((item) -> {
                comboSelectCom.getItems().add(item.getType());
            });
        }

        comboSelectCom.setOnAction(e -> {
            availableCompetition.forEach((Competition item) -> {
                if (comboSelectCom.getSelectionModel().getSelectedItem().equalsIgnoreCase(item.getType())) {
                    lblTypeCom.setText(item.getType());
                    lblEdition.setText("" + item.getEdition());
                    lblLieu.setText(item.getLieu());
                    lblDate.setText(item.getDate().toString());
                    selectedCompetitionId = item.getIdCom();
                    return;
                }
            });
        });
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
    public void btnSuivant() {
        if(comboSelectCom.getSelectionModel().getSelectedItem() == null) {
            toastMsg.show("Svp, selectionner un competition pour continuer !", 3000);
            return;
        }
        athleteOfCompetition = new CompetitionDao().getAthleteOfCompetition(selectedCompetitionId);
        selectPane.setVisible(false);
        saveComPane.setVisible(true);
    }

    @FXML
    public void btnBackToHome() {
        if (backToHomeDialog.isVisible()) {
            return;
        }

        backToHomeDialog.show();
    }

    /* Start Classement competition part */

    private void initializeTableAthlete() {

        nDosCol = new JFXTreeTableColumn<>("N°Dos");
        nDosCol.setPrefWidth(100);
        nDosCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().nDos);

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

        codeWilayaCol = new JFXTreeTableColumn<>("C.W");
        codeWilayaCol.setPrefWidth(80);
        codeWilayaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().codeWilaya);

        observationCol = new JFXTreeTableColumn<>("Eq/Ind");
        observationCol.setPrefWidth(80);
        observationCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().observation);
        
        tableClassement.getColumns().addAll(nDosCol, nomCol, prenomCol, dateNaissCol, clubCol, codeWilayaCol, observationCol);
        tableClassement.setShowRoot(false);
    }

    @FXML
    public void addScore() {
        if (!dosScoreField.getText().matches("[0-9]+")) {
            return;
        }
        
        Athlete athleteArrive = athleteOfCompetition.get(Integer.parseInt(dosScoreField.getText()));
        if(athleteArrive != null) {
            dataAthlete.add(new TableAthlete(athleteArrive.getnDos(), athleteArrive.getNom(),
            athleteArrive.getPrenom(), athleteArrive.getDateNaiss(), athleteArrive.getSexe(), athleteArrive.getClub(), 
                    athleteArrive.getCodeWilaya(), athleteArrive.getObs()));

            treeItem = new RecursiveTreeItem<>(dataAthlete, RecursiveTreeObject::getChildren);
            tableClassement.setRoot(treeItem);
        }
        
        dosScoreField.setText("");
    }

    @FXML
    public void onOtherCom() {
        List<Integer> numbersDos = new ArrayList<>();
        for (int i = 0; i < dataAthlete.size(); i++) {
            numbersDos.add(dataAthlete.get(i).getNDos());
            athleteOfCompetition.remove(numbersDos.get(i));
        }
        boolean status = new CompetitionDao().addClassement(selectedCompetitionId, numbersDos);
        if(status) {
            System.out.println("Inserted without any error !");
        }
        
        
        
        // Make table empty
        dataAthlete.clear();
        treeItem = new RecursiveTreeItem<>(dataAthlete, RecursiveTreeObject::getChildren);
        tableClassement.setRoot(treeItem);
        
    }
    
    /* End Classement competition part */
    
}
