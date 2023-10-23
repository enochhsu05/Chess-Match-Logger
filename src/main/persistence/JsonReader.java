package persistence;

import model.ChessMatch;
import model.MatchLog;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.json.*;

/**
 * Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

// Represents a reader that reads match log from JSON data stored in file
public class JsonReader {
    private String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads match log from file and returns it;
    // throws IOException if an error occurs reading data from file
    public MatchLog read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseMatchLog(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses log from JSON object and returns it
    private MatchLog parseMatchLog(JSONObject jsonObject) {
        MatchLog log = new MatchLog();
        addMatches(log, jsonObject);
        return log;
    }

    // MODIFIES: log
    // EFFECTS: parses matches from JSON object and adds them to log
    private void addMatches(MatchLog log, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("matches");
        for (Object json : jsonArray) {
            JSONObject nextMatch = (JSONObject) json;
            addMatch(log, nextMatch);
        }
    }

    // MODIFIES: log
    // EFFECTS: parses match from JSON object and adds it to log
    private void addMatch(MatchLog log, JSONObject jsonObject) {
        String result = jsonObject.getString("result");
        Boolean pieceColour = jsonObject.getBoolean("pieceColour");
        String date = jsonObject.getString("date");
        String time = jsonObject.getString("time");
        ChessMatch match = new ChessMatch(result, pieceColour, date, time);
        log.addMatch(match);
    }
}
