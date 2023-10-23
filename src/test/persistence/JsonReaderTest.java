package persistence;

import model.ChessMatch;
import model.MatchLog;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Source: https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo
 */

class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            MatchLog testLog = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyChessLog() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyChessLog.json");
        try {
            MatchLog testLog = reader.read();
            assertEquals(0, testLog.numberOfMatches());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderOneMatchInLog() {
        JsonReader reader = new JsonReader("./data/testReaderOneMatchInLog.json");
        try {
            MatchLog testLog = reader.read();
            List<ChessMatch> matches = testLog.getMatches();
            assertEquals(1, matches.size());
            assertEquals("{\"result\":\"w\",\"pieceColour\":true," +
                    "\"date\":\"02/20/2023\",\"time\":\"12:30\"}",
                    matches.get(0).toJson().toString());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}