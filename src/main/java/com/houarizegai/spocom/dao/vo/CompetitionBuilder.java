package com.houarizegai.spocom.dao.vo;

import java.sql.Date;
import java.util.List;

public class CompetitionBuilder {

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

    public CompetitionBuilder setIdCom(int idCom) {
        this.idCom = idCom;
        return this;
    }

    public CompetitionBuilder setEdition(int edition) {
        this.edition = edition;
        return this;
    }

    public CompetitionBuilder setType(String type) {
        this.type = type;
        return this;
    }

    public CompetitionBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public CompetitionBuilder setLieu(String lieu) {
        this.lieu = lieu;
        return this;
    }

    public CompetitionBuilder setBengemine(String bengemine) {
        this.bengemine = bengemine;
        return this;
    }

    public CompetitionBuilder setMinime(String minime) {
        this.minime = minime;
        return this;
    }

    public CompetitionBuilder setCadet(String cadet) {
        this.cadet = cadet;
        return this;
    }

    public CompetitionBuilder setJunior(String junior) {
        this.junior = junior;
        return this;
    }

    public CompetitionBuilder setSenior(String senior) {
        this.senior = senior;
        return this;
    }

    public CompetitionBuilder setAthlete(List<Athlete> athletes) {
        this.athletes = athletes;
        return this;
    }

    public Competition getCompetition() {
        return new Competition(idCom, edition, type, date, lieu, bengemine, minime, cadet, junior, senior, athletes);
    }
}
