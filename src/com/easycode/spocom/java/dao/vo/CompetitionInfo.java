
package com.easycode.spocom.java.dao.vo;

import java.sql.Date;

public class CompetitionInfo {
    private String edition;
    private String type;
    private Date date;
    private String lieu;
    
    public CompetitionInfo(String edition, String type, Date date, String lieu) {
        this.edition = edition;
        this.type = type;
        this.date = date;
        this.lieu = lieu;
    }

    public CompetitionInfo() {
        
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
    
    @Override
    public String toString() {
       return edition + ", " + type + ", " + date.toString() + ", " + lieu;
    }
}
