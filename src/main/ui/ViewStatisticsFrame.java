package ui;

import model.Event;
import model.EventLog;
import model.MatchLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

// Displays a frame that allows the user to view their statistics
class ViewStatisticsFrame implements ActionListener, WindowListener {
    private MatchLog matches;
    private JFrame frame = new JFrame();

    private JButton viewAllMatchesButton = new JButton("View All Matches");
    private JButton countMatchesButton = new JButton("View Total Number of Matches Played");
    private JButton winRateButton = new JButton("View Overall Win Rate");
    private JButton returnButton = new JButton("Return To Menu");

    private JTextArea textArea = new JTextArea();

    /*
     * EFFECTS: runs the frame
     */
    public ViewStatisticsFrame(MatchLog matches) {
        this.matches = matches;

        setButtons();

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        JLabel background = new JLabel(new ImageIcon("/Users/enochhsu/Downloads/chess.jpeg"));

        frame.add(background);

        background.setLayout(new FlowLayout());
        background.add(viewAllMatchesButton);
        background.add(countMatchesButton);
        background.add(winRateButton);
        background.add(returnButton);
        background.add(textArea);

        textArea.setText("The statistics will be displayed here");
        textArea.setEditable(false);

        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        frame.setSize(450, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addWindowListener(this);
    }

    /*
     * EFFECTS: processes the user's button presses
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "viewAllMatches":
                viewAllMatches();
                break;
            case "countMatches":
                countMatches();
                break;
            case "winRate":
                winRate();
                break;
            case "return":
                frame.dispose();
        }
    }

    /*
     * EFFECTS: makes the buttons ready to use
     */
    private void setButtons() {
        viewAllMatchesButton.addActionListener(this);
        viewAllMatchesButton.setActionCommand("viewAllMatches");
        countMatchesButton.addActionListener(this);
        countMatchesButton.setActionCommand("countMatches");
        winRateButton.addActionListener(this);
        winRateButton.setActionCommand("winRate");
        returnButton.addActionListener(this);
        returnButton.setActionCommand("return");
    }

    /*
     * EFFECTS: displays all matches played
     */
    private void viewAllMatches() {
        String text = "Here are all of your matches: \n\n" + matches.viewAllMatches();
        textArea.setText(text);
    }

    /*
     * EFFECTS: displays the total number of matches played
     */
    private void countMatches() {
        textArea.setText("You have played a total of " + matches.numberOfMatchesLog() + " match(es).");
    }

    /*
     * EFFECTS: displays the win rate of all matches played
     */
    private void winRate() {
        String winRate = matches.winRate();
        textArea.setText("You have won " + Double.parseDouble(winRate.substring(0, winRate.indexOf("/")))
                * 100
                + "% of your matches.\n" + "You have drawn "
                + Double.parseDouble(winRate.substring(winRate.indexOf("/") + 1, winRate.lastIndexOf("/")))
                * 100
                + "% of your matches.\n" + "You have lost "
                + Double.parseDouble(winRate.substring(winRate.lastIndexOf("/") + 1))
                * 100
                + "% of your matches.");
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    /*
     * EFFECTS: prints out every logged event before closing the application
     */
    @Override
    public void windowClosing(WindowEvent e) {
        for (Event next : EventLog.getInstance()) {
            System.out.println(next.getDescription());
        }

        System.exit(0);
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}

