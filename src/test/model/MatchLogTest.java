package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MatchLogTest {
    private MatchLog testLog;
    private ChessMatch m1 = new ChessMatch("w", true, 2, 20, 2023, 12, 30);
    private ChessMatch m2 = new ChessMatch("d", false, 1, 2, 2023, 1, 2);
    private ChessMatch m3 = new ChessMatch("l", true, 2, 20, 2022, 23, 23);
    private ChessMatch m4 = new ChessMatch("w", true, "03/20/2023", "12:00");

    @BeforeEach
    void runBefore() {
        testLog = new MatchLog();
    }

    @Test
    void testConstructor() {
        assertEquals(0, testLog.numberOfMatches());
        assertEquals(0, testLog.getMatches().size());
    }

    @Test
    void testAddMatch() {
        testLog.addMatch(m1);
        assertEquals(1, testLog.numberOfMatches());
        testLog.addMatch(m1);
        assertEquals(2, testLog.numberOfMatches());
        testLog.addMatch(m2);
        assertEquals(3, testLog.numberOfMatches());
    }

    @Test
    void testNumberOfMatches() {
        testLog.addMatch(m4);
        assertEquals(0, testLog.numberOfMatches(18, 12));
        assertEquals(0, testLog.numberOfMatches(18, 24));
        assertEquals(0, testLog.numberOfMatches(0, 6));
        assertEquals(0, testLog.numberOfMatches(6, 12));
        assertEquals(1, testLog.numberOfMatches(12, 18));
    }

    @Test
    void testNumberOfMatchesTime() {
        testLog.addMatch(m1);
        testLog.addMatch(m2);
        testLog.addMatch(m3);
        assertEquals(0, testLog.numberOfMatches(1, 1, 2020, 1, 2,
                2020));
        assertEquals(1, testLog.numberOfMatches(2, 20, 2023, 2, 20,
                2023));
        assertEquals(2, testLog.numberOfMatches(1, 1, 2023, 12, 30,
                2023));
        assertEquals(0, testLog.numberOfMatches(2, 5, 2023, 2, 5,
                2023));
    }

    @Test
    void testWinRateOverall() {
        testLog.addMatch(m1);
        testLog.addMatch(m2);
        testLog.addMatch(m3);
        testLog.addMatch(m3);
        assertEquals("0.25/0.25/0.5", testLog.winRate());
        testLog.addMatch(m1);
        assertEquals("0.4/0.2/0.4", testLog.winRate());
    }

    @Test
    void testWinRateColour() {
        testLog.addMatch(m1);
        testLog.addMatch(m2);
        testLog.addMatch(m3);
        assertEquals("0.5/0.0/0.5", testLog.winRate(true));
        assertEquals("0.0/1.0/0.0", testLog.winRate(false));
        testLog.addMatch(m2);
        assertEquals("0.0/1.0/0.0", testLog.winRate(false));
    }

    @Test
    void testToJson() {
        assertEquals("{\"matches\":[]}", testLog.toJson().toString());
        testLog.addMatch(m1);
        assertEquals("{\"matches\":[{\"result\":\"w\",\"pieceColour\":true," +
                "\"date\":\"02/20/2023\",\"time\":\"12:30\"}]}",
                testLog.toJson().toString());
    }

    @Test
    void testMatchesToJson() {
        assertEquals("[]", testLog.matchesToJson().toString());
        testLog.addMatch(m1);
        assertEquals("[{\"result\":\"w\",\"pieceColour\":true," +
                        "\"date\":\"02/20/2023\",\"time\":\"12:30\"}]",
                testLog.matchesToJson().toString());
    }
}
