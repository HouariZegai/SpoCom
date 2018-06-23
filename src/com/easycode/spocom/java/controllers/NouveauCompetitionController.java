package com.easycode.spocom.java.controllers;

import static com.easycode.spocom.java.controllers.form.AthleteFormController.formTypeIsAdd;
import com.easycode.spocom.java.dao.db.CompetitionDao;
import com.easycode.spocom.java.dao.vo.Athlete;
import com.easycode.spocom.java.dao.vo.Categorie;
import com.easycode.spocom.java.dao.vo.Competition;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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

    public static ObservableList<TableAthlete> dataTableAthlete;

    /* End Infos Part */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dataTableAthlete = FXCollections.observableArrayList();
        initializeTableAthlete();
        initiliazeCombo();
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
        if (editionComField.getText().trim().isEmpty() || typeComField.getText().trim().isEmpty()
                || dateComPicker.getValue() == null || lieuComField.getText().trim().isEmpty()) {
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
        Competition competition = new Competition();
        competition.setEdition(editionComField.getText().trim().toLowerCase());
        competition.setType(typeComField.getText().trim().toLowerCase());
        competition.setDate(Date.valueOf(dateComPicker.getValue()));
        competition.setLieu(lieuComField.getText().trim().toLowerCase());

        Categorie categorie = new Categorie();
        categorie.setYear1Bengemine(year1Bengemine.getSelectionModel().getSelectedItem());
        categorie.setYear2Bengemine(year2Bengemine.getSelectionModel().getSelectedItem());
        categorie.setYear1Minime(year1Minime.getSelectionModel().getSelectedItem());
        categorie.setYear2Minime(year2Minime.getSelectionModel().getSelectedItem());
        categorie.setYear1Cadet(year1Cadet.getSelectionModel().getSelectedItem());
        categorie.setYear2Cadet(year2Cadet.getSelectionModel().getSelectedItem());
        categorie.setYear1Junior(year1Junior.getSelectionModel().getSelectedItem());
        categorie.setYear2Junior(year2Junior.getSelectionModel().getSelectedItem());
        categorie.setYear1Senior(year1Senior.getSelectionModel().getSelectedItem());
        categorie.setYear2Senior(year2Senior.getSelectionModel().getSelectedItem());

        competition.setCategory(categorie);

        List<Athlete> athletes = new LinkedList<>();

        if (dataTableAthlete != null) {

            for (int i = 0; i < dataTableAthlete.size(); i++) {
                TableAthlete item = dataTableAthlete.get(i);

                Athlete athlete = new Athlete();
                athlete.setNom(item.getNom());
                athlete.setPrenom(item.getNom());
                athlete.setDateNaiss(item.getDateNaiss());
                athlete.setSexe(item.getSexe());
                athlete.setClub(item.getClub());
                athlete.setCodeWilaya(item.getCodeWilaya());
                athlete.setObservation(item.getObservation());

                athletes.add(athlete);
            }
            competition.setAthletes(athletes);

        }

        int status = new CompetitionDao().addCompetition(competition);

        if (status == 1) {
            System.out.println("Connection failed !");
            return;
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
    public class TableAthlete extends RecursiveTreeObject<TableAthlete> {

        private StringProperty nom;
        private StringProperty prenom;
        private StringProperty dateNaiss;
        private StringProperty sexe;
        private StringProperty club;
        private StringProperty codeWilaya;
        private StringProperty observation;

        public TableAthlete() {

        }

        public TableAthlete(String nom, String prenom, String dataNaiss, boolean sexe, String club,
                String codeWilaya, boolean observation) {
            this.nom = new SimpleStringProperty(nom);
            this.prenom = new SimpleStringProperty(prenom);
            this.dateNaiss = new SimpleStringProperty(dataNaiss);
            this.sexe = new SimpleStringProperty((sexe) ? "Homme" : "Femme");
            this.club = new SimpleStringProperty(club);
            this.codeWilaya = new SimpleStringProperty(codeWilaya);
            this.observation = new SimpleStringProperty((observation) ? "Ind" : "Eq");
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
            this.sexe = new SimpleStringProperty((sexe) ? "Homme" : "Femme");
        }

        public void setClub(String club) {
            this.club = new SimpleStringProperty(club);
        }

        public void setCodeWilaya(String codeWilaya) {
            this.codeWilaya = new SimpleStringProperty(codeWilaya);
        }

        public void setObservation(boolean observation) {
            this.observation = new SimpleStringProperty((observation) ? "Ind" : "Eq");
        }

        public String getNom() {
            return nom.get();
        }

        public String getPrenom() {
            return prenom.get();
        }

        public Date getDateNaiss() {
            return Date.valueOf(dateNaiss.get());
        }

        public boolean getSexe() {
            return "Homme".equals(sexe.get());
        }

        public String getClub() {
            return club.get();
        }

        public int getCodeWilaya() {
            return Integer.parseInt(codeWilaya.get());
        }

        public boolean getObservation() {
            return observation.get().equals("Ind");
        }

    }

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
        formTypeIsAdd = true;
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

        formTypeIsAdd = false;

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
