
package com.easycode.spocom.java.dao.db;

import com.easycode.spocom.java.dao.vo.Competition;

public class CompetitionDao {
    
    public int addCompetition(Competition competition) { // add new competition
        System.out.println("Passed addCompetition method !");
        System.out.println(competition.toString());
        // return status: Success => 0, Failed => 1
        return 0;
    }
    
}
