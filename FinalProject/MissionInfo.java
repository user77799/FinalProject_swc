public class MissionInfo{
    public String missionId; //(e.g., MIS001)
    public String missionType; //(e.g., Treasure Hunt, Marine Battle)
    public int dangerLevel; //(e.g., 1–5
    public String missionDate; //(e.g., 2025‑07‑15)
    public String expectedDuration; //(e.g., 2 days)
    public int bountyReward; //(e.g., 5000 Berries)

    // Constructor without and with parameter
    public MissionInfo(String missionId, String missionType, int dangerLevel,String missionDate,String expectedDuration, int bountyReward){
        this.missionId= missionId;
        this.missionType= missionType;
        this.dangerLevel= dangerLevel;
        this.missionDate=missionDate;
        this.expectedDuration=expectedDuration;
        this.bountyReward=bountyReward;
    }
    
    // mutator for each attributes
    // mutator for all attributes
        
    public void setmissionId (String missionId  ){
            this.missionId  = missionId ;
    }
    
    public void setmissionType (String missionType  ){
            this.missionType  = missionType ;
    }
    
    public void setdangerLevel (int dangerLevel  ){
            this.dangerLevel = dangerLevel ;
    }
    
    public void setmissionDate (String missionDate ){
            this.missionDate  = missionDate ;
    }
    
    public void setexpectedDuration (String expectedDuration ){
            this.expectedDuration  = expectedDuration ;
    }
    
    public void setbountyReward (int bountyReward  ){
            this. bountyReward =bountyReward  ;
    }
    
    public void setAll(String missionId, String missionType, int dangerLevel,String missionDate,String expectedDuration, int bountyReward){
            this.missionId  = missionId ;
            this.missionType  = missionType ;
            this.dangerLevel = dangerLevel ;
            this.missionDate  = missionDate ;
            this.expectedDuration  = expectedDuration ;  
            this. bountyReward =bountyReward  ;
    }
    
    // accessor methods
    
    public String getmissionId (String missionId  ){
            return missionId ;
    }
    
    public String getmissionType (String missionType  ){
            return missionType ;
    }
    
    public int getdangerLevel (int dangerLevel  ){
            return dangerLevel ;
    }
    
    public String getmissionDate (String missionDate ){
            return missionDate ;
    }
    
    public String getexpectedDuration (String expectedDuration ){
            return expectedDuration ;
    }
    
    public int getbountyReward (int bountyReward  ){
         return bountyReward  ;
    }
    
    // display computer hardware information
    public String toString(){
        return "missionId: " + missionId + "\n missionType: " + missionType + "\n dangerLevel:" + dangerLevel + "\n missionDate:"+ missionDate + "\n";
    }

    
}