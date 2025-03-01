import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import student.BoardGame;
import student.GameData;

/**
 * Test class for the BoardGame class.
 * Tests the functionality of the BoardGame class, including constructors,
 * getters, equals, hashCode, and other utility methods.
 */
public class TestBoardGame {

    /**
     * Test the constructor and getters of the BoardGame class.
     */
    @Test
    public void testConstructorAndGetters() {
        BoardGame game = new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);

        assertEquals("Catan", game.getName());
        assertEquals(1, game.getId());
        assertEquals(3, game.getMinPlayers());
        assertEquals(4, game.getMaxPlayers());
        assertEquals(60, game.getMinPlayTime());
        assertEquals(120, game.getMaxPlayTime());
        assertEquals(2.5, game.getDifficulty(), 0.001);
        assertEquals(100, game.getRank());
        assertEquals(4.5, game.getRating(), 0.001);
        assertEquals(1995, game.getYearPublished());
    }

    /**
     * Test the equals method of the BoardGame class.
     * Two BoardGames are considered equal if they have the same name and ID.
     */
    @Test
    public void testEquals() {
        BoardGame game1 = new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);
        BoardGame game2 = new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);
        BoardGame game3 = new BoardGame("Catan", 2, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);
        BoardGame game4 = new BoardGame("Monopoly", 1, 2, 6, 60, 180, 1.5, 200, 3.5, 1935);

        // Test reflexivity
        assertEquals(game1, game1);

        // Test symmetry
        assertEquals(game1, game2);
        assertEquals(game2, game1);

        // Test with different ID (should not be equal)
        assertNotEquals(game1, game3);

        // Test with different name (should not be equal)
        assertNotEquals(game1, game4);

        // Test with null and different class
        assertNotEquals(game1, null);
        assertNotEquals(game1, "Catan");
    }

    /**
     * Test the hashCode method of the BoardGame class.
     * Objects that are equal should have the same hash code.
     */
    @Test
    public void testHashCode() {
        BoardGame game1 = new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);
        BoardGame game2 = new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);
        BoardGame game3 = new BoardGame("Catan", 2, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);

        // Equal objects should have equal hash codes
        assertEquals(game1.hashCode(), game2.hashCode());

        // Different objects may have different hash codes
        assertNotEquals(game1.hashCode(), game3.hashCode());
    }

    /**
     * Test the toString method of the BoardGame class.
     */
    @Test
    public void testToString() {
        BoardGame game = new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);
        String expected = game.toString();

        assertTrue(expected.contains("Catan"));
        assertTrue(expected.contains("1"));
        assertTrue(expected.contains("3"));
        assertTrue(expected.contains("4"));
        assertTrue(expected.contains("60"));
        assertTrue(expected.contains("120"));
        assertTrue(expected.contains("2.5"));
        assertTrue(expected.contains("100"));
        assertTrue(expected.contains("4.5"));
        assertTrue(expected.contains("1995"));
    }

    /**
     * Test the toStringWithInfo method of the BoardGame class.
     */
    @Test
    public void testToStringWithInfo() {
        BoardGame game = new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);

        // Test with NAME
        assertEquals("Catan", game.toStringWithInfo(GameData.NAME));

        // Test with RATING
        assertEquals("Catan (4.50)", game.toStringWithInfo(GameData.RATING));

        // Test with DIFFICULTY
        assertEquals("Catan (2.50)", game.toStringWithInfo(GameData.DIFFICULTY));

        // Test with RANK
        assertEquals("Catan (100)", game.toStringWithInfo(GameData.RANK));

        // Test with MIN_PLAYERS
        assertEquals("Catan (3)", game.toStringWithInfo(GameData.MIN_PLAYERS));

        // Test with MAX_PLAYERS
        assertEquals("Catan (4)", game.toStringWithInfo(GameData.MAX_PLAYERS));

        // Test with MIN_TIME
        assertEquals("Catan (60)", game.toStringWithInfo(GameData.MIN_TIME));

        // Test with MAX_TIME
        assertEquals("Catan (120)", game.toStringWithInfo(GameData.MAX_TIME));

        // Test with YEAR
        assertEquals("Catan (1995)", game.toStringWithInfo(GameData.YEAR));
    }

    /**
     * Test the getNumericValue method of the BoardGame class.
     */
    @Test
    public void testGetNumericValue() {
        BoardGame game = new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);

        assertEquals(4.5, game.getNumericValue(GameData.RATING), 0.001);
        assertEquals(2.5, game.getNumericValue(GameData.DIFFICULTY), 0.001);
        assertEquals(100, game.getNumericValue(GameData.RANK), 0.001);
        assertEquals(3, game.getNumericValue(GameData.MIN_PLAYERS), 0.001);
        assertEquals(4, game.getNumericValue(GameData.MAX_PLAYERS), 0.001);
        assertEquals(60, game.getNumericValue(GameData.MIN_TIME), 0.001);
        assertEquals(120, game.getNumericValue(GameData.MAX_TIME), 0.001);
        assertEquals(1995, game.getNumericValue(GameData.YEAR), 0.001);
        assertEquals(1, game.getNumericValue(GameData.ID), 0.001);

        // Test with invalid enum value
        assertThrows(IllegalArgumentException.class, () -> game.getNumericValue(GameData.NAME));
    }

    /**
     * Test the getStringValue method of the BoardGame class.
     */
    @Test
    public void testGetStringValue() {
        BoardGame game = new BoardGame("Catan", 1, 3, 4, 60, 120, 2.5, 100, 4.5, 1995);

        assertEquals("Catan", game.getStringValue(GameData.NAME));

        // Test with invalid enum value
        assertThrows(IllegalArgumentException.class, () -> game.getStringValue(GameData.RATING));
        assertThrows(IllegalArgumentException.class, () -> game.getStringValue(GameData.DIFFICULTY));
        assertThrows(IllegalArgumentException.class, () -> game.getStringValue(GameData.RANK));
    }

    /**
     * Test edge cases with the BoardGame class.
     */
    @Test
    public void testEdgeCases() {
        // Test with extreme values
        BoardGame extremeGame = new BoardGame("Extreme", Integer.MAX_VALUE, 0, 100, 0,
                Integer.MAX_VALUE, Double.MAX_VALUE, Integer.MIN_VALUE, Double.MIN_VALUE, 0);

        assertEquals("Extreme", extremeGame.getName());
        assertEquals(Integer.MAX_VALUE, extremeGame.getId());
        assertEquals(0, extremeGame.getMinPlayers());
        assertEquals(100, extremeGame.getMaxPlayers());
        assertEquals(0, extremeGame.getMinPlayTime());
        assertEquals(Integer.MAX_VALUE, extremeGame.getMaxPlayTime());
        assertEquals(Double.MAX_VALUE, extremeGame.getDifficulty(), 0.001);
        assertEquals(Integer.MIN_VALUE, extremeGame.getRank());
        assertEquals(Double.MIN_VALUE, extremeGame.getRating(), 0.001);
        assertEquals(0, extremeGame.getYearPublished());
    }
}