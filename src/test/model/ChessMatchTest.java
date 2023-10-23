package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChessMatchTest {
    private ChessMatch testMatch;

    @BeforeEach
    void runBefore() {
        testMatch = new ChessMatch("w", true, 2, 20, 2023, 12, 30);
    }

    @Test
    void testConstructor1() {
        assertEquals("w", testMatch.getResult());
        assertTrue(testMatch.getPieceColour());
        assertEquals("02/20/2023", testMatch.getDate());
        assertEquals("12:30", testMatch.getTime());
        assertEquals(2, testMatch.getMonth());
        assertEquals(20, testMatch.getDay());
        assertEquals(2023, testMatch.getYear());
        assertEquals(12, testMatch.getHour());
        assertEquals(30, testMatch.getMinute());
    }

    @Test
    void testConstructor2() {
        testMatch = new ChessMatch("w", true, "02/20/2023", "12:30");
        assertEquals("w", testMatch.getResult());
        assertTrue(testMatch.getPieceColour());
        assertEquals("02/20/2023", testMatch.getDate());
        assertEquals("12:30", testMatch.getTime());
        assertEquals(2, testMatch.getMonth());
        assertEquals(20, testMatch.getDay());
        assertEquals(2023, testMatch.getYear());
        assertEquals(12, testMatch.getHour());
        assertEquals(30, testMatch.getMinute());
    }

    @Test
    void testToJson() {
        assertEquals("{\"result\":\"w\",\"pieceColour\":true," +
                        "\"date\":\"02/20/2023\",\"time\":\"12:30\"}",
            testMatch.toJson().toString());
    }
}