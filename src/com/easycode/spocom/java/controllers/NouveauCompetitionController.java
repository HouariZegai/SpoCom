package com.easycode.spocom.java.controllers;

import com.easycode.spocom.java.controllers.form.AthleteFormController;
import com.easycode.spocom.java.dao.db.CompetitionDao;
import com.easycode.spocom.java.dao.vo.Athlete;
import com.easycode.spocom.java.dao.vo.Competition;
import com.easycode.spocom.java.dao.vo.CompetitionBuilder;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import java.sql.Date;
import java.util.LinkedList;
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
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class NouveauCompetitionController implements Initializable {

    @FXML
    private StackPane root;

    @FXML
    private VBox infoComPane, infoCategoryPane, infoAthletePane, finishPane;

    @FXML // Line of progress of steps
    private Line line1, line2, line3;
    @FXML // Circle of progress of steps
    private Circle cir1, cir2, cir3;
    
    @FXML
    private Label titleStep;

    private JFXSnackbar toastMsg;

    /* Start Athlete Part */
    
    @FXML
    private JFXTextField searchAthleteField;
    @FXML
    private JFXComboBox comboSearchAthlete;

    @FXML
    private JFXTreeTableView tableAthlete;

    private JFXTreeTableColumn<TableAthlete, String> nomCol, prenomCol, dateNaissCol, sexeCol, clubCol, codeWilayaCol,
            observationCol;

    public static JFXDialog athleteFormDialog;

    private VBox athleteFormPane;

    public static ObservableList<TableAthlete> dataTableAthlete;

    // Athlete selected to modifier
    public static Athlete selectedAhtlete;
    public static int indexAthleteSelected;

    /* End Athlete Part */
 
    /* Start Infos Part */
    
    @FXML
    private JFXTextField editionComField, typeComField, lieuComField;
    @FXML
    private JFXDatePicker dateComPicker;

    @FXML
    private JFXComboBox<Integer> year1Bengemine, year2Bengemine, year1Minime, year2Minime, year1Cadet, year2Cadet,
            year1Junior, year2Junior, year1Senior, year2Senior;

    /* End Infos Part */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataTableAthlete = FXCollections.observableArrayList();
        initializeTableAthlete();
        initiliazeCombo();

        toastMsg = new JFXSnackbar(root);
        toastMsg.getStylesheets().add("/com/easycode/spocom/resources/css/main.css");

        // Shortcut if Enter typed
        infoComPane.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                goToInfoCategorie();
            }
        });
        infoCategoryPane.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                goToAthlete();
            }
        });
        infoAthletePane.setOnKeyReleased(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                goToAthlete();
            }
        });

        AthleteFormController.isNewCompetitionCall = true;
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

        comboSearchAthlete.getItems().addAll("Nom", "Prenom", "Date Naissance", "Sexe", "Club", "Code Wilaya", "Eq/Ind");
    }

    @FXML
    private void goToInfosCom() {

        infoComPane.setVisible(true);
        infoCategoryPane.setVisible(false);
        infoAthletePane.setVisible(false);
        finishPane.setVisible(false);

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
        if (editionComField.getText().trim().isEmpty()) {
            toastMsg.show("Svp, Remplir l'Edition", 3000);
            return;
        }
        if (!editionComField.getText().matches("[0-9]+")) {
            toastMsg.show("l'Edition sera seulement nombre !", 3000);
            return;
        }
        if (typeComField.getText().trim().isEmpty()) {
            toastMsg.show("Svp, Remplir le Type de competition", 3000);
            return;
        }
        if (dateComPicker.getValue() == null) {
            toastMsg.show("Svp, Remplir la date de competition", 3000);
            return;
        }
        if (lieuComField.getText().trim().isEmpty()) {
            toastMsg.show("Svp, Remplir le lieu de competition", 3000);
            return;
        }

        infoComPane.setVisible(false);
        infoCategoryPane.setVisible(true);
        infoAthletePane.setVisible(false);
        finishPane.setVisible(false);

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

        if (year1Bengemine.getSelectionModel().getSelectedItem() == null
                || year1Bengemine.getSelectionModel().getSelectedItem() == null) {
            toastMsg.show("Svp, Selectionner l'année de Bengemine", 3000);
            return;
        }
        if (year1Minime.getSelectionModel().getSelectedItem() == null
                || year2Minime.getSelectionModel().getSelectedItem() == null) {
            toastMsg.show("Svp, Selectionner l'année de Minime", 3000);
            return;
        }
        if (year1Cadet.getSelectionModel().getSelectedItem() == null
                || year2Cadet.getSelectionModel().getSelectedItem() == null) {
            toastMsg.show("Svp, Selectionner l'année de Cadet", 3000);
            return;
        }
        if (year1Junior.getSelectionModel().getSelectedItem() == null
                || year2Junior.getSelectionModel().getSelectedItem() == null) {
            toastMsg.show("Svp, Selectionner l'année de Junior", 3000);
            return;
        }
        if (year1Senior.getSelectionModel().getSelectedItem() == null
                || year2Senior.getSelectionModel().getSelectedItem() == null) {
            toastMsg.show("Svp, Selectionner l'année de Senior", 3000);
            return;
        }
        
        infoAthletePane.setVisible(false);
        infoCategoryPane.setVisible(false);
        infoAthletePane.setVisible(true);
        finishPane.setVisible(false);

        line1.setStyle("-fx-stroke: #2196f3");
        cir1.setStyle("-fx-fill: #2196f3");
        line2.setStyle("-fx-stroke: #2196f3");
        cir2.setStyle("-fx-fill: #2196f3");
        line3.setStyle("-fx-stroke: #2196f3");
        cir3.setStyle("-fx-fill: #FFF");
        titleStep.setText("Information Athlète");
    }

    @FXML
    private void goToFinish() {
        infoAthletePane.setVisible(false);
        finishPane.setVisible(true);

        cir3.setStyle("-fx-fill: #2196f3");
    }

    @FXML
    private void btnFinish() {

        List<Athlete> athletes = new LinkedList<>();

        if (dataTableAthlete != null) {
            for (int i = 0; i < dataTableAthlete.size(); i++) {
                TableAthlete item = dataTableAthlete.get(i);

                Athlete athlete = new Athlete();
                athlete.setnDos(i + 1);
                athlete.setNom(item.getNom());
                athlete.setPrenom(item.getPrenom());
                athlete.setDateNaiss(item.getDateNaiss());
                athlete.setSexe(item.getSexe());
                athlete.setClub(item.getClub());
                athlete.setCodeWilaya(item.getCodeWilaya());
                athlete.setObs(item.getObservation());
                athletes.add(athlete);
            }
        }

        Competition competition = new CompetitionBuilder()
                .setEdition(Integer.parseInt(editionComField.getText().trim()))
                .setType(typeComField.getText().trim().toLowerCase())
                .setDate(Date.valueOf(dateComPicker.getValue()))
                .setLieu(lieuComField.getText().trim().toLowerCase())
                .setBengemine("" + year1Bengemine.getSelectionModel().getSelectedItem() + year2Bengemine.getSelectionModel().getSelectedItem())
                .setMinime("" + year1Minime.getSelectionModel().getSelectedItem() + year2Minime.getSelectionModel().getSelectedItem())
                .setCadet("" + year1Cadet.getSelectionModel().getSelectedItem() + year2Cadet.getSelectionModel().getSelectedItem())
                .setJunior("" + year1Junior.getSelectionModel().getSelectedItem() + year2Junior.getSelectionModel().getSelectedItem())
                .setSenior("" + year1Senior.getSelectionModel().getSelectedItem() + year2Senior.getSelectionModel().getSelectedItem())
                .setAthlete(athletes)
                .getCompetition();

        int status = new CompetitionDao().addCompetition(competition);

        if (status == -1) {
            System.out.println("Connection failed !");
            return;
        } else if (status == 1) {
            System.out.println("Error Insert !");
            return;
        } else {
            System.out.println("Successed added new competition ^^ !");
        }

        try {
            StackPane accueilPane = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/Accueil.fxml"));
            Stage stage = (Stage) root.getScene().getWindow();

            //create a new scene with root and set the stage
            Scene scene = new Scene(accueilPane);
            stage.setScene(scene);

        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }

    }
    
    /* Start Athlete Part */
    
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

        final TreeItem<TableAthlete> treeItem = new RecursiveTreeItem<>(dataTableAthlete, RecursiveTreeObject::getChildren);
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
    private void btnAddAthlete() {
        AthleteFormController.fromTypeIsAdd = true;
        athleteFormPane = null;
        try {
            athleteFormPane = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/form/AthleteForm.fxml"));
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
        selectedAhtlete.setObs(observationCol.getCellData(indexAthleteSelected).equalsIgnoreCase("Ind"));

        athleteFormPane = null;
        try {
            athleteFormPane = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/form/AthleteForm.fxml"));
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
    
    @FXML
    private void btnBackToHome() {
        JFXDialogLayout content = new JFXDialogLayout();
        Text headerText = new Text("Confirmation");
        Text contentText = new Text("Vous voulez quitter l'operation d'ajouter ?");
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
            try {
                StackPane accueilPane = FXMLLoader.load(getClass().getResource("/com/easycode/spocom/resources/views/Accueil.fxml"));
                Stage stage = (Stage) root.getScene().getWindow();

                //create a new scene with root and set the stage
                Scene scene = new Scene(accueilPane);
                stage.setScene(scene);

            } catch (IOException ioe) {
                System.out.println("Error msg(IOException): " + ioe.getMessage());
            }
            dialog.close();
        });

        btnNo.setOnAction(e -> {
            dialog.close();
        });

        content.setActions(btnOk, btnNo);
        StackPane stackpane = new StackPane();

        dialog.getStylesheets().add("/com/easycode/spocom/resources/css/main.css");
        dialog.show();
    }

}
