package com.houarizegai.spocom.utils;

public class Tools {
    public static java.sql.Date toSqlDate(java.util.Date utilDate) {
        if(utilDate == null)
            return null;
        return new java.sql.Date(utilDate.getTime());
    }
}
