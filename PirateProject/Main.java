import java.util.*;
import java.io.*;
import javax.swing.*;
import java.awt.Dimension; //sepcificaly to manipulate joption window's size

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
                // differentiation the data in which index is what variable
                String memberId = tokens[0];
                String memberName = tokens[1];
                String pirateCrew = tokens[2];
                String missionId = tokens[3];
                String missionType = tokens[4];
                int dangerLevel = Integer.parseInt(tokens[5]);
                String missionDate = tokens[6];
                String expectedDuration = tokens[7];
                int bountyReward = Integer.parseInt(tokens[8]);
                
                // to track pirates and missions count
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
            
            // Alternate distribution for rookie pirates to either the east or suth
            for (int i = 0; i < rookieP.size(); i++) {
                if (i % 2 == 0) {
                    EastBlue.enqueue(rookieP.get(i));
                } else {
                    SouthBlue.enqueue(rookieP.get(i));
                }
            }
            
            // Display queues in GUI (default output page can't handle the vast amount of data from file)
            displayQueueInGUI(EastBlue, "East Blue Route");
            displayQueueInGUI(SouthBlue, "South Blue Route");
            displayQueueInGUI(GrandLine, "Grand Line Route");
            
            // Phase 3: Process missions
            processMissionsInGUI(EastBlue, SouthBlue, GrandLine, completeStack);
            
            // Display completestack when pirates have finished their tasks
            displayCompletedStackInGUI(completeStack);
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    //////////////////////
    
    //setting up the GUI
    private static void showScrollableMessage(String title, String message) {
        JTextArea textArea = new JTextArea(message);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        
        JOptionPane.showMessageDialog(null, scrollPane, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    
    private static void displayQueueInGUI(Queue q, String routeName) 
    {
        StringBuilder pp = new StringBuilder();
        Queue temp = new Queue();
        
        pp.append("~~ ").append(routeName).append(" ~~" +"\n\n");
        
        if (q.empty()) {
            pp.append("(Empty)\n");
        } else {
            while (!q.empty()) {
                CrewMemberInfo cm = (CrewMemberInfo) q.dequeue();
                temp.enqueue(cm);
                
                pp.append("Pirate: ").append(cm.memberName).append(" | Crew: ").append(cm.pirateCrew).append("\n");
                pp.append("Assigned Missions:\n");
                for (MissionInfo m : cm.getMissions()) {
                    pp.append(" - ").append(m.missionId).append(": ").append(m.missionType).append("\n");
                }
                int totalBounty = cm.getMissions().stream().mapToInt(m -> m.bountyReward).sum();
                pp.append("Total Bounty: ").append(String.format("%,d", totalBounty)).append(" Berries\n\n");
            }
            
            while (!temp.empty()) {
                q.enqueue(temp.dequeue());
            }
        }
        
        showScrollableMessage(routeName, pp.toString());
    }
    
    
    //////////////////////
    private static void processMissionsInGUI(Queue EastBlue,Queue SouthBlue, Queue GrandLine,Stack completeStack)
    {
        int batchSize = 5;
        int batchCount = 1;
        while(!EastBlue.empty()||!SouthBlue.empty()||!GrandLine.empty())
        {
            StringBuilder batchInfo = new StringBuilder();
            batchInfo.append("--- BATCH ").append(batchCount).append(" ---\n\n");
            
            // Process East Blue
            batchInfo.append(processBatchInGUI(EastBlue, completeStack, batchSize, "East Blue Route"));
            
            // Process South Blue
            batchInfo.append(processBatchInGUI(SouthBlue, completeStack, batchSize, "South Blue Route"));
            
            // Process Grand Line
            batchInfo.append(processBatchInGUI(GrandLine, completeStack, batchSize, "Grand Line Route"));
            
            showScrollableMessage("Mission Processing - Batch " + batchCount, batchInfo.toString());
            
            batchCount++;
        }
        
    }
    
    // batch means the rounds taken by the system to process each queues by 5 one after another
    private static String processBatchInGUI(Queue queue,Stack completeStack,int batchSize,String routeName)
    {
        StringBuilder pp = new StringBuilder();
        pp.append("--- ").append(routeName).append(" ---\n");
        
        for (int i = 0; i < batchSize && !queue.empty(); i++) 
        {
            CrewMemberInfo cm = (CrewMemberInfo) queue.dequeue();
            pp.append("/ ").append(cm.memberName).append(" completed ")
              .append(cm.getMissions().size()).append(" missions\n");
            completeStack.push(cm);
        }
        
        pp.append("\n");
        return pp.toString();
    }
    
    private static void displayCompletedStackInGUI(Stack completeStack)
    {
        StringBuilder pp = new StringBuilder();
        Stack tem = new Stack(); // temporari stack
        
        pp.append("~~ COMPLETEd ~~");
        
        
        while (!completeStack.empty()) {
            CrewMemberInfo cm = (CrewMemberInfo) completeStack.pop();
            tem.push(cm);
            
            pp.append("Pirate: ").append(cm.memberName).append("\n");
            pp.append("Crew: ").append(cm.pirateCrew).append("\n");
            pp.append("Missions Completed:\n");
            
            int totalBounty = 0;
            for (MissionInfo m : cm.getMissions()) {
                pp.append(" - ").append(m.missionId).append(": ").append(m.missionType)
                  .append(" (").append(String.format("%,d", m.bountyReward)).append(" Berries)\n");
                totalBounty += m.bountyReward;
            }
            pp.append("Total Bounty Earned: ").append(String.format("%,d", totalBounty)).append(" Berries\n\n");
        }
        
        // Restore stack
        while (!tem.empty()) {
            completeStack.push(tem.pop());
        }
        
        showScrollableMessage("Mission Completion Report",pp.toString());
    }
    
    ////////////////////////////////////
    // Method to find and return a CrewMemberInfo object from a list based on memberId.
    // Avoids duplicate crew members in the list.
    //Ensures missions are correctly linked to existing pirates.
    
    private static CrewMemberInfo findCrewMember(List<CrewMemberInfo> crewList, String memberId) {
        for (CrewMemberInfo member : crewList) {
            if (member.memberId.equals(memberId)) {
                return member;}
        }
        return null;
    }
}
