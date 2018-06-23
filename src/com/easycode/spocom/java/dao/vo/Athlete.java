
package com.easycode.spocom.java.dao.vo;

import java.sql.Date;

public class Athlete {
    private int nDos;
    private String nom, prenom;
    private Date dateNaiss;
    private boolean sexe; // true => Homme, false => femme
    private String club;
    private int codeWilaya;
    private boolean observation;
    
    public Athlete() {
        
    }
    
    public Athlete(int nDos, String nom, String prenom, Date dateNaiss, boolean sexe, String club, int codeWilaya, boolean observation) {
        this.nDos = nDos;
        this.nom = nom;
        this.prenom = prenom;
        this.dateNaiss = dateNaiss;
        this.sexe = sexe;
        this.club = club;
        this.codeWilaya = codeWilaya;
        this.observation = observation;
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

    public boolean isHomme() {
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

    public boolean isInd() {
        return observation;
    }

    public void setObservation(boolean observation) {
        this.observation = observation;
    }
    
    @Override
    public String toString() {
        return "Athlete: {" + nDos + ", " + nom + ", " + prenom + ", " + dateNaiss.toString() + ", " + ((sexe)? "H" : "F") + 
                 ", " + club + ", " + codeWilaya + ", " + ((observation)? "Ind": "Eq") + " }";
    }
    
}
