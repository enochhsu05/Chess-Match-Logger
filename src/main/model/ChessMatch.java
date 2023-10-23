package model;

import org.json.JSONObject;
import persistence.Writable;

// Represents a chess match with a result and a piece colour being played at a certain date and time
public class ChessMatch implements Writable {
    private String result;       // w for win, d for draw, and l for loss
    private Boolean pieceColour; // true for white, false for black
    private String date;         // the date in the form MM/DD/YYYY
    private String time;         // the time in the 24-hour form

    /*
     * REQUIRES: result is one of: w, d, l, W, D, L
     *           month >= 1 and month <= 12
     *           day is a day that exists in the given month and year
     *           year <= 9999
     *           hour >= 0 and hour <= 23
     *           minute >= 0 and minute <= 59
     * EFFECTS: result is set to the given result; pieceColour is set to the given pieceColour;
     *          date is set by combining the given year, month, and day;
     *          time is set by combining the given hour and minute
     */
    public ChessMatch(String result, Boolean pieceColour, int month, int day, int year, int hour, int minute) {
        this.result = result.toLowerCase();
        this.pieceColour = pieceColour;
        this.date = toProperForm(month) + "/" + toProperForm(day) + "/" + toProperForm(year / 100)
                + toProperForm(year % 100);
        this.time = toProperForm(hour) + ":" + toProperForm(minute);
    }

    /*
     * REQUIRES: result is one of: w, d, l, W, D, L
     *           date is in the form MM/DD/YYYY
     *           time is in the 24-hour form (ex. 13:12)
     * EFFECTS: result is set to the given result; pieceColour is set to the given pieceColour;
     *          date is set by combining the given year, month, and day;
     *          time is set by combining the given hour and minute
     */
    public ChessMatch(String result, Boolean pieceColour, String date, String time) {
        this.result = result.toLowerCase();
        this.pieceColour = pieceColour;
        this.date = date;
        this.time = time;
    }

    /*
     * EFFECTS: converts the given integer to the commonly used form for dates and times
     */
    private String toProperForm(int dateOrTime) {
        if (dateOrTime <= 9) {
            return "0" + dateOrTime;
        } else {
            return Integer.toString(dateOrTime);
        }
    }

    public String getResult() {
        return result;
    }

    public Boolean getPieceColour() {
        return pieceColour;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    /*
     * EFFECTS: returns the integer value of month by extracting it from the date
     */
    public int getMonth() {
        return Integer.parseInt(date.substring(0, 2));
    }

    /*
     * EFFECTS: returns the integer value of day by extracting it from the date
     */
    public int getDay() {
        return Integer.parseInt(date.substring(3, 5));
    }

    /*
     * EFFECTS: returns the integer value of year by extracting it from the date
     */
    public int getYear() {
        return Integer.parseInt(date.substring(6));
    }

    /*
     * EFFECTS: returns the integer value of hour by extracting it from the time
     */
    public int getHour() {
        return Integer.parseInt(time.substring(0, 2));
    }

    /*
     * EFFECTS: returns the integer value of minute by extracting it from the time
     */
    public int getMinute() {
        return Integer.parseInt(time.substring(3));
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("result", result);
        json.put("pieceColour", pieceColour);
        json.put("date", date);
        json.put("time", time);
        return json;
    }
}
