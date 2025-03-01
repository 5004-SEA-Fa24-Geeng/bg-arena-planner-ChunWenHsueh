package student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class GameList implements IGameList {

    /** Set of board games. */
    private Set<BoardGame> games;

    /**
     * Constructor for the GameList.
     */
    public GameList() {
        this.games = new HashSet<>();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getGameNames() {
        List<String> gameNames = games.stream()
                .map(BoardGame::getName)
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
        return gameNames;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        games.clear();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int count() {
        return games.size();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void saveGame(String filename) {
        try {
            List<String> gameNames = getGameNames();
            Files.write(Path.of(filename), gameNames, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error saving game list: " + e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addToList(String str, Stream<BoardGame> filtered) throws IllegalArgumentException {
        List<BoardGame> gamesList = filtered
                .sorted(Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER)).toList();

        // check if str is all
        if (str.equalsIgnoreCase("all")) {
            games.addAll(gamesList);
            return;
        }

        // check if str is a range
        if (str.matches("^\\d+-\\d+$")) {
            String[] parts = str.split("-");
            int start = Integer.parseInt(parts[0]) - 1;
            int end = Math.min(Integer.parseInt(parts[1]) - 1, gamesList.size() - 1);

            // Validate range
            if (start < 0) {
                throw new IllegalArgumentException("Must start from 1 or greater");
            }

            for (int i = start; i <= end; i++) {
                games.add(gamesList.get(i));
            }
            return;
        }

        // check if str is a single number
        if (str.matches("^\\d+$")) {
            int index = Integer.parseInt(str) - 1;

            if (index < 0 || index >= gamesList.size()) {
                throw new IllegalArgumentException(
                        "Index " + index + " out of bounds: list size is " + gamesList.size());
            }
            games.add(gamesList.get(index));
            return;
        }

        // check if str is a single name
        Optional<BoardGame> gameByName = gamesList.stream()
                .filter(game -> game.getName().equalsIgnoreCase(str))
                .findFirst();

        if (gameByName.isPresent()) {
            games.add(gameByName.get());
            return;
        }

        // If we reach here, the input didn't match any valid format
        throw new IllegalArgumentException("Invalid input format: " + str);

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFromList(String str) throws IllegalArgumentException {
        List<BoardGame> gamesList = new ArrayList<>(games);
        gamesList.sort(Comparator.comparing(BoardGame::getName));

        // check if str is all
        if (str.equalsIgnoreCase("all")) {
            clear();
            return;
        }

        // check if str is a range
        if (str.matches("^\\d+-\\d+$")) {
            String[] parts = str.split("-");
            int start = Integer.parseInt(parts[0]) - 1;
            int end = Math.min(Integer.parseInt(parts[1]) - 1, games.size() - 1);

            // Validate range
            if (start < 0) {
                throw new IllegalArgumentException("Must start from 1 or greater");
            }

            for (int i = start; i <= end; i++) {
                games.remove(gamesList.get(i));
            }
            return;
        }

        // check if str is a single number
        if (str.matches("^\\d+$")) {
            int index = Integer.parseInt(str) - 1;

            if (index < 0 || index >= gamesList.size()) {
                throw new IllegalArgumentException(
                        "Index " + index + " out of bounds: list size is " + gamesList.size());
            }
            games.remove(gamesList.get(index));
            return;
        }

        // check if str is a single name
        Optional<BoardGame> gameByName = gamesList.stream()
                .filter(game -> game.getName().equalsIgnoreCase(str))
                .findFirst();

        if (gameByName.isPresent()) {
            games.remove(gameByName.get());
            return;
        }

        // If we reach here, the input didn't match any valid format
        throw new IllegalArgumentException("Invalid input format: " + str);
    }

}
