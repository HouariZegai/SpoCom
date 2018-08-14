
package com.easycode.spocom.java.dao.db;

import com.easycode.spocom.java.dao.vo.Athlete;
import com.easycode.spocom.java.dao.vo.AthleteBuilder;
import com.easycode.spocom.java.dao.vo.Competition;
import com.easycode.spocom.java.dao.vo.CompetitionBuilder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompetitionDao {
    
    public int addCompetition(Competition competition) { // add new competition
        
        Connection con = DBConnection.con;
        if(con == null) {
            System.out.println("Connection failed !");
            return 0;
        }
        
        int idCom = 0;
        // Add infos of competition
        String sql = "INSERT INTO competition (edition, type, date, lieu, benjemine, minime, cadet, junior, senior) VALUES"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?);";
        PreparedStatement prest = null;
        
        try {
            prest = con.prepareStatement(sql);
            prest.setInt(1, competition.getEdition());
            prest.setString(2, competition.getType());
            prest.setDate(3, new java.sql.Date(competition.getDate().getTime()));
            prest.setString(4, competition.getLieu());
            prest.setString(5, competition.getBengemine());
            prest.setString(6, competition.getMinime());
            prest.setString(7, competition.getCadet());
            prest.setString(8, competition.getJunior());
            prest.setString(9, competition.getSenior());
            prest.executeUpdate();
        } catch(SQLException se) {
            System.out.println("Error in insert information od competition");
            se.printStackTrace();
            return 1;
        }
        
        if(competition.getAthletes().isEmpty()) // there is no Athlete to add in competition
            return 0;
        
        // Get id of competition
        sql = "SELECT id_com FROM competition WHERE edition=? AND type=? AND date=? AND lieu=?;";
        try {
            prest = con.prepareStatement(sql);
            prest.setInt(1, competition.getEdition());
            prest.setString(2, competition.getType());
            prest.setDate(3, competition.getDate());
            prest.setString(4, competition.getLieu());
            ResultSet rs = prest.executeQuery();
            if (rs.next()) {
                idCom = rs.getInt("id_com");
            } else {
                System.out.println("Competition not found !");
            }
        } catch(SQLException se) {
            System.out.println("Error in insert information od competition");
            se.printStackTrace();
            return 1;
        }
        
        // Add athletes
        sql = "INSERT INTO athlete (n_doss, nom, prenom, date_naiss, sexe, club, cw, obs, id_com) VALUES"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            List<Athlete> athletes = competition.getAthletes();
            prest = con.prepareStatement(sql);
            
            for(Athlete athlete : athletes) {
                prest.setInt(1, athlete.getnDos());
                prest.setString(2, athlete.getNom());
                prest.setString(3, athlete.getPrenom());
                prest.setDate(4, athlete.getDateNaiss());
                prest.setBoolean(5, athlete.getSexe());
                prest.setString(6, athlete.getClub());
                prest.setInt(7, athlete.getCodeWilaya());
                prest.setBoolean(8, athlete.getObs());
                prest.setInt(9, idCom);
                prest.executeUpdate();
            }
            
        } catch(SQLException se) {
            System.out.println("Error in insert Athletes");
            se.printStackTrace();
            return 1;
        }
        
        // return status: Success => 0, Failed => 1, Connection failed => -1
        return 0;
    }
    
    public List<Competition> getAvailableCompetition() {
        
        Connection con = DBConnection.con;
        List<Competition> com = new ArrayList<>();
        
        if(con == null) {
            System.out.println("Connection failed !");
            return com;
        }
                
        String sql = "SELECT id_com, edition, type, date, lieu FROM competition WHERE id_com NOT IN ("
                + "SELECT DISTINCT id_com FROM classement);";
        try {
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()) {
                com.add(new CompetitionBuilder()
                        .setIdCom(rs.getInt("id_com"))
                        .setEdition(rs.getInt("edition"))
                        .setType(rs.getString("type"))
                        .setDate(rs.getDate("date"))
                        .setLieu(rs.getString("lieu"))
                .getCompetition());
            }
        } catch(SQLException se) {
            System.out.println("Error in getAvailableComepetition !");
            se.printStackTrace();
        }
        return com;
    }
    
   public Map<Integer, Athlete> getAthleteOfCompetition(int selectedCompetitionId) { // Return list of athlete participate in this competition
        // Map<Number Of dosal, Athlete>
        
        Map<Integer, Athlete> athletes = new HashMap<>();
        
        Connection con = DBConnection.con;
        if(con == null) {
            System.out.println("Connection failed !");
            return athletes;
        }
        
        String sql = "SELECT * FROM athlete WHERE id_com=?";
        try {
            PreparedStatement prest = con.prepareStatement(sql);
            prest.setInt(1, selectedCompetitionId);
            ResultSet rs = prest.executeQuery();
            while(rs.next()) {
                athletes.put(rs.getInt("n_doss"), new AthleteBuilder()
                        .setnDos(rs.getInt("n_doss"))
                        .setNom(rs.getString("nom"))
                        .setPrenom(rs.getString("prenom"))
                        .setDateNaiss(rs.getDate("date_naiss"))
                        .setSexe(rs.getBoolean("sexe"))
                        .setClub(rs.getString("club"))
                        .setCodeWilaya(rs.getInt("cw"))
                        .setObs(rs.getBoolean("obs"))
                        .getAthlete());
            }
        } catch(SQLException se) {
            se.printStackTrace();
        }
        return athletes;
    }
   
   public boolean addClassement(int idCom, List<Integer> nDos) {
       
       String sql = "INSERT INTO Classement (id_com, n_doss, classement) VALUES(?, ?, ?);";
       
       Connection con = DBConnection.con;
       if(con == null) {
           System.out.println("Connection failed !");
           return false;
       }
       
       try {
           PreparedStatement prest = con.prepareStatement(sql);
           for(int i=0; i < nDos.size(); i++) {
                prest.setInt(1, idCom);
                prest.setInt(2, nDos.get(i));
                prest.setInt(3, i + 1);
                prest.executeUpdate();
           }
       } catch(SQLException se) {
           se.printStackTrace();
           return false;
       }
       
       return true;
   }
}
