import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.BoardGame;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import student.Planner;
import student.IPlanner;
import student.GameData;

/**
 * JUnit test for the Planner class.
 * 
 * Tests various filter operations and sorting functionality.
 */
public class TestPlanner {
    private static Set<BoardGame> games;
    private IPlanner planner;

    @BeforeAll
    public static void setup() {
        games = new HashSet<>();
        games.add(new BoardGame("17 days", 6, 1, 8, 70, 70, 9.0, 600, 9.0, 2005));
        games.add(new BoardGame("Chess", 7, 2, 2, 10, 20, 10.0, 700, 10.0, 2006));
        games.add(new BoardGame("Go", 1, 2, 5, 30, 30, 8.0, 100, 7.5, 2000));
        games.add(new BoardGame("Go Fish", 2, 2, 10, 20, 120, 3.0, 200, 6.5, 2001));
        games.add(new BoardGame("golang", 4, 2, 7, 50, 55, 7.0, 400, 9.5, 2003));
        games.add(new BoardGame("GoRami", 3, 6, 6, 40, 42, 5.0, 300, 8.5, 2002));
        games.add(new BoardGame("Monopoly", 8, 6, 10, 20, 1000, 1.0, 800, 5.0, 2007));
        games.add(new BoardGame("Tucano", 5, 10, 20, 60, 90, 6.0, 500, 8.0, 2004));
    }

    @BeforeEach
    public void initPlanner() {
        planner = new Planner(games);
    }

    // Helper method to check if a game with a specific name is in the filtered list
    private boolean containsGameWithName(List<BoardGame> games, String name) {
        return games.stream().anyMatch(game -> game.getName().equalsIgnoreCase(name));
    }

    // Test filtering by name with CONTAINS operator
    @Test
    public void testFilterByNameContains() {
        List<BoardGame> filtered = planner.filter("name ~= Go").toList();
        assertEquals(4, filtered.size());
        assertTrue(containsGameWithName(filtered, "Go"));
        assertTrue(containsGameWithName(filtered, "Go Fish"));
        assertTrue(containsGameWithName(filtered, "golang"));
        assertTrue(containsGameWithName(filtered, "GoRami"));
    }

    // Test filtering by name with EQUALS operator
    @Test
    public void testFilterByNameEquals() {
        List<BoardGame> filtered = planner.filter("name == Go").toList();
        assertEquals(1, filtered.size());
        assertEquals("Go", filtered.get(0).getName());
    }

    // Test filtering by name with GREATER_THAN operator
    @Test
    public void testNameGreaterThan() {
        List<BoardGame> filtered = planner.filter("name > M").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getName().compareToIgnoreCase("M") > 0);
        }
    }

    // Test filtering by name with LESS_THAN operator
    @Test
    public void testNameLessThan() {
        List<BoardGame> filtered = planner.filter("name < D").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getName().compareToIgnoreCase("D") < 0);
        }
    }

    // Test filtering by name with GREATER_THAN_EQUALS operator
    @Test
    public void testNameGreaterThanOrEqual() {
        List<BoardGame> filtered = planner.filter("name >= Go").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getName().compareToIgnoreCase("Go") >= 0);
        }
        assertTrue(containsGameWithName(filtered, "Go"));
    }

    // Test filtering by name with LESS_THAN_EQUALS operator
    @Test
    public void testNameLessThanOrEqual() {
        List<BoardGame> filtered = planner.filter("name <= Go").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getName().compareToIgnoreCase("Go") <= 0);
        }
        assertTrue(containsGameWithName(filtered, "Go"));
    }

    // Test filtering by name with NOT_EQUALS operator
    @Test
    public void testNameNotEqual() {
        List<BoardGame> filtered = planner.filter("name != Go").toList();
        assertEquals(games.size() - 1, filtered.size());
        assertFalse(containsGameWithName(filtered, "Go"));
    }

    // Test filtering by minPlayers with GREATER_THAN operator
    @Test
    public void testMinPlayersGreaterThan() {
        List<BoardGame> filtered = planner.filter("minplayers > 5").toList();
        assertEquals(3, filtered.size());
        for (BoardGame game : filtered) {
            assertTrue(game.getMinPlayers() > 5);
        }
    }

    // Test filtering by minPlayers with LESS_THAN operator
    @Test
    public void testMinPlayersLessThan() {
        List<BoardGame> filtered = planner.filter("minplayers < 3").toList();
        assertEquals(5, filtered.size());
        for (BoardGame game : filtered) {
            assertTrue(game.getMinPlayers() < 3);
        }
    }

    // Test filtering by minPlayers with GREATER_THAN_EQUALS operator
    @Test
    public void testMinPlayersGreaterThanOrEqual() {
        List<BoardGame> filtered = planner.filter("minplayers >= 6").toList();
        assertEquals(3, filtered.size());
        for (BoardGame game : filtered) {
            assertTrue(game.getMinPlayers() >= 6);
        }
    }

    // Test filtering by minPlayers with LESS_THAN_EQUALS operator
    @Test
    public void testMinPlayersLessThanOrEqual() {
        List<BoardGame> filtered = planner.filter("minplayers <= 2").toList();
        assertEquals(5, filtered.size());
        for (BoardGame game : filtered) {
            assertTrue(game.getMinPlayers() <= 2);
        }
    }

    // Test filtering by minPlayers with NOT_EQUALS operator
    @Test
    public void testMinPlayersNotEqual() {
        List<BoardGame> filtered = planner.filter("minplayers != 2").toList();
        assertEquals(4, filtered.size());
        for (BoardGame game : filtered) {
            assertNotEquals(2, game.getMinPlayers());
        }
    }

    // Test filtering by minPlayers with EQUALS operator
    @Test
    public void testMinPlayersEqual() {
        List<BoardGame> filtered = planner.filter("minplayers == 2").toList();
        assertEquals(4, filtered.size());
        for (BoardGame game : filtered) {
            assertEquals(2, game.getMinPlayers());
        }
    }

    // Test filtering by maxPlayers with GREATER_THAN operator
    @Test
    public void testMaxPlayersGreaterThan() {
        List<BoardGame> filtered = planner.filter("maxplayers > 8").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMaxPlayers() > 8);
        }
    }

    // Test filtering by maxPlayers with LESS_THAN operator
    @Test
    public void testMaxPlayersLessThan() {
        List<BoardGame> filtered = planner.filter("maxplayers < 7").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMaxPlayers() < 7);
        }
    }

    // Test filtering by maxPlayers with GREATER_THAN_EQUALS operator
    @Test
    public void testMaxPlayersGreaterThanOrEqual() {
        List<BoardGame> filtered = planner.filter("maxplayers >= 10").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMaxPlayers() >= 10);
        }
    }

    // Test filtering by maxPlayers with LESS_THAN_EQUALS operator
    @Test
    public void testMaxPlayersLessThanOrEqual() {
        List<BoardGame> filtered = planner.filter("maxplayers <= 6").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMaxPlayers() <= 6);
        }
    }

    // Test filtering by maxPlayers with NOT_EQUALS operator
    @Test
    public void testMaxPlayersNotEqual() {
        List<BoardGame> filtered = planner.filter("maxplayers != 10").toList();
        assertTrue(filtered.size() < games.size());
        for (BoardGame game : filtered) {
            assertNotEquals(10, game.getMaxPlayers());
        }
    }

    // Test filtering by maxPlayers with EQUALS operator
    @Test
    public void testMaxPlayersEqual() {
        List<BoardGame> filtered = planner.filter("maxplayers == 10").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertEquals(10, game.getMaxPlayers());
        }
    }

    // Test filtering by minPlayTime with GREATER_THAN operator
    @Test
    public void testMinPlayTimeGreaterThan() {
        List<BoardGame> filtered = planner.filter("minplaytime > 50").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMinPlayTime() > 50);
        }
    }

    // Test filtering by minPlayTime with LESS_THAN operator
    @Test
    public void testMinPlayTimeLessThan() {
        List<BoardGame> filtered = planner.filter("minplaytime < 30").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMinPlayTime() < 30);
        }
    }

    // Test filtering by minPlayTime with GREATER_THAN_EQUALS operator
    @Test
    public void testMinPlayTimeGreaterThanOrEqual() {
        List<BoardGame> filtered = planner.filter("minplaytime >= 50").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMinPlayTime() >= 50);
        }
    }

    // Test filtering by minPlayTime with LESS_THAN_EQUALS operator
    @Test
    public void testMinPlayTimeLessThanOrEqual() {
        List<BoardGame> filtered = planner.filter("minplaytime <= 30").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMinPlayTime() <= 30);
        }
    }

    // Test filtering by maxPlayTime with various operators
    @Test
    public void testMaxPlayTime() {
        // Test equals
        List<BoardGame> filtered = planner.filter("maxplaytime == 30").toList();
        for (BoardGame game : filtered) {
            assertEquals(30, game.getMaxPlayTime());
        }

        // Test greater than
        planner = new Planner(games);
        filtered = planner.filter("maxplaytime > 90").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMaxPlayTime() > 90);
        }

        // Test less than
        planner = new Planner(games);
        filtered = planner.filter("maxplaytime < 30").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMaxPlayTime() < 30);
        }
    }

    // Test filtering by difficulty with various operators
    @Test
    public void testDifficulty() {
        // Test equals
        List<BoardGame> filtered = planner.filter("difficulty == 9.0").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertEquals(9.0, game.getDifficulty(), 0.01);
        }

        // Test greater than
        planner = new Planner(games);
        filtered = planner.filter("difficulty > 8.0").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getDifficulty() > 8.0);
        }

        // Test less than
        planner = new Planner(games);
        filtered = planner.filter("difficulty < 7.0").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getDifficulty() < 7.0);
        }
    }

    // Test filtering by rank with various operators
    @Test
    public void testRank() {
        // Test equals
        List<BoardGame> filtered = planner.filter("rank == 300").toList();
        assertEquals(1, filtered.size());
        assertEquals(300, filtered.get(0).getRank());

        // Test greater than
        planner = new Planner(games);
        filtered = planner.filter("rank > 500").toList();
        assertEquals(3, filtered.size());
        for (BoardGame game : filtered) {
            assertTrue(game.getRank() > 500);
        }

        // Test less than
        planner = new Planner(games);
        filtered = planner.filter("rank < 400").toList();
        assertEquals(3, filtered.size());
        for (BoardGame game : filtered) {
            assertTrue(game.getRank() < 400);
        }
    }

    // Test filtering by rating with various operators
    @Test
    public void testRating() {
        // Test equals
        List<BoardGame> filtered = planner.filter("rating == 8.0").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertEquals(8.0, game.getRating(), 0.01);
        }

        // Test greater than
        planner = new Planner(games);
        filtered = planner.filter("rating > 8.0").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getRating() > 8.0);
        }

        // Test less than
        planner = new Planner(games);
        filtered = planner.filter("rating < 7.0").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getRating() < 7.0);
        }
    }

    // Test filtering by yearPublished with various operators
    @Test
    public void testYearPublished() {
        // Test equals
        List<BoardGame> filtered = planner.filter("year == 2003").toList();
        assertEquals(1, filtered.size());
        assertEquals(2003, filtered.get(0).getYearPublished());

        // Test greater than
        planner = new Planner(games);
        filtered = planner.filter("year > 2005").toList();
        assertEquals(2, filtered.size());
        for (BoardGame game : filtered) {
            assertTrue(game.getYearPublished() > 2005);
        }

        // Test less than
        planner = new Planner(games);
        filtered = planner.filter("year < 2002").toList();
        assertEquals(2, filtered.size());
        for (BoardGame game : filtered) {
            assertTrue(game.getYearPublished() < 2002);
        }
    }

    // Test empty filter
    @Test
    public void testEmptyFilter() {
        List<BoardGame> filtered = planner.filter("").toList();
        assertEquals(games.size(), filtered.size());
    }

    // Test chained filters
    @Test
    public void testChainedFilters() {
        // Multiple filters separated by semicolon
        List<BoardGame> filtered = planner.filter("minplayers >= 2,maxplayers <= 10").toList();
        assertTrue(filtered.size() > 0);
        for (BoardGame game : filtered) {
            assertTrue(game.getMinPlayers() >= 2 && game.getMaxPlayers() <= 10);
        }
    }

    // Test filters with spaces
    @Test
    public void testFiltersWithSpaces() {
        // Filter with spaces that should be ignored
        List<BoardGame> filtered = planner.filter("name == Go").toList();
        assertEquals(1, filtered.size());
        assertEquals("Go", filtered.get(0).getName());

        // Filter with more spaces
        planner = new Planner(games);
        filtered = planner.filter("minplayers   >   5").toList();
        assertEquals(3, filtered.size());
        for (BoardGame game : filtered) {
            assertTrue(game.getMinPlayers() > 5);
        }
    }

    // Test filter with sort by name descending
    @Test
    public void testFilterSortNameDesc() {
        List<BoardGame> filtered = planner.filter("", GameData.NAME, false).toList();
        assertEquals(games.size(), filtered.size());

        // Check if sorted in descending order
        for (int i = 0; i < filtered.size() - 1; i++) {
            assertTrue(filtered.get(i).getName().compareToIgnoreCase(filtered.get(i + 1).getName()) >= 0);
        }
    }

    // Test filter with sort by minPlayers ascending
    @Test
    public void testFilterByMinPlayersAsc() {
        List<BoardGame> filtered = planner.filter("", GameData.MIN_PLAYERS, true).toList();
        assertEquals(games.size(), filtered.size());

        // Check if sorted in ascending order
        for (int i = 0; i < filtered.size() - 1; i++) {
            assertTrue(filtered.get(i).getMinPlayers() <= filtered.get(i + 1).getMinPlayers());
        }
    }

    // Test filter with sort by minPlayers descending
    @Test
    public void testFilterByMinPlayersDesc() {
        List<BoardGame> filtered = planner.filter("", GameData.MIN_PLAYERS, false).toList();
        assertEquals(games.size(), filtered.size());

        // Check if sorted in descending order
        for (int i = 0; i < filtered.size() - 1; i++) {
            assertTrue(filtered.get(i).getMinPlayers() >= filtered.get(i + 1).getMinPlayers());
        }
    }

    // Test reset functionality
    @Test
    public void testFilterReset() {
        // Apply a filter that returns a subset
        List<BoardGame> filtered = planner.filter("minplayers > 5").toList();
        assertTrue(filtered.size() < games.size());

        // Reset the planner
        planner.reset();

        // Check if all games are available again
        filtered = planner.filter("").toList();
        assertEquals(games.size(), filtered.size());
    }
}