import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

public class VotingApp extends JFrame implements ActionListener {
    private JLabel candidateLabel;
    private JComboBox<String> candidateComboBox;
    private JButton voteButton;
   
    public VotingApp() {
        super("Voting App");
        
        String voterid = JOptionPane.showInputDialog(this, "Enter Voter ID:");
        System.out.println(voterid);
        if(voterid.equals("")){
            int result = JOptionPane.showConfirmDialog(null, "Do you want to view the result?", "View Result", JOptionPane.YES_NO_OPTION);
  
            // if user selects yes, call the result method
            if (result == JOptionPane.YES_OPTION) {
                result();
            }
        }

        Boolean hasnotvoted = true;
        try{
            BufferedReader res = new BufferedReader(new FileReader("voted.txt")) ;
            FileWriter myWriter = new FileWriter("voted.txt",true);

            String value;

            while((value = res.readLine()) != null){
                if(value.equals(voterid)){
                    hasnotvoted = false;
                    break;
                }
            }
            if(hasnotvoted){
                myWriter.append(voterid+"\n");
            }
            res.close();
            myWriter.close();

        }
        catch(Exception e){
             
        }

        if(hasnotvoted){
            candidateLabel = new JLabel("Select Candidate:");
            candidateLabel.setHorizontalAlignment(SwingConstants.CENTER);

            candidateComboBox = new JComboBox<String>();

            candidateComboBox.addItem("Viraj");
            candidateComboBox.addItem("Jyot");


            voteButton = new JButton("Vote");
            voteButton.addActionListener(this);
            voteButton.setBackground(Color.GREEN);
            voteButton.setText("Cast Vote");
            voteButton.setHorizontalAlignment(SwingConstants.CENTER);

            // Set layout and add components
            setLayout(new FlowLayout());
            add(candidateLabel);
            add(candidateComboBox);
            add(voteButton);

            // Set window properties
            setSize(800, 400);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        }
        else{
            for (Window window : Window.getWindows()) {
                window.dispose();
            }

            JOptionPane.showMessageDialog(this, "You have already voted.", "Error", JOptionPane.ERROR_MESSAGE);
            JButton okButton = new JButton("OK");
            okButton.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    for (Window window : Window.getWindows()) {
                        window.dispose();
                    }
                }
            });
            add(okButton);
            setSize(400, 150);
            

            new VotingApp();
        }
    }

    public void actionPerformed(ActionEvent e) {
        // Get selected candidate name
        String candidateName = (String) candidateComboBox.getSelectedItem();
    
        try {
    
            BufferedReader br = new BufferedReader(new FileReader("data.txt")) ;
    
            ArrayList<String> list = new ArrayList<>() ;
    
            String line ;
            while((line = br.readLine()) != null){
                list.add(line) ;
            }
    
            FileWriter file = new FileWriter("data.txt") ;
            
    
            boolean foundCandidate = false ;
            for(String str: list){
                String[] arr = str.split(" ") ;
    
                if(arr[0].equals(candidateName)){
                    int votes = Integer.parseInt(arr[1]) + 1 ;
                    arr[1] = Integer.toString(votes) ;
                    foundCandidate = true ;
                }
                String newStr = arr[0] +" " + arr[1] ;
                file.write(newStr+"\n");
            }
    
            if(!foundCandidate){
                file.write(candidateName +" 1\n");
            }
    
            file.flush();
            file.close();
            br.close();
    
    
            // Show success message
            JOptionPane.showMessageDialog(this, "Vote cast for " + candidateName, "Success",JOptionPane.INFORMATION_MESSAGE);
    
            // Display candidate information
            JLabel candidateInfoLabel = new JLabel();
            if(candidateName.equals("Viraj")) {
                candidateInfoLabel.setText("Party : XYZ");
            }
            else if(candidateName.equals("Jyot")) {
                candidateInfoLabel.setText("party : ABC");
            }
            candidateInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            add(candidateInfoLabel);
    
        } catch (Exception ex) {
             
        }

        for (Window window : Window.getWindows()) {
            window.dispose();
        }

        new VotingApp();

    }

    public static void result(){
        try{
            BufferedReader br = new BufferedReader(new FileReader("data.txt")) ;
            ArrayList<String> list = new ArrayList<>() ;
            
            String line;

            while((line = br.readLine()) != null){
                list.add(line);
            }
            int Viraj=0,Jyot=0;
            for(String str: list){
                String[] arr = str.split(" ") ;
                if(arr[0].equals("Viraj")){
                    Viraj=Integer.parseInt(arr[1]);
                }
                else{
                    Jyot = Integer.parseInt(arr[1]);
                }
            }

            String result;
            if (Viraj > Jyot) {
                result = "Viraj won by " + (Viraj - Jyot) + " Votes";
            } else if (Viraj < Jyot) {
                result = "Jyot won by " + Math.abs(Viraj - Jyot) + " Votes";
            } else {
                result = "It's a Draw! Stay tuned for next Elections...";
            }

            JOptionPane.showMessageDialog(null, result, "Election Result", JOptionPane.INFORMATION_MESSAGE);

            br.close();
            
        }
        catch(Exception e){
             
        }
    }

    public static void main(String[] args) {
        new VotingApp();
    }
}
