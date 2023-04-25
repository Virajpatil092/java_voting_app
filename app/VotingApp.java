import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
// import java.io.File;
import java.util.*;

public class VotingApp extends JFrame implements ActionListener {
    private JLabel candidateLabel;
    private JComboBox<String> candidateComboBox;
    private JButton voteButton;
   
    public VotingApp() {
        super("Voting App");
        
        String voterid = JOptionPane.showInputDialog(this, "Enter Voter ID:");

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
            System.out.println(e.getMessage());
        }

        if(hasnotvoted){
            candidateLabel = new JLabel("Select Candidate:");
            candidateComboBox = new JComboBox<String>();

            candidateComboBox.addItem("Viraj");
            candidateComboBox.addItem("Jyot");


            voteButton = new JButton("Vote");
            voteButton.addActionListener(this);

            // Set layout and add components
            setLayout(new FlowLayout());
            add(candidateLabel);
            add(candidateComboBox);
            add(voteButton);

            // Connect to database
            try {
                
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Database error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }

            // Set window properties
            setSize(300, 100);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        }
        else{
            System.out.println("the user has already casted vote");
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
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        for (Window window : Window.getWindows()) {
            window.dispose();
        }

        new VotingApp();
    }

    public static void main(String[] args) {
        new VotingApp();
    }
}
