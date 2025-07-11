//route-based queues and bounty processing using LinkedList, Queue and Stack.
import java.util.*;

public class CrewMemberInfo
{
    // instance variables - replace the example below with your own
    private int x;
    
    public String memberId; //(e.g., CM001)
    public String memberName; //(e.g., Monkey D. Luffy)
    public String pirateCrew; //(e.g., Straw Hat Pirates)
    public List<MissionInfo> missions = new LinkedList<MissionInfo>();  // Has-A relationship

    
    public CrewMemberInfo(String memberId, String memberName, String pirateCrew){
        this.memberId= memberId;
        this.memberName= memberName;
        this.pirateCrew= pirateCrew;
        this.missions = new LinkedList<>();  // Initialize missions list

    }
    

    public CrewMemberInfo(){
        this.memberId= null;
        this.memberName= null;
        this.pirateCrew= null;
    }
    
    //mutator
    public void setId(String memberId){
            this.memberId= memberId;
        }
        
    public void setName(String memberName){
            this.memberName= memberName;
        }
        
    public void setpirateCrew(String pirateCrew){
            this.pirateCrew= pirateCrew;
        }
        
    public String getmemberName (String memberName  ){
            return memberName ;
    }
    
    public String getmemberId (String memberId  ){
            return memberId ;
    }
    
    public String getpirateCrew (String pirateCrew  ){
            return pirateCrew ;
    }
        
    public List<MissionInfo> getMissions() {
        return missions;  // Return as List (interface) for flexibility
    }

}

