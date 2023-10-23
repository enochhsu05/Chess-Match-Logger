package model;

import org.json.JSONArray;
import org.json.JSONObject;
import persistence.Writable;

import java.util.ArrayList;

// Represents the match log, containing a list of chess matches.
public class MatchLog implements Writable {
    private ArrayList<ChessMatch> matches;

    /*
     * EFFECTS: Initializes matches as an empty arraylist
     */
    public MatchLog() {
        matches = new ArrayList<>();
    }

    public ArrayList<ChessMatch> getMatches() {
        return matches;
    }

    /*
     * MODIFIES: this
     * EFFECTS: adds the given match to the list of matches and logs the action
     */
    public void addMatch(ChessMatch match) {
        matches.add(match);
        EventLog.getInstance().logEvent(new Event("chess match added to match log"));
    }

    /*
     * EFFECTS: returns the total number of matches in the list and logs the action
     */
    public int numberOfMatchesLog() {
        EventLog.getInstance().logEvent(new Event("total number of matches displayed"));
        return matches.size();
    }

    /*
     * EFFECTS: returns the total number of matches in the list
     */
    public int numberOfMatches() {
        return matches.size();
    }

    /*
     * EFFECTS: returns the number of matches played by the given colour (true for white and false for black)
     */
    private int numberOfMatches(boolean pieceColour) {
        int count = 0;
        for (ChessMatch match : matches) {
            if (pieceColour == match.getPieceColour()) {
                count++;
            }
        }
        return count;
    }

    /*
     * REQUIRES: the start date does not occur after the end date and the given dates are legitimate
     * EFFECTS: returns the number of matches played before the two given dates, inclusive
     */
    public int numberOfMatches(int startMonth, int startDay, int startYear,
                               int endMonth, int endDay, int endYear) {
        int count = 0;
        int weightedStart = toWeightedForm(startMonth, startDay, startYear);
        int weightedEnd = toWeightedForm(endMonth, endDay, endYear);
        for (ChessMatch match : matches) {
            int weightedDate = toWeightedForm(match.getMonth(), match.getDay(), match.getYear());
            if (weightedStart <= weightedDate && weightedDate <= weightedEnd) {
                count++;
            }
        }
        return count;
    }

    /*
     * REQUIRES: the given hours are between 0 and 24, inclusive
     * EFFECTS: returns the number of matches played between the two hours (start inclusive end exclusive)
     */
    public int numberOfMatches(int startHour, int endHour) {
        int count = 0;
        for (ChessMatch match : matches) {
            if (startHour <= match.getHour() && endHour > match.getHour()) {
                count++;
            }
        }
        return count;
    }

    /*
     * EFFECTS: returns the weighted form of the date
     */
    private int toWeightedForm(int month, int day, int year) {
        return day + month * 100 + year * 10000;
    }

    /*
     * REQUIRES: at least 1 chess match must be logged already
     * EFFECTS: returns a string containing the proportion of wins, draws, and losses, and logs the action
     */
    public String winRate() {
        EventLog.getInstance().logEvent(new Event("overall win rate displayed"));
        int winCount = 0;
        int drawCount = 0;
        int lossCount = 0;
        for (ChessMatch match : matches) {
            if (match.getResult().equals("w")) {
                winCount++;
            }
            if (match.getResult().equals("d")) {
                drawCount++;
            }
            if (match.getResult().equals("l")) {
                lossCount++;
            }
        }
        return winCount / (double) numberOfMatches() + "/"
                + drawCount / (double) numberOfMatches() + "/"
                + lossCount / (double) numberOfMatches();
    }

    /*
     * REQUIRES: at least 1 chess match using the specified colour must be logged already
     * EFFECTS: returns a string containing the proportion of wins, draws, and losses depending on the piece colour
     */
    public String winRate(boolean pieceColour) {
        int winCount = 0;
        int drawCount = 0;
        int lossCount = 0;
        for (ChessMatch match : matches) {
            if (pieceColour == match.getPieceColour()) {
                if (match.getResult().equals("w")) {
                    winCount++;
                }
                if (match.getResult().equals("d")) {
                    drawCount++;
                }
                if (match.getResult().equals("l")) {
                    lossCount++;
                }
            }
        }
        return winCount / (double) numberOfMatches(pieceColour) + "/"
                + drawCount / (double) numberOfMatches(pieceColour) + "/"
                + lossCount / (double) numberOfMatches(pieceColour);
    }

    /*
     * EFFECTS: displays the string version of every match and logs the action
     */
    public String viewAllMatches() {
        EventLog.getInstance().logEvent(new Event("all matches displayed"));
        String text = "";
        for (ChessMatch match : matches) {
            text += "On the day " + match.getDate() + ", at " + match.getTime() + ", you "
                    + getMatchResult(match) + " your match, \nwhere you played with the "
                    + getPieceColour(match) + " pieces.\n\n";
        }
        return text;
    }

    /*
     * EFFECTS: converts match result a human-readable form
     */
    private String getMatchResult(ChessMatch match) {
        switch (match.getResult()) {
            case "w":
                return "won";
            case "d":
                return "drew";
            case "l":
                return "lost";
            default:
                return "???";
        }
    }

    /*
     * EFFECTS: converts piece colour to a human-readable form
     */
    private String getPieceColour(ChessMatch match) {
        if (match.getPieceColour()) {
            return "white";
        } else {
            return "black";
        }
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("matches", matchesToJson());
        return json;
    }

    // EFFECTS: returns matches in the match log as a JSON array
    public JSONArray matchesToJson() {
        JSONArray jsonArray = new JSONArray();

        for (ChessMatch match : matches) {
            jsonArray.put(match.toJson());
        }
        return jsonArray;
    }
}
