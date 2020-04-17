
package com.houarizegai.spocom.dao.vo;

import java.sql.Date;

public class Athlete {
    private int nDos;
    private String nom, prenom;
    private Date dateNaiss;
    private boolean sexe; // true => Homme, false => femme
    private String club;
    private int codeWilaya;
    private boolean obs; // True -> Ind, False -> Equipe

    public Athlete() {
        
    }
    
    public Athlete(int nDos, String nom, String prenom, Date dateNaiss, boolean sexe, String club, int codeWilaya, boolean obs) {
        this.nDos = nDos;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.sexe = sexe;
        this.club = club;
        this.codeWilaya = codeWilaya;
        this.obs = obs;
    }

    public int getnDos() {
        return nDos;
    }

    public void setnDos(int nDos) {
        this.nDos = nDos;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Date getDateNaiss() {
        return dateNaiss;
    }

    public void setDateNaiss(Date dateNaiss) {
        this.dateNaiss = dateNaiss;
    }

    public boolean getSexe() {
        return sexe;
    }

    public void setSexe(boolean sexe) {
        this.sexe = sexe;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getCodeWilaya() {
        return codeWilaya;
    }

    public void setCodeWilaya(int codeWilaya) {
        this.codeWilaya = codeWilaya;
    }

    public boolean getObs() {
        return obs;
    }

    public void setObs(boolean obs) {
        this.obs = obs;
    }
    
    @Override
    public String toString() {
        return "Athlete: {" + nDos + ", " + nom + ", " + prenom + ", " + dateNaiss.toString() + ", " + ((sexe)? "H" : "F") + 
                 ", " + club + ", " + codeWilaya + ", " + ((obs)? "Ind": "Eq") + " }";
    }
    
}
