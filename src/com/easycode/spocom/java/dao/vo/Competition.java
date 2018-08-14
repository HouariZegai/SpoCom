package com.easycode.spocom.java.dao.vo;

import java.sql.Date;
import java.util.List;

public class Competition {

    // Competition infos
    private int idCom;
    private int edition;
    private String type;
    private Date date;
    private String lieu;

    // Category
    private String bengemine;
    private String minime;
    private String cadet;
    private String junior;
    private String senior;

    // Athletes
    List<Athlete> athletes;

    public Competition(int idCom, int edition, String type, Date date, String lieu, String benjemine, String minime, String cadet, String junior, String senior,
            List<Athlete> athletes) {
        this.idCom = idCom;
        this.edition = edition;
        this.type = type;
        this.date = date;
        this.lieu = lieu;
        this.bengemine = benjemine;
        this.minime = minime;
        this.cadet = cadet;
        this.junior = junior;
        this.senior = senior;
        this.athletes = athletes;
    }

    public int getIdCom() {
        return idCom;
    }

    public void setIdCom(int idCom) {
        this.idCom = idCom;
    }

    public int getEdition() {
        return edition;
    }

    public void setEdition(int edition) {
        this.edition = edition;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getBengemine() {
        return bengemine;
    }

    public void setBengemine(String bengemine) {
        this.bengemine = bengemine;
    }

    public String getMinime() {
        return minime;
    }

    public void setMinime(String minime) {
        this.minime = minime;
    }

    public String getCadet() {
        return cadet;
    }

    public void setCadet(String cadet) {
        this.cadet = cadet;
    }

    public String getJunior() {
        return junior;
    }

    public void setJunior(String junior) {
        this.junior = junior;
    }

    public String getSenior() {
        return senior;
    }

    public void setSenior(String senior) {
        this.senior = senior;
    }

    public List<Athlete> getAthletes() {
        return athletes;
    }

    public void setAthletes(List<Athlete> athletes) {
        this.athletes = athletes;
    }

    @Override
    public String toString() {
        String strAthlete = "Athlete : {";
        if (athletes.size() > 0) {
            for (Athlete e : athletes) {
                strAthlete += "[" + e.getnDos() + ", " + e.getNom() + ", " + e.getPrenom() + ", " + e.getDateNaiss().toString() + ", " + e.getClub()
                        + ", " + e.getCodeWilaya() + "], ";
            }
            strAthlete = strAthlete.substring(0, strAthlete.length() - 2);
            strAthlete += "}";
        }

        strAthlete += "}";

        return "Competition : { " + edition + ", " + type + ", " + date.toString() + ", " + lieu
                + ", " + strAthlete + "}";
    }

}
