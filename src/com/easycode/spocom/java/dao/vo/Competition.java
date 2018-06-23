package com.easycode.spocom.java.dao.vo;

import java.sql.Date;
import java.util.List;

public class Competition {

    private String edition;
    private String type;
    private Date date;
    private String lieu;

    Categorie category;

    List<Athlete> athletes;

    public Competition() {

    }

    public Competition(String edition, String type, Date date, String lieu, Categorie category, List<Athlete> athletes) {
        this.edition = edition;
        this.type = type;
        this.date = date;
        this.lieu = lieu;
        this.category = category;
        this.athletes = athletes;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
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

    public Categorie getCategory() {
        return category;
    }

    public void setCategory(Categorie category) {
        this.category = category;
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
        }
        
        strAthlete += "}";

        String data = "Competition : { " + edition + ", " + type + ", " + date.toString() + ", " + lieu + category.toString()
                + "[" + strAthlete + "]";

        return data;
    }
}
