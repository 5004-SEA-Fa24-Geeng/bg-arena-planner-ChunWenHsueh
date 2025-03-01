package student;

import java.util.Comparator;

public final class BoardGameSortStrategy {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private BoardGameSortStrategy() {
    }

    /**
     * Gets a comparator for the given column.
     * 
     * @param column The column to get a comparator for
     * @return A comparator for the column
     */
    public static Comparator<BoardGame> getComparatorForColumn(GameData column) {
        switch (column) {
            case NAME:
                return Comparator.comparing(BoardGame::getName, String.CASE_INSENSITIVE_ORDER);
            case RATING:
                return Comparator.comparing(BoardGame::getRating);
            case DIFFICULTY:
                return Comparator.comparing(BoardGame::getDifficulty);
            case RANK:
                return Comparator.comparing(BoardGame::getRank);
            case MIN_PLAYERS:
                return Comparator.comparing(BoardGame::getMinPlayers);
            case MAX_PLAYERS:
                return Comparator.comparing(BoardGame::getMaxPlayers);
            case MIN_TIME:
                return Comparator.comparing(BoardGame::getMinPlayTime);
            case MAX_TIME:
                return Comparator.comparing(BoardGame::getMaxPlayTime);
            case YEAR:
                return Comparator.comparing(BoardGame::getYearPublished);
            default:
                return Comparator.comparing(BoardGame::getName);
        }
    }
}
