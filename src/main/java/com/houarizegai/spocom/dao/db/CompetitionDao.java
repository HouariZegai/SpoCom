
package com.houarizegai.spocom.dao.db;

import com.houarizegai.spocom.dao.vo.Athlete;
import com.houarizegai.spocom.dao.vo.Competition;
import com.houarizegai.spocom.dao.vo.CompetitionInfo;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompetitionDao {
    
    public int addCompetition(Competition competition) { // add new competition
        
        System.out.println(competition.toString());
        // return status: Success => 0, Failed => 1
        return 0;
    }
    
    public List<CompetitionInfo> getAvailableCompetition() {
        
        List<CompetitionInfo> com = new ArrayList<>();
        for(int i = 1; i < 10; i++) {
            com.add(new CompetitionInfo("25", "Cross N " + i, Date.valueOf(new Date(10000 * i).toLocalDate()), "Tiaret"));
        }
        
        return com;
    }
    
    public Map<Integer, Athlete> getAthleteOfCompetition(CompetitionInfo competitionSelected) { // Return list of athlete participate in this competition
        // Map<Number Of dosal, Athlete>
        
        Map<Integer, Athlete> ath = new HashMap<>();
        
        for(int i = 1; i < 100; i++) {
           //Competition
        }
        
        return null;
    }
    
}
