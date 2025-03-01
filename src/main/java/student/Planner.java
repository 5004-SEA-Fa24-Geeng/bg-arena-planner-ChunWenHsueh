package student;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;
import java.util.List;
import java.util.Arrays;

public class Planner implements IPlanner {

    /** The games to filter. */
    private Set<BoardGame> games;
    /** The filtered games. */
    private Set<BoardGame> filteredGames;

    /**
     * Constructor for the Planner.
     * 
     * @param games The games to filter.
     */
    public Planner(Set<BoardGame> games) {
        this.games = games;
        this.filteredGames = new HashSet<>(games);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<BoardGame> filter(String filter) {
        return filter(filter, GameData.NAME, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn) {
        return filter(filter, sortOn, true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<BoardGame> filter(String filter, GameData sortOn, boolean ascending) {
        filter = filter.toLowerCase().replaceAll("\\s+", "");
        Stream<BoardGame> filteredStream = filteredGames.stream();

        // If filter is null or empty, return sorted games
        if (filter == null || filter.isEmpty()) {
            return sortGames(filteredStream, sortOn, ascending);
        }

        // else process each requriement one by one
        List<String> filters = List.of(filter.split(","));
        for (String singleFilter : filters) {
            filteredStream = filterSingleCondition(filteredStream, singleFilter);
        }
        return sortGames(filteredStream, sortOn, ascending);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reset() {
        filteredGames = new HashSet<>(games);
    }

    /**
     * Applies a single filter to a stream of games.
     * 
     * If filter is invalid, returns the original stream
     * Invalid means:
     * - operator is not valid
     * - format is not valid
     * - column is not valid
     * 
     * @param filteredGames The stream of games to filter
     * @param filter        The filter to apply
     * @return The filtered stream of games
     */
    private Stream<BoardGame> filterSingleCondition(Stream<BoardGame> filteredGames, String filter) {

        // return original stream if operation is invalid
        Operations operator = Operations.getOperatorFromStr(filter);
        if (operator == null) {
            return filteredGames;
        }

        List<String> parts = Arrays.asList(filter.split(operator.getOperator()));
        // return original stream if format is invalid
        if (parts.size() != 2) {
            return filteredGames;
        }

        // return original stream if column is invalid
        GameData column;
        try {
            column = GameData.fromString(parts.get(0));
        } catch (IllegalArgumentException e) {
            return filteredGames;
        }

        String value = parts.get(1);

        // Apply the appropriate filter based on column type
        if (isNumericColumn(column)) {
            return applyNumericFilter(filteredGames, column, operator, value);
        } else {
            return applyStringFilter(filteredGames, column, operator, value);
        }
    }

    /**
     * Sorts a stream of games based on the given column and order.
     * 
     * @param games     The stream of games to sort
     * @param sortOn    The column to sort on
     * @param ascending Whether to sort in ascending order
     * @return The sorted stream of games
     */
    private Stream<BoardGame> sortGames(Stream<BoardGame> games, GameData sortOn, boolean ascending) {
        List<BoardGame> gameList = games.toList();
        filteredGames = new HashSet<>(gameList);
        Comparator<BoardGame> comparator = BoardGameSortStrategy.getComparatorForColumn(sortOn);
        comparator = ascending ? comparator : comparator.reversed();
        return filteredGames.stream().sorted(comparator);
    }

    /**
     * Checks if a column is numeric.
     * 
     * @param column The column to check
     * @return True if the column is numeric, false otherwise
     */
    private boolean isNumericColumn(GameData column) {
        switch (column) {
            case RATING:
            case DIFFICULTY:
            case RANK:
            case MIN_PLAYERS:
            case MAX_PLAYERS:
            case MIN_TIME:
            case MAX_TIME:
            case YEAR:
                return true;
            default:
                return false;
        }
    }

    /**
     * Applies a numeric filter to a stream of games.
     * 
     * @param games    The stream of games to filter
     * @param column   The column to filter on
     * @param operator The operator to use
     * @param value    The value to filter with
     * @return The filtered stream of games
     */
    private Stream<BoardGame> applyNumericFilter(Stream<BoardGame> games, GameData column,
            Operations operator, String value) {
        double numValue;
        try {
            numValue = Double.parseDouble(value);
        } catch (NumberFormatException e) {
            // If the value is not a number, return the original stream
            return games;
        }

        return games.filter(game -> applyNumericFilterOnSingleGame(game, column, operator, numValue));
    }

    /**
     * Applies a numeric filter to a single game.
     * 
     * @param game         The game to filter
     * @param column       The column to filter on
     * @param operator     The operator to use
     * @param numericValue The value to filter with
     * @return True if the game matches the filter, false otherwise
     */
    private boolean applyNumericFilterOnSingleGame(BoardGame game, GameData column, Operations operator,
            double numericValue) {
        double gameValue;
        try {
            gameValue = game.getNumericValue(column);
        } catch (IllegalArgumentException e) {
            // This should never happen if isNumericColumn is implemented correctly
            return true;
        }
        switch (operator) {
            case EQUALS:
                return gameValue == numericValue;
            case NOT_EQUALS:
                return gameValue != numericValue;
            case GREATER_THAN:
                return gameValue > numericValue;
            case LESS_THAN:
                return gameValue < numericValue;
            case GREATER_THAN_EQUALS:
                return gameValue >= numericValue;
            case LESS_THAN_EQUALS:
                return gameValue <= numericValue;
            default:
                return true;
        }
    }

    /**
     * Applies a string filter to a stream of games.
     * 
     * @param games    The stream of games to filter
     * @param column   The column to filter on
     * @param operator The operator to use
     * @param value    The value to filter with
     * @return The filtered stream of games
     */
    private Stream<BoardGame> applyStringFilter(Stream<BoardGame> games, GameData column,
            Operations operator, String value) {
        return games.filter(game -> applyStringFilterOnSingleGame(game, column, operator, value));
    }

    /**
     * Applies a string filter to a single game.
     * 
     * @param game        The game to filter
     * @param column      The column to filter on
     * @param operator    The operator to use
     * @param stringValue The value to filter with
     * @return True if the game matches the filter, false otherwise
     */
    private boolean applyStringFilterOnSingleGame(BoardGame game, GameData column, Operations operator,
            String stringValue) {
        String gameValue;
        try {
            gameValue = game.getStringValue(column);
        } catch (IllegalArgumentException e) {
            // This should never happen if isNumericColumn is implemented correctly
            return true;
        }
        switch (operator) {
            case EQUALS:
                return gameValue.equalsIgnoreCase(stringValue);
            case NOT_EQUALS:
                return !gameValue.equalsIgnoreCase(stringValue);
            case GREATER_THAN:
                return gameValue.compareToIgnoreCase(stringValue) > 0;
            case LESS_THAN:
                return gameValue.compareToIgnoreCase(stringValue) < 0;
            case GREATER_THAN_EQUALS:
                return gameValue.compareToIgnoreCase(stringValue) >= 0;
            case LESS_THAN_EQUALS:
                return gameValue.compareToIgnoreCase(stringValue) <= 0;
            case CONTAINS:
                return gameValue.toLowerCase().contains(stringValue.toLowerCase());
            default:
                return true;
        }
    }
}
