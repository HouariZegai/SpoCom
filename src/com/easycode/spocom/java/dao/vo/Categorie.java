package com.easycode.spocom.java.dao.vo;

public class Categorie {

    private int year1Bengemine;
    private int year2Bengemine;

    private int year1Minime;
    private int year2Minime;

    private int year1Cadet;
    private int year2Cadet;

    private int year1Junior;
    private int year2Junior;

    private int year1Senior;
    private int year2Senior;

    @Override
    public String toString() {
        return "Categorie : { " + year1Bengemine + ", " + year2Bengemine + ", " + year1Minime + ", "
                + year2Minime + ", " + year1Cadet + ", " + year2Cadet + ", " + year1Junior + ", "
                + year2Junior + ", " + year1Senior + ", "+ year2Senior + " }";
    }

    public Categorie() {

    }

    public int getYear1Bengemine() {
        return year1Bengemine;
    }

    public void setYear1Bengemine(int year1Bengemine) {
        this.year1Bengemine = year1Bengemine;
    }

    public int getYear2Bengemine() {
        return year2Bengemine;
    }

    public void setYear2Bengemine(int year2Bengemine) {
        this.year2Bengemine = year2Bengemine;
    }

    public int getYear1Minime() {
        return year1Minime;
    }

    public void setYear1Minime(int year1Minime) {
        this.year1Minime = year1Minime;
    }

    public int getYear2Minime() {
        return year2Minime;
    }

    public void setYear2Minime(int year2Minime) {
        this.year2Minime = year2Minime;
    }

    public int getYear1Cadet() {
        return year1Cadet;
    }

    public void setYear1Cadet(int year1Cadet) {
        this.year1Cadet = year1Cadet;
    }

    public int getYear2Cadet() {
        return year2Cadet;
    }

    public void setYear2Cadet(int year2Cadet) {
        this.year2Cadet = year2Cadet;
    }

    public int getYear1Junior() {
        return year1Junior;
    }

    public void setYear1Junior(int year1Junior) {
        this.year1Junior = year1Junior;
    }

    public int getYear2Junior() {
        return year2Junior;
    }

    public void setYear2Junior(int year2Junior) {
        this.year2Junior = year2Junior;
    }

    public int getYear1Senior() {
        return year1Senior;
    }

    public void setYear1Senior(int year1Senior) {
        this.year1Senior = year1Senior;
    }

    public int getYear2Senior() {
        return year2Senior;
    }

    public void setYear2Senior(int year2Senior) {
        this.year2Senior = year2Senior;
    }

}
