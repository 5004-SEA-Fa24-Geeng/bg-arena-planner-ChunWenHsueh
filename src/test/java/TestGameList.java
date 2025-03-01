import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import static org.junit.jupiter.api.Assertions.*;

import student.BoardGame;
import student.GameList;
import student.IGameList;

public class TestGameList {
    private static Set<BoardGame> games;
    private IGameList gameList;

    @TempDir
    Path tempDir;

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
    public void initGameList() {
        gameList = new GameList();
    }

    @Test
    public void testEmptyGameList() {
        assertEquals(0, gameList.count());
        assertTrue(gameList.getGameNames().isEmpty());
    }

    @Test
    public void testAddToListAll() {
        gameList.addToList("all", games.stream());
        assertEquals(8, gameList.count());
    }

    @Test
    public void testAddToListByIndex() {
        // Add single game by index
        gameList.addToList("1", games.stream());
        assertEquals(1, gameList.count());

        // Add range of games by index
        gameList.clear();
        gameList.addToList("2-4", games.stream().sorted((g1, g2) -> g1.getName().compareToIgnoreCase(g2.getName())));
        assertEquals(3, gameList.count());
    }

    @Test
    public void testAddToListByName() {
        gameList.addToList("Chess", games.stream());
        assertEquals(1, gameList.count());
        assertEquals("Chess", gameList.getGameNames().get(0));
    }

    @Test
    public void testAddToListInvalidInput() {
        // Invalid index
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("20", games.stream());
        });
        assertTrue(exception.getMessage().contains("out of bounds"));

        // Invalid format
        exception = assertThrows(IllegalArgumentException.class, () -> {
            gameList.addToList("invalid-format", games.stream());
        });
        assertTrue(exception.getMessage().contains("Invalid input format"));
    }

    @Test
    public void testRemoveFromList() {
        gameList.addToList("all", games.stream());
        assertEquals(8, gameList.count());

        // Remove by index
        gameList.removeFromList("1");
        assertEquals(7, gameList.count());

        // Remove by range
        gameList.removeFromList("2-4");
        assertEquals(4, gameList.count());

        // Remove by name
        gameList.removeFromList("Monopoly");
        assertEquals(3, gameList.count());

        // Remove all
        gameList.removeFromList("all");
        assertEquals(0, gameList.count());
    }

    @Test
    public void testRemoveFromListInvalidInput() {
        gameList.addToList("all", games.stream());

        // Invalid index
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("20");
        });
        assertTrue(exception.getMessage().contains("out of bounds"));

        // Invalid format
        exception = assertThrows(IllegalArgumentException.class, () -> {
            gameList.removeFromList("invalid-format");
        });
        assertTrue(exception.getMessage().contains("Invalid input format"));
    }

    @Test
    public void testClear() {
        gameList.addToList("all", games.stream());
        assertEquals(8, gameList.count());

        gameList.clear();
        assertEquals(0, gameList.count());
    }

    @Test
    public void testSaveGame() throws IOException {
        gameList.addToList("all", games.stream());

        Path filePath = tempDir.resolve("games.txt");
        gameList.saveGame(filePath.toString());

        List<String> lines = Files.readAllLines(filePath);
        assertEquals(8, lines.size());
        assertTrue(lines.contains("Chess"));
        assertTrue(lines.contains("Monopoly"));
    }
}
