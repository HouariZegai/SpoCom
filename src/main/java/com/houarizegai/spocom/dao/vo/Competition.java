package com.houarizegai.spocom.dao.vo;

import java.util.List;

public class Competition {

    CompetitionInfo competitionInfo;

    Categorie category;

    List<Athlete> athletes;

    public Competition() {

    }

    public Competition(CompetitionInfo competitionInfo, Categorie category, List<Athlete> athletes) {
        this.competitionInfo = competitionInfo;
        this.category = category;
        this.athletes = athletes;
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

    public Categorie getCompetitionInfo() {
        return category;
    }

    public void setCompetitionInfo(CompetitionInfo competitionInfo) {
        this.competitionInfo = competitionInfo;
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

        String data = "Competition : { " + competitionInfo + ", " + category + ", " + strAthlete + "}";

        return data;
    }
}
