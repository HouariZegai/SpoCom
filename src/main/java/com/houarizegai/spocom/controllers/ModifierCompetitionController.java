package com.houarizegai.spocom.controllers;

import com.houarizegai.spocom.controllers.form.AthleteFormController;
import com.houarizegai.spocom.dao.db.CompetitionDao;
import com.houarizegai.spocom.dao.vo.Athlete;
import com.houarizegai.spocom.dao.vo.CompetitionInfo;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class ModifierCompetitionController implements Initializable {

    /* Start all part */
    @FXML
    private StackPane root;

    @FXML
    private JFXButton btnFinish;
    @FXML
    private VBox selectPane;
    @FXML
    private JFXTabPane editPane;
    private JFXDialog backToHomeDialog;

    /* End all part */
    
    /* Start Select part */
    
    @FXML
    private JFXComboBox<String> comboSelectCom;

    @FXML
    private Label lblEdition, lblTypeCom, lblDate, lblLieu;
    
    private List<CompetitionInfo> availableCompetition;
    private CompetitionInfo selectedCompetition;
    
    /* End Select part */
    
    /* Start Edit part */
    
    @FXML
    private JFXTextField editionComField, typeComField, lieuComField;
    @FXML
    private JFXDatePicker dateComPicker;

    // Information of category
    @FXML
    private JFXComboBox<Integer> year1Bengemine, year2Bengemine, year1Minime, year2Minime, year1Cadet, year2Cadet,
            year1Junior, year2Junior, year1Senior, year2Senior;

    // Athlete part
    @FXML
    private JFXTextField searchAthleteField;
    @FXML
    private JFXComboBox comboSearchAthlete;

    @FXML
    private JFXTreeTableView tableAthlete;

    private JFXTreeTableColumn<TableAthlete, String> nomCol, prenomCol, dateNaissCol, sexeCol, clubCol, codeWilayaCol,
            observationCol;
    
    @FXML
    private VBox athleteFormPane;
    
    public static JFXDialog athleteFormDialog;
    
    public static ObservableList<TableAthlete> dataTableAthlete;
    
    // Athlete selected to modifier
    public static Athlete selectedAhtlete;
    public static int indexAthleteSelected;
    
    /* End Edit part */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        availableCompetition = new CompetitionDao().getAvailableCompetition();
        dataTableAthlete = FXCollections.observableArrayList();
        
        initializeTableAthlete();
        initiliazeCombo();
        initializeDialog();
        AthleteFormController.isNewCompetitionCall = false;
    }

    private void initializeDialog() {
        JFXDialogLayout content = new JFXDialogLayout();
        Text headerText = new Text("Confirmation");
        Text contentText = new Text("Sauvgader les données avant fermer ?");
        headerText.setStyle("-fx-font-size: 19px");
        contentText.setStyle("-fx-font-size: 18px");

        content.setHeading(headerText);
        content.setBody(contentText);

        backToHomeDialog = new JFXDialog(root, content, JFXDialog.DialogTransition.CENTER);

        JFXButton btnOk = new JFXButton("Ok");
        JFXButton btnNo = new JFXButton("Non");
        JFXButton btnCancel = new JFXButton("Annuler");
        btnOk.getStyleClass().add("btn-dialog");
        btnNo.getStyleClass().add("btn-dialog");
        btnCancel.getStyleClass().add("btn-dialog");

        btnCancel.setOnAction(e -> {
            try {
                StackPane accueilPane = FXMLLoader.load(getClass().getResource("/views/Accueil.fxml"));
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

        btnOk.setOnAction(e -> {
            // Save data before closing ?
            System.out.println("Save data before closing ?");

            try {
                StackPane accueilPane = FXMLLoader.load(getClass().getResource("/views/Accueil.fxml"));
                Stage stage = (Stage) root.getScene().getWindow();

                //create a new scene with root and set the stage
                Scene scene = new Scene(accueilPane);
                stage.setScene(scene);

            } catch (IOException ioe) {
                System.out.println("Error msg(IOException): " + ioe.getMessage());
            }
            backToHomeDialog.close();
        });

        content.setActions(btnOk, btnNo, btnCancel);

        backToHomeDialog.getStylesheets().add("/css/main.css");
    }

    private void initiliazeCombo() {

        for (int i = 1930; i < 2015; i++) {
            year1Bengemine.getItems().add(i);
            year2Bengemine.getItems().add(i);
            year1Minime.getItems().add(i);
            year2Minime.getItems().add(i);
            year1Cadet.getItems().add(i);
            year2Cadet.getItems().add(i);
            year1Junior.getItems().add(i);
            year2Junior.getItems().add(i);
            year1Senior.getItems().add(i);
            year2Senior.getItems().add(i);

        }
        
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
        
        comboSearchAthlete.getItems().addAll("Nom", "Prenom", "Date Naissance", "Sexe", "Club", "Code Wilaya", "Eq/Ind");
    }

    private void initializeTableAthlete() {
        System.out.println("After 1");
        nomCol = new JFXTreeTableColumn<>("Nom");
        nomCol.setPrefWidth(130);
        nomCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().nom);
        System.out.println("After 2");

        prenomCol = new JFXTreeTableColumn<>("Prenom");
        prenomCol.setPrefWidth(140);
        prenomCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().prenom);

        dateNaissCol = new JFXTreeTableColumn<>("Date Naissance");
        dateNaissCol.setPrefWidth(130);
        dateNaissCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().dateNaiss);

        sexeCol = new JFXTreeTableColumn<>("Sexe");
        sexeCol.setPrefWidth(100);
        sexeCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().sexe);

        clubCol = new JFXTreeTableColumn<>("Club");
        clubCol.setPrefWidth(150);
        clubCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().club);

        codeWilayaCol = new JFXTreeTableColumn<>("Code Wilaya");
        codeWilayaCol.setPrefWidth(100);
        codeWilayaCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().codeWilaya);

        observationCol = new JFXTreeTableColumn<>("Eq/Ind");
        observationCol.setPrefWidth(80);
        observationCol.setCellValueFactory((TreeTableColumn.CellDataFeatures<TableAthlete, String> param) -> param.getValue().getValue().observation);

        tableAthlete.getColumns().addAll(nomCol, prenomCol, dateNaissCol, sexeCol, clubCol, codeWilayaCol, observationCol);
        tableAthlete.setShowRoot(false);
        
        TreeItem<TableAthlete> treeItem = null;
        try {
            treeItem = new RecursiveTreeItem<>(dataTableAthlete, RecursiveTreeObject::getChildren);
        } catch(Exception e) {
            System.out.println("Exception : No Element in the List");
        }
        
        tableAthlete.setRoot(treeItem);
        searchAthleteField.textProperty().addListener(e -> {
            filterSearchTable();
        });

        comboSearchAthlete.setOnAction(e -> {
            filterSearchTable();
        });
    }

    private void filterSearchTable() {
        tableAthlete.setPredicate(new Predicate<TreeItem<TableAthlete>>() {
            @Override
            public boolean test(TreeItem<TableAthlete> athlete) {
                switch (comboSearchAthlete.getSelectionModel().getSelectedIndex()) {
                    case 0:
                        return athlete.getValue().nom.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 1:
                        return athlete.getValue().prenom.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 2:
                        return athlete.getValue().dateNaiss.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 3:
                        return athlete.getValue().sexe.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 4:
                        return athlete.getValue().club.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 5:
                        return athlete.getValue().codeWilaya.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    case 6:
                        return athlete.getValue().observation.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase());
                    default:
                        return athlete.getValue().nom.getValue().toLowerCase().contains(searchAthleteField.getText().trim().toLowerCase())
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
    private void btnAccueil() {
        try {
            StackPane accueilPane = FXMLLoader.load(getClass().getResource("/views/Accueil.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();

            //create a new scene with root and set the stage
            Scene scene = new Scene(accueilPane);
            stage.setScene(scene);

        } catch (IOException ioe) {
            System.out.println("Error msg(IOException): " + ioe.getMessage());
        }
    }

    // Edit Part
    @FXML
    private void goToInfosCom() {

    }

    @FXML // action of button suivant of Category information
    private void goToInfoCategorie() {
        if (editionComField.getText().trim().isEmpty() || typeComField.getText().trim().isEmpty()
                || dateComPicker.getValue() == null || lieuComField.getText().trim().isEmpty()) {
            return;
        }

    }

    @FXML // action of button suivant of Athele information
    private void goToAthlete() {

        if (year1Bengemine.getSelectionModel().getSelectedItem() == null
                || year1Bengemine.getSelectionModel().getSelectedItem() == null
                || year1Minime.getSelectionModel().getSelectedItem() == null
                || year2Minime.getSelectionModel().getSelectedItem() == null
                || year1Cadet.getSelectionModel().getSelectedItem() == null
                || year2Cadet.getSelectionModel().getSelectedItem() == null
                || year1Junior.getSelectionModel().getSelectedItem() == null
                || year2Junior.getSelectionModel().getSelectedItem() == null
                || year1Senior.getSelectionModel().getSelectedItem() == null
                || year2Senior.getSelectionModel().getSelectedItem() == null) {
            return;
        }

    }

    @FXML
    public void btnSuivant() {
        if (comboSelectCom.getSelectionModel().getSelectedItem() == null) {
            return;
        }
        selectPane.setVisible(false);
        editPane.setVisible(true);
        btnFinish.setDisable(false);
    }

    @FXML
    private void btnAddAthlete() {
        AthleteFormController.fromTypeIsAdd = true;
        athleteFormPane = null;
        try {
            athleteFormPane = FXMLLoader.load(getClass().getResource("/views/form/AthleteForm.fxml"));
        } catch (IOException ex) {
            System.out.println("Error msg(IOException): " + ex.getMessage());
        }
        athleteFormDialog = new JFXDialog(root, athleteFormPane, JFXDialog.DialogTransition.CENTER);
        athleteFormDialog.show();
    }

    @FXML
    private void btnEditAthlete() {
        indexAthleteSelected = tableAthlete.getSelectionModel().getSelectedIndex();
        if (indexAthleteSelected < 0) {
            return;
        }

        AthleteFormController.fromTypeIsAdd = false;

        selectedAhtlete = new Athlete();
        selectedAhtlete.setNom(nomCol.getCellData(indexAthleteSelected));
        selectedAhtlete.setPrenom(prenomCol.getCellData(indexAthleteSelected));
        selectedAhtlete.setDateNaiss(Date.valueOf(dateNaissCol.getCellData(indexAthleteSelected)));
        selectedAhtlete.setSexe(sexeCol.getCellData(indexAthleteSelected).equalsIgnoreCase("Homme"));
        selectedAhtlete.setClub(clubCol.getCellData(indexAthleteSelected));
        selectedAhtlete.setCodeWilaya(Integer.parseInt(codeWilayaCol.getCellData(indexAthleteSelected)));
        selectedAhtlete.setObservation(observationCol.getCellData(indexAthleteSelected).equalsIgnoreCase("Ind"));

        athleteFormPane = null;
        try {
            athleteFormPane = FXMLLoader.load(getClass().getResource("/views/form/AthleteForm.fxml"));
        } catch (IOException ex) {
            System.out.println("Error msg(IOException): " + ex.getMessage());
        }
        athleteFormDialog = new JFXDialog(root, athleteFormPane, JFXDialog.DialogTransition.CENTER);
        athleteFormDialog.show();
    }

    @FXML
    private void btnRemoveAthlete() {
        int index = tableAthlete.getSelectionModel().getSelectedIndex();
        if (index < 0) {
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
                    .graphic(new ImageView(new Image("/images/icons/valid.png")))
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

        dialog.getStylesheets().add("/css/main.css");
        dialog.show();

        // This line for disable removing after delete until new selection
        tableAthlete.getSelectionModel().select(null);
    }

    @FXML
    private void btnDeleteAllAthlete() {
        ObservableList o = FXCollections.observableArrayList(dataTableAthlete);
        o.forEach(dataTableAthlete::remove);
    }

}
