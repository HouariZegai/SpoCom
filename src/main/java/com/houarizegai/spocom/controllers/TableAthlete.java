
package com.houarizegai.spocom.controllers;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import java.sql.Date;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class TableAthlete extends RecursiveTreeObject<TableAthlete>{
        StringProperty nom;
        StringProperty prenom;
        StringProperty dateNaiss;
        StringProperty sexe;
        StringProperty club;
        StringProperty codeWilaya;
        StringProperty observation;

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
