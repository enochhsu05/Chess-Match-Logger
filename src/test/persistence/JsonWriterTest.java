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

class JsonWriterTest {

    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyMatchLog() {
        try {
            MatchLog testLog = new MatchLog();
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyChessLog.json");
            writer.open();
            writer.write(testLog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyChessLog.json");
            testLog = reader.read();
            assertEquals(0, testLog.numberOfMatches());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterOneMatchInLog() {
        try {
            MatchLog testLog = new MatchLog();
            testLog.addMatch(new ChessMatch("w", true, "02/20/2023", "12:30"));
            JsonWriter writer = new JsonWriter("./data/testWriterOneMatchInLog.json");
            writer.open();
            writer.write(testLog);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterOneMatchInLog.json");
            testLog = reader.read();
            List<ChessMatch> matches = testLog.getMatches();
            assertEquals(1, matches.size());
            assertEquals("{\"result\":\"w\",\"pieceColour\":true," +
                            "\"date\":\"02/20/2023\",\"time\":\"12:30\"}",
                    matches.get(0).toJson().toString());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}