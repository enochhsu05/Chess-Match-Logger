package ui;

import model.ChessMatch;
import model.MatchLog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// a frame that allows the user to add a match
class AddMatchFrame implements ActionListener {
    private MatchLog log;
    private JFrame frame = new JFrame();

    private JButton winButton = new JButton("Win");
    private JButton drawButton = new JButton("Draw");
    private JButton lossButton = new JButton("Loss");
    private JButton whiteButton = new JButton("White");
    private JButton blackButton = new JButton("Black");
    private JButton addButton = new JButton("Confirm");

    private JTextField dateText = new JTextField();
    private JTextField timeText = new JTextField();

    private String result;
    private boolean pieceColour;
    private String date = "";
    private String time = "";

    // initializes the frame
    public AddMatchFrame(MatchLog log) {
        this.log = log;

        addActionListenerAndCommand();

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        JLabel background = new JLabel(new ImageIcon("/Users/enochhsu/Downloads/chess.jpeg"));

        frame.add(background);

        background.setLayout(new FlowLayout());
        background.add(winButton);
        background.add(drawButton);
        background.add(lossButton);
        background.add(whiteButton);
        background.add(blackButton);
        background.add(dateText);
        background.add(timeText);
        background.add(addButton);

        dateText.setText("Write the date here (MM/DD/YYYY)");
        timeText.setText("Write the time here (XX:XX)");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes the user inputs and adds the new match to log
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "win":
                result = "w";
                break;
            case "draw":
                result = "d";
                break;
            case "loss":
                result = "l";
                break;
            case "white":
                pieceColour = true;
                break;
            case "black":
                pieceColour = false;
                break;
            case "end":
                date = dateText.getText();
                time = timeText.getText();
                log.addMatch(new ChessMatch(result, pieceColour, date, time));
                frame.dispose();
        }
    }

    /*
     * MODIFIES: this
     * EFFECTS: makes the buttons ready for use
     */
    private void addActionListenerAndCommand() {
        winButton.addActionListener(this);
        winButton.setActionCommand("win");
        drawButton.addActionListener(this);
        drawButton.setActionCommand("draw");
        lossButton.addActionListener(this);
        lossButton.setActionCommand("loss");
        whiteButton.addActionListener(this);
        whiteButton.setActionCommand("white");
        blackButton.addActionListener(this);
        blackButton.setActionCommand("black");
        addButton.addActionListener(this);
        addButton.setActionCommand("end");
        dateText.addActionListener(this);
        dateText.setActionCommand("date");
        timeText.addActionListener(this);
        timeText.setActionCommand("time");
    }
}
