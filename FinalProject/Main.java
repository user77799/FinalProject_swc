import java.util.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize data structures
            Queue EastBlue = new Queue();
            Queue SouthBlue = new Queue();
            Queue GrandLine = new Queue();
            Stack completeStack = new Stack();
            LinkedList<CrewMemberInfo> crewList = new LinkedList<>();
            
            // Read and parse the file
            BufferedReader imp = new BufferedReader(new FileReader("onepiece_combined.txt"));
            String inData;
            
            while ((inData = imp.readLine()) != null) {
                inData = inData.trim();
                if (inData.isEmpty()) continue;
                
                String[] tokens = inData.split(",");
                if (tokens.length < 9) {
                    System.out.println("Skipping invalid line: " + inData);
                    continue;
                }
                
                String memberId = tokens[0];
                String memberName = tokens[1];
                String pirateCrew = tokens[2];
                String missionId = tokens[3];
                String missionType = tokens[4];
                int dangerLevel = Integer.parseInt(tokens[5]);
                String missionDate = tokens[6];
                String expectedDuration = tokens[7];
                int bountyReward = Integer.parseInt(tokens[8]);
                
                CrewMemberInfo crewMember = findCrewMember(crewList, memberId);
                if (crewMember == null) {
                    crewMember = new CrewMemberInfo(memberId, memberName, pirateCrew);
                    crewList.add(crewMember);
                }
                
                MissionInfo mission = new MissionInfo(missionId, missionType, dangerLevel,
                    missionDate, expectedDuration, bountyReward);
                crewMember.getMissions().add(mission);
            }
            imp.close();
            
            // Phase 2: Distribute to queues
            LinkedList<CrewMemberInfo> rookieP = new LinkedList<>();
            for (CrewMemberInfo cm : crewList) {
                if (cm.getMissions().size() <= 3) {
                    rookieP.add(cm);
                } else {
                    GrandLine.enqueue(cm);
                }
            }
            
            // Alternate distribution for rookie pirates
            for (int i = 0; i < rookieP.size(); i++) {
                if (i % 2 == 0) {
                    EastBlue.enqueue(rookieP.get(i));
                } else {
                    SouthBlue.enqueue(rookieP.get(i));
                }
            }
            
            // Display queues in simplified format
            System.out.println("--- East Blue Route ---");
            displayQueueSimplified(EastBlue);
            
            System.out.println("\n--- South Blue Route ---");
            displayQueueSimplified(SouthBlue);
            
            System.out.println("\n--- Grand Line Route ---");
            displayQueueSimplified(GrandLine);
            
            // Phase 3: Process missions
            System.out.println("\n=== MISSION PROCESSING ===");
            processMissions(EastBlue, SouthBlue, GrandLine, completeStack);
            
            displayCompletedStack(completeStack);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void processMissions(Queue EastBlue, Queue SouthBlue, Queue GrandLine, Stack completeStack) {
        int batchSize = 5;
        int batchCount = 1;
        
        while (!EastBlue.empty() || !SouthBlue.empty() || !GrandLine.empty()) {
            System.out.println("\n=== BATCH " + batchCount + " ===");
            
            // Process East Blue
            System.out.println("\nProcessing East Blue Route:");
            processBatch(EastBlue, completeStack, batchSize);
            
            // Process South Blue
            System.out.println("\nProcessing South Blue Route:");
            processBatch(SouthBlue, completeStack, batchSize);
            
            // Process Grand Line
            System.out.println("\nProcessing Grand Line Route:");
            processBatch(GrandLine, completeStack, batchSize);
            
            batchCount++;
        }
        
    }
    
    private static void processBatch(Queue queue, Stack completeStack, int batchSize) {
        for (int i = 0; i < batchSize && !queue.empty(); i++) {
            CrewMemberInfo cm = (CrewMemberInfo) queue.dequeue();
            System.out.println(" - Completed: " + cm.memberName + " (" + cm.getMissions().size() + " missions)");
            completeStack.push(cm);
        }
    }
    
    
    private static void displayQueueSimplified(Queue q) {
        Queue temp = new Queue();
        
        if (q.empty()) {
            System.out.println("(Empty)");
            return;
        }
        
        while (!q.empty()) {
            CrewMemberInfo cm = (CrewMemberInfo) q.dequeue();
            temp.enqueue(cm);
            
            System.out.println("\nPirate: " + cm.memberName + " | Crew: " + cm.pirateCrew);
            System.out.println("Assigned Missions:");
            for (MissionInfo m : cm.getMissions()) {
                System.out.println(" - " + m.missionId + ": " + m.missionType);
            }
            System.out.println("Total Potential Bounty: " + 
                String.format("%,d", cm.getMissions().stream().mapToInt(m -> m.bountyReward).sum()) + " Berries");
        }
        
        // Restore queue
        while (!temp.empty()) {
            q.enqueue(temp.dequeue());
        }
    }
    ////////////////////////////////////
    private static void displayCompletedStack(Stack completeStack) 
    {
        System.out.println("\n=== COMPLETED MISSION STACK ===");

        Stack temp = new Stack(); // Temporary stack to restore order later

        while (!completeStack.empty()) 
        {
            CrewMemberInfo cm = (CrewMemberInfo) completeStack.pop();
            temp.push(cm); // Save for restoring later

            System.out.println("\nPirate: " + cm.memberName);
            System.out.println("Crew: " + cm.pirateCrew);
            System.out.println("Assigned Missions:");
            int totalBounty = 0;
            for (MissionInfo m : cm.getMissions())  
            {
                System.out.println(" - " + m.missionId + ": " + m.missionType);
                totalBounty += m.bountyReward;
            }
            System.out.println("Total Bounty Earned: " + String.format("%,d", totalBounty) + " Berries");
        }

         // Restore the original stack
        while (!temp.empty()) 
        {
            completeStack.push(temp.pop());
        }
    }
    
    ////////////////////////////////////
    
    private static CrewMemberInfo findCrewMember(List<CrewMemberInfo> crewList, String memberId) {
        for (CrewMemberInfo member : crewList) {
            if (member.memberId.equals(memberId)) {
                return member;
            }
        }
        return null;
    }
}
