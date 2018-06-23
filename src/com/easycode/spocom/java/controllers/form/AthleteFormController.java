package com.easycode.spocom.java.controllers.form;

import com.easycode.spocom.java.controllers.NouveauCompetitionController;
import com.easycode.spocom.java.controllers.NouveauCompetitionController.TableAthlete;
import static com.easycode.spocom.java.controllers.NouveauCompetitionController.athleteFormDialog;
import static com.easycode.spocom.java.controllers.NouveauCompetitionController.dataTableAthlete;
import static com.easycode.spocom.java.controllers.NouveauCompetitionController.indexAthleteSelected;
import static com.easycode.spocom.java.controllers.NouveauCompetitionController.selectedAhtlete;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

public class AthleteFormController implements Initializable {

    @FXML
    private VBox root;

    private JFXSnackbar toastErrorMsg;

    @FXML
    private JFXTextField nomField, prenomField, clubField;
    @FXML
    private JFXDatePicker dateNaissPicker;
    @FXML
    private JFXRadioButton rHomme, rFemme,
            rEq, rInd; // Radio Equipe, Radio Individual
    @FXML
    private JFXComboBox<String> comboCodeWilaya;

    @FXML
    private Label titleField;
    @FXML
    private JFXButton btnValid;

    public static boolean formTypeIsAdd; // Edit or Add

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initializeComboAndRadio();

        if (formTypeIsAdd) {
            titleField.setText("Ajouter Athlète");
            btnValid.setText("Ajouter");
        } else {
            titleField.setText("Modifier Athlète");
            btnValid.setText("Modifier");
            nomField.setText(selectedAhtlete.getNom());
            prenomField.setText(selectedAhtlete.getPrenom());
            dateNaissPicker.setValue(selectedAhtlete.getDateNaiss().toLocalDate());
            if(selectedAhtlete.isHomme())
                rHomme.setSelected(true);
            else
                rFemme.setSelected(true);
            clubField.setText(selectedAhtlete.getClub());
            comboCodeWilaya.getSelectionModel().select(selectedAhtlete.getCodeWilaya());
            if(selectedAhtlete.isInd())
                rInd.setSelected(true);
            else
                rEq.setSelected(true);
        }
        
        btnValid.setOnAction(e -> {
            if (formTypeIsAdd) {
                addAthlete();        
            } else {
                editAthlete();
            }
        });

        root.setOnKeyPressed(event -> { // Add Key Listener, if i click to the Enter Button Call btnLogin() method
            if (event.getCode().equals(KeyCode.ENTER)) {

            }
        });

        nomField.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (nomField.getText().length() > 25) {
                nomField.setText(nomField.getText().substring(0, 25));
                nomField.positionCaret(nomField.getText().length());
            }
        });
        prenomField.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (prenomField.getText().length() > 50) {
                prenomField.setText(prenomField.getText().substring(0, 50));
                prenomField.positionCaret(prenomField.getText().length());
            }
        });
        clubField.setOnKeyReleased(event -> { // this event for check max length of the answer area
            if (clubField.getText().length() > 50) {
                clubField.setText(clubField.getText().substring(0, 50));
                clubField.positionCaret(clubField.getText().length());
            }
        });

        toastErrorMsg = new JFXSnackbar(root);

    }

    private void initializeComboAndRadio() {
        for (int i = 1; i <= 48; i++) {
            comboCodeWilaya.getItems().add(String.valueOf(i));
        }

        ToggleGroup tbSexe = new ToggleGroup();
        rHomme.setToggleGroup(tbSexe);
        rFemme.setToggleGroup(tbSexe);

        ToggleGroup tbObservation = new ToggleGroup();
        rEq.setToggleGroup(tbObservation);
        rInd.setToggleGroup(tbObservation);
    }

    /* Start Add Account Action Part */
    @FXML
    private void btnClear() {
        nomField.setText("");
        prenomField.setText("");
        dateNaissPicker.setValue(null);
        clubField.setText("");
        comboCodeWilaya.getSelectionModel().select(null);
        rHomme.setSelected(true);
        rInd.setSelected(true);
    }

    private void addAthlete() { // Add New Student
        if (!nomField.getText().trim().toLowerCase().matches("[a-z0-9_]{4,}")) {
            toastErrorMsg.show("Erreur dans le Nom !", 1500);
            return;
        }
        if (!prenomField.getText().trim().toLowerCase().matches("[a-z0-9_]{4,}")) {
            toastErrorMsg.show("Erreur dans le prenom !", 1500);
            return;
        }
        if (dateNaissPicker.getValue() == null) {
            toastErrorMsg.show("Please remplir la date de naissance !", 1500);
            return;
        }
        if (!clubField.getText().trim().toLowerCase().matches("[a-z0-9_-]{3,}")) {
            toastErrorMsg.show("Erreur dans le club !", 1500);
            return;
        }
        if (comboCodeWilaya.getSelectionModel().getSelectedItem() == null) {
            toastErrorMsg.show("Svp, selectionner le code wilaya !", 1500);
            return;
        }

        TableAthlete athlete = new NouveauCompetitionController().new TableAthlete();
        athlete.setNom(nomField.getText().trim().toUpperCase());

        String prenom = prenomField.getText().trim().toLowerCase();
        prenom = prenom.substring(0, 1).toUpperCase() + prenom.substring(1);
        athlete.setPrenom(prenom);

        athlete.setDateNaiss(dateNaissPicker.getValue().toString());
        athlete.setSexe(rHomme.isSelected());
        athlete.setClub(clubField.getText().trim().toUpperCase());
        athlete.setCodeWilaya(comboCodeWilaya.getSelectionModel().getSelectedItem());
        athlete.setObservation(rInd.isSelected());
        dataTableAthlete.add(athlete);

        Notifications notification = Notifications.create()
                .title("Vous Avez Ajouter un Athlète !")
                .graphic(new ImageView(new Image("/com/easycode/spocom/resources/images/icons/valid.png")))
                .hideAfter(Duration.millis(2000))
                .position(Pos.BOTTOM_RIGHT);
        notification.darkStyle();
        notification.show();
        
        btnClear();
    }

    private void editAthlete() {
        if (!nomField.getText().trim().toLowerCase().matches("[a-z0-9_]{4,}")) {
            toastErrorMsg.show("Erreur dans le Nom !", 1500);
            return;
        }
        if (!prenomField.getText().trim().toLowerCase().matches("[a-z0-9_]{4,}")) {
            toastErrorMsg.show("Erreur dans le prenom !", 1500);
            return;
        }
        if (dateNaissPicker.getValue() == null) {
            toastErrorMsg.show("Please remplir la date de naissance !", 1500);
            return;
        }
        if (!clubField.getText().trim().toLowerCase().matches("[a-z0-9_-]{3,}")) {
            toastErrorMsg.show("Erreur dans le club !", 1500);
            return;
        }
        if (comboCodeWilaya.getSelectionModel().getSelectedItem() == null) {
            toastErrorMsg.show("Svp, selectionner le code wilaya !", 1500);
            return;
        }
        TableAthlete athlete = new NouveauCompetitionController().new TableAthlete();
        athlete.setNom(nomField.getText().trim().toUpperCase());

        String prenom = prenomField.getText().trim().toLowerCase();
        prenom = prenom.substring(0, 1).toUpperCase() + prenom.substring(1);
        athlete.setPrenom(prenom);

        athlete.setDateNaiss(dateNaissPicker.getValue().toString());
        athlete.setSexe(rHomme.isSelected());
        athlete.setClub(clubField.getText().trim().toUpperCase());
        athlete.setCodeWilaya(comboCodeWilaya.getSelectionModel().getSelectedItem());
        athlete.setObservation(rInd.isSelected());
        
        dataTableAthlete.set(indexAthleteSelected, athlete);
        
        Notifications notification = Notifications.create()
                .title("Vous Avez Modifier L'athlète !")
                .graphic(new ImageView(new Image("/com/easycode/spocom/resources/images/icons/valid.png")))
                .hideAfter(Duration.millis(2000))
                .position(Pos.BOTTOM_RIGHT);
        notification.darkStyle();
        notification.show();
        athleteFormDialog.close();
    }

    @FXML
    private void btnClose() {
        athleteFormDialog.close();
    }
}
