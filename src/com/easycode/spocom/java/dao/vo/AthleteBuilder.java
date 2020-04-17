package com.easycode.spocom.java.dao.vo;

import java.sql.Date;

public class AthleteBuilder {
    private int nDos;
    private String nom, prenom;
    private Date dateNaiss;
    private boolean sexe; // true => Homme, false => femme
    private String club;
    private int codeWilaya;
    private boolean obs; // True -> Ind, False -> Equipe

    public AthleteBuilder setnDos(int nDos) {
        this.nDos = nDos;
        return this;
    }

    public AthleteBuilder setNom(String nom) {
        this.nom = nom;
        return this;
    }

    public AthleteBuilder setPrenom(String prenom) {
        this.prenom = prenom;
        return this;
    }

    public AthleteBuilder setDateNaiss(Date dateNaiss) {
        this.dateNaiss = dateNaiss;
        return this;
    }

    public AthleteBuilder setSexe(boolean sexe) {
        this.sexe = sexe;
        return this;
    }

    public AthleteBuilder setClub(String club) {
        this.club = club;
        return this;
    }

    public AthleteBuilder setCodeWilaya(int codeWilaya) {
        this.codeWilaya = codeWilaya;
        return this;
    }

    public AthleteBuilder setObs(boolean obs) {
        this.obs = obs;
        return this;
    }
    
    public Athlete getAthlete() {
        return new Athlete(nDos, nom, prenom, dateNaiss, sexe, club, codeWilaya, obs);
    }
    
}
