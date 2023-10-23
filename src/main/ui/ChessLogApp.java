package ui;

import model.ChessMatch;
import model.Event;
import model.EventLog;
import model.MatchLog;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE;

// chess log application
public class ChessLogApp implements ActionListener, WindowListener {
    private static final String JSON_STORE = "./data/chess_log.json";
    private MatchLog log;
    private Scanner input;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;

    private JButton addMatchButton = new JButton("Add Chess Match");
    private JButton viewStatsButton = new JButton("View Statistics");
    private JButton saveLogButton = new JButton("Save Chess Log");
    private JButton loadLogButton = new JButton("Load Chess Log");

    /*
     * EFFECTS: runs the chess log application
     */
    public ChessLogApp() {
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
        JFrame frame = new JFrame();

        setButtons();

        frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
        JLabel background = new JLabel(new ImageIcon("/Users/enochhsu/Downloads/chess.jpeg"));

        frame.add(background);

        background.setLayout(new FlowLayout());
        background.add(addMatchButton);
        background.add(viewStatsButton);
        background.add(saveLogButton);
        background.add(loadLogButton);

        frame.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        frame.setSize(450, 450);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        frame.addWindowListener(this);

        runChessLog();
    }

    /*
     * MODIFIES: this
     * EFFECTS: processes user inputs
     */
    private void runChessLog() {
        boolean keepGoing = true;
        String command;

        initializer();

        System.out.println("Hi! Welcome to this application!");

        while (keepGoing) {
            displayMenu();
            command = input.nextLine();
            if (command.equals("1")) {
                processAddMatch();
            }
            if (command.equals("2")) {
                processViewStatistics();
            }
            if (command.equals("3")) {
                saveChessLog();
            }
            if (command.equals("4")) {
                loadChessLog();
            }

            keepGoing = (!command.equals("5"));
        }

        System.out.println("Have a nice day!");
    }

    /*
     * MODIFIES: this
     * EFFECTS: initializes the match log and input scanner
     */
    private void initializer() {
        log = new MatchLog();
        input = new Scanner(System.in);
    }

    /*
     EFFECTS: displays the options for the user
     */
    private void displayMenu() {
        System.out.println("\nChoose between the following options:");
        System.out.println("\t1: add a chess match");
        System.out.println("\t2: view my statistics");
        System.out.println("\t3: save chess log");
        System.out.println("\t4: load chess log");
        System.out.println("\t5: quit");
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds a match to the log according to user specifications
     */
    private void processAddMatch() {
        System.out.println("Please provide some information about the chess match.");
        System.out.println("Enter 'w' if you won, 'l' if you lost, and 'd' if you drew.");
        String result = input.nextLine();
        System.out.println("Enter 'w' if your played with white and 'b' if you played with black");
        Boolean pieceColour = true;
        if (input.nextLine().equals("b")) {
            pieceColour = false;
        }
        System.out.println("Enter the year that you played this match");
        int year = Integer.parseInt(input.nextLine());
        System.out.println("Enter the month that you played this match (1-12)");
        int month = Integer.parseInt(input.nextLine());
        System.out.println("Enter the day that you played this match (1-31)");
        int day = Integer.parseInt(input.nextLine());
        System.out.println("Enter the hour that you played this match (0-23)");
        int hour = Integer.parseInt(input.nextLine());
        System.out.println("Enter the minute that you played this match (0-59)");
        int minute = Integer.parseInt(input.nextLine());
        log.addMatch(new ChessMatch(result, pieceColour, month, day, year, hour, minute));
        System.out.println("You've successfully added logged your chess match!");
    }

    /*
     * EFFECTS: process user inputs for the different statistics
     */
    private void processViewStatistics() {
        displayStatisticsOptions();

        String command = input.nextLine();

        if (command.equals("1")) {
            processNumberOfMatchesTotal();
        }
        if (command.equals("2")) {
            processNumberOfMatches();
        }
        if (command.equals("3")) {
            processWinRate();
        }
        if (command.equals("4")) {
            processWinRateColour();
        }
        if (command.equals("5")) {
            processNumberOfMatchesTime();
        }
    }

    /*
     * EFFECTS: displays the different statistics the user can choose to view
     */
    private void displayStatisticsOptions() {
        System.out.println("Choose between the following statistics:");
        System.out.println("\t1: Total number of matches played");
        System.out.println("\t2: Number of matches played between a given timeframe");
        System.out.println("\t3: Win/Draw/Loss rate across all games");
        System.out.println("\t4: Win/Draw/Loss rate across all games played with a given colour");
        System.out.println("\t5: Number of matches played between at a given time of day");
    }

    /*
     * EFFECTS: shows the total number of matches played
     */
    private void processNumberOfMatchesTotal() {
        System.out.println("The number of games you played in total is " + log.numberOfMatches());
    }

    /*
     * EFFECTS: shows the number of matches played between a certain timeframe based on user specifications
     */
    private void processNumberOfMatches() {
        System.out.println("Please provide the date range that you would like the know the "
                + "number of matches you played between (inclusive)");
        System.out.println("Enter the starting year");
        int startYear = Integer.parseInt(input.nextLine());
        System.out.println("Enter the starting month");
        int startMonth = Integer.parseInt(input.nextLine());
        System.out.println("Enter the starting day");
        int startDay = Integer.parseInt(input.nextLine());
        System.out.println("Enter the ending year");
        int endYear = Integer.parseInt(input.nextLine());
        System.out.println("Enter the ending month");
        int endMonth = Integer.parseInt(input.nextLine());
        System.out.println("Enter the ending day");
        int endDay = Integer.parseInt(input.nextLine());
        System.out.println("The number of games you played between the given dates is "
                + log.numberOfMatches(startMonth, startDay, startYear, endMonth, endDay, endYear));
    }

    /*
     * EFFECTS: shows the number of matches played during a certain time of day based on user specifications
     */
    private void processNumberOfMatchesTime() {
        System.out.println("\nChoose between the following times:");
        System.out.println("\t1: morning (06:00-11:59)");
        System.out.println("\t2: afternoon (12:00-17:59)");
        System.out.println("\t3: evening (18:00-23:59)");
        System.out.println("\t4: night (00:00-5:59)");
        String choice = input.nextLine();
        String result = "";
        if (choice.equals("1")) {
            result = log.numberOfMatches(6, 12) + " match(es) during the morning";
        }
        if (choice.equals("2")) {
            result = log.numberOfMatches(12, 18) + " match(es) during the afternoon";
        }
        if (choice.equals("3")) {
            result = log.numberOfMatches(18, 24) + " match(es) during the evening";
        }
        if (choice.equals("4")) {
            result = log.numberOfMatches(0, 6) + " match(es) during the night";
        }
        System.out.println("You have played " + result);
    }

    /*
     * EFFECTS: shows the overall win/draw/loss ratio
     */
    private void processWinRate() {
        System.out.println("Your overall win/draw/loss ratio is " + log.winRate());
    }

    /*
     * EFFECTS: shows the win/draw/loss ratio depending on the piece colour the user provides
     */
    private void processWinRateColour() {
        System.out.println("Which colour would you like to know your win rate for? ('w' or 'b')");
        Boolean pieceColour = true;
        String colour = "white";
        if (input.nextLine().equals("b")) {
            pieceColour = false;
            colour = "black";
        }
        System.out.println("Your overall win/draw/loss ratio while played with the " + colour
                + " pieces is " + log.winRate(pieceColour));
    }

    // EFFECTS: saves the chess log to file
    private void saveChessLog() {
        try {
            jsonWriter.open();
            jsonWriter.write(log);
            jsonWriter.close();
            System.out.println("Saved the chess log to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
    }

    // MODIFIES: this
    // EFFECTS: loads the chess log from file
    private void loadChessLog() {
        try {
            log = jsonReader.read();
            System.out.println("Loaded the chess log from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
    }

    /*
     * EFFECTS: processes the user command
     */
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "addMatch":
                addMatch();
                break;
            case "viewStats":
                viewStatistics();
                break;
            case "saveLog":
                saveChessLog();
                break;
            case "loadLog":
                loadChessLog();
                break;
        }
    }

    /*
     * EFFECTS: creates a new frame for the user to add a match
     */
    private void addMatch() {
        new AddMatchFrame(log);
    }

    /*
     * EFFECTS: creates a new frame for the user to view their statistics
     */
    private void viewStatistics() {
        new ViewStatisticsFrame(log);
    }

    /*
     * MODIFIES: this
     * EFFECTS: makes the buttons ready for use
     */
    private void setButtons() {
        addMatchButton.addActionListener(this);
        viewStatsButton.addActionListener(this);
        saveLogButton.addActionListener(this);
        loadLogButton.addActionListener(this);
        addMatchButton.setActionCommand("addMatch");
        viewStatsButton.setActionCommand("viewStats");
        saveLogButton.setActionCommand("saveLog");
        loadLogButton.setActionCommand("loadLog");
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

    public static void main(String[] args) {
        new ChessLogApp();
    }
}
