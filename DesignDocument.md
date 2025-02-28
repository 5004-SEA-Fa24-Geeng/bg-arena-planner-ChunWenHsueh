# Board Game Arena Planner Design Document


This document is meant to provide a tool for you to demonstrate the design process. You need to work on this before you code, and after have a finished product. That way you can compare the changes, and changes in design are normal as you work through a project. It is contrary to popular belief, but we are not perfect our first attempt. We need to iterate on our designs to make them better. This document is a tool to help you do that.


## (INITIAL DESIGN): Class Diagram 

Place your class diagrams below. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. If it is not, you will need to fix it. As a reminder, here is a link to tools that can help you create a class diagram: [Class Resources: Class Design Tools](https://github.com/CS5004-khoury-lionelle/Resources?tab=readme-ov-file#uml-design-tools)

### Provided Code

Provide a class diagram for the provided code as you read through it. For the classes you are adding, you will create them as a separate diagram, so for now, you can just point towards the interfaces for the provided code diagram.

```mermaid
classDiagram
    class IPlanner {
        <<interface>>
        ~ filter(String filter) Stream~BoardGame~
        ~ filter(String filter, GameData sortOn) Stream~BoardGame~
        ~ filter(String filter, GameData sortOn, boolean ascending) Stream~BoardGame~
        ~ reset() void
    }

    class Planner {
    }

    class BoardGame {
        - String name
        - int id
        - int minPlayers
        - int maxPlayers
        - int minPlaytime
        - int maxPlaytime
        - double difficulty
        - int rank
        - double averageRating
        - int yearPublished
        + BoardGame(String name, int id, int minPlayers, int maxPlayers, int minPlaytime, int maxPlaytime, double difficulty, int rank, double averageRating, int yearPublished)
        + getName() String
        + getId() int
        + getMinPlayers() int
        + getMaxPlayers() int
        + getMinPlaytime() int
        + getMaxPlaytime() int
        + getDifficulty() double
        + getRank() int
        + getRating() double
        + getYearPublished() int
        + toStringWIthInfo(GameData col) String
        + toString() String
        + equals(Object obj) boolean
        + hashCode() int
    }

    class GameData {
        <<enumeration>>
        NAME
        ID
        RATING
        DIFFICULTY
        RANK
        MIN_PLAYERS
        MAX_PLAYERS
        MIN_TIME
        MAX_TIME
        YEAR
        - String columnName
        + GameData(String columnName)
        + getColumnName() String
        + fromColumnName(String columnName) GameData
        + fromString(String name) GameData
    }

    class Operations {
        <<enumeration>>
        EQUALS
        NOT_EQUALS
        GREATER_THAN
        LESS_THAN
        GREATER_THAN_EQUALS
        LESS_THAN_EQUALS
        CONTAINS
        - String operator
        + Operations(String operator)
        + getOperator() String
        + fromOperator(String operator) Operations
        + getOperatorFromString(String operator) Operations
    }

    class IGameList {
        <<interface>>
        ~ String ADD_ALL
        ~ getGameNames  List~String~
        ~ clear() void
        ~ count() int
        ~ saveGame(String filename) void
        ~ addToList(String str, Stream~BoardGame~ filtered) void
        ~ removeFromList(String str) void
    }

    class GameList {

    }

    class GamesLoader {
        + String DELIMITER
        - GamesLoader()
        + static loadGamesFile(String filename) Set~BoardGame~ 
        - static toBoardGame(String line, Map<GameData, Integer> columnMap) BoardGame
        - static processHeader(String header) Map<GameData, Integer>
    }

    class ConsoleApp {
        - Scanner IN
        - String DEFAULT_FILENAME
        - Random RND
        - Scanner current
        - IGameList gameList
        - IPlanner planner
        + ConsoleApp(IGameList gameList, IPlanner planner)
        + start() void
        - randomNumber() void
        - processHelp() void
        - processFilter() void
        - printFilterStream(Stream~BoardGame~ games, GameData sortON) void
        - processListCommands() void
        - printCurrentList() void
        - nextCommand() ConsoleText
        - remainder() String
        - getInput(String format, Object... args) String
        - printOutput(String format, Object... output) void
    }

    class ConsoleText {
        <<enumeration>>
        WELCOME
        HELP
        INVALID
        GOODBYE
        PROMPT
        NO_FILTER
        NO_GAMES_LIST
        FILTERED_CLEAR
        LIST_HELP
        FILTER_HELP
        INVALID_LIST
        EASTER_EGG
        CMD_EASTER_EGG
        CMD_EXIT
        CMD_HELP
        CMD_QUESTION
        CMD_FILTER
        CMD_LIST
        CMD_SHOW
        CMD_ADD
        CMD_REMOVE
        CMD_CLEAR
        CMD_SAVE
        CMD_OPTION_ALL
        CMD_SORT_OPTION
        CMD_SORT_OPTION_DIRECTION_ASC
        CMD_SORT_OPTION_DIRECTION_DESC
        - Properties CTEXT
        + toString() String
        + static fromString(String str)$ ConsoleText
    }

    class BGArenaPlanner {
        - String DEFAULT_COLLECTION
        - BGArenaPlanner()
        + main(String[] args) void
    }

    Planner ..|> IPlanner: implements
    Planner --> "many" BoardGame: contains
    GameList ..|> IGameList: implements
    ConsoleApp --> ConsoleText : uses
    ConsoleApp --> IGameList : uses
    ConsoleApp --> IPlanner : uses
    BGArenaPlanner --> IPlanner : creates
    BGArenaPlanner --> IGameList : creates
    BGArenaPlanner --> ConsoleApp : creates
    BGArenaPlanner --> GamesLoader : uses
```

### Your Plans/Design

Create a class diagram for the classes you plan to create. This is your initial design, and it is okay if it changes. Your starting points are the interfaces. 

```mermaid
classDiagram
    class Planner {
        + filter(String filter) Stream~BoardGame~
        + filter(String filter, GameData sortOn) Stream~BoardGame~
        + filter(String filter, GameData sortOn, boolean ascending) Stream~BoardGame~
        + reset() void
        - parseStringtoIntegers(String str) List~Integer~
    }

    class GameList {
        + getGameNames  List~String~
        + clear() void
        + count() int
        + saveGame(String filename) void
        + addToList(String str, Stream~BoardGame~ filtered) void
        + removeFromList(String str) void
        - parseStringtoIntegers(String str) List~Integer~
    }
```



## (INITIAL DESIGN): Tests to Write - Brainstorm

Write a test (in english) that you can picture for the class diagram you have created. This is the brainstorming stage in the TDD process. 

> [!TIP]
> As a reminder, this is the TDD process we are following:
> 1. Figure out a number of tests by brainstorming (this step)
> 2. Write **one** test
> 3. Write **just enough** code to make that test pass
> 4. Refactor/update  as you go along
> 5. Repeat steps 2-4 until you have all the tests passing/fully built program

You should feel free to number your brainstorm. 

1. Test sort by each column in filter
2. Test filter by each column in filter
3. Test constructor of GameBoard
4. Test toString of GameBoard
5. Test equals, hashCode of GameBoard

## (FINAL DESIGN): Class Diagram

Go through your completed code, and update your class diagram to reflect the final design. Make sure you check the file in the browser on github.com to make sure it is rendering correctly. It is normal that the two diagrams don't match! Rarely (though possible) is your initial design perfect. 

For the final design, you just need to do a single diagram that includes both the original classes and the classes you added. 

> [!WARNING]
> If you resubmit your assignment for manual grading, this is a section that often needs updating. You should double check with every resubmit to make sure it is up to date.

```mermaid
classDiagram
    class IPlanner {
        <<interface>>
        ~ filter(String filter) Stream~BoardGame~
        ~ filter(String filter, GameData sortOn) Stream~BoardGame~
        ~ filter(String filter, GameData sortOn, boolean ascending) Stream~BoardGame~
        ~ reset() void
    }

    class Planner {
        + filter(String filter) Stream~BoardGame~
        + filter(String filter, GameData sortOn) Stream~BoardGame~
        + filter(String filter, GameData sortOn, boolean ascending) Stream~BoardGame~
        + reset() void
        - filterSingleCondition(Stream~BoardGame~ filteredGames, String filter) Stream~BoardGame~
        - sortGames(Stream~BoardGame~ filteredGames, GameData sortOn, boolean ascending) Stream~BoardGame~
        - isNumericColumn(GameData column) boolean
        - applyNumericFilter(Stream~BoardGame~ games, GameData column, Operations operator, String value) Stream~BoardGame~ games
        - applyNumericFilterOnSingleGame(BoardGame game, GameData column, Operations operator, double numericValue) boolean
        - applyStringFilter(Stream~BoardGame~ games, GameData column, Operations operator, String value) Stream~BoardGame~
        - applyStringFilterOnSingleGame(BoardGame game, GameData column, Operations operator, String stringValue) boolean
    }

    class BoardGameSortStrategy {
        + getComparatorForColumn(GameData column) Comparator~BoardGame~
    }

    class GameList {
        + getGameNames  List~String~
        + clear() void
        + count() int
        + saveGame(String filename) void
        + addToList(String str, Stream~BoardGame~ filtered) void
        + removeFromList(String str) void
        - parseStringtoIntegers(String str) List~Integer~
    }

    class BoardGame {
        - String name
        - int id
        - int minPlayers
        - int maxPlayers
        - int minPlaytime
        - int maxPlaytime
        - double difficulty
        - int rank
        - double averageRating
        - int yearPublished
        + getName() String
        + getId() int
        + getMinPlayers() int
        + getMaxPlayers() int
        + getMinPlaytime() int
        + getMaxPlaytime() int
        + getDifficulty() double
        + getRank() int
        + getRating() double
        + getYearPublished() int
        + toStringWIthInfo(GameData col) String
        + toString() String
        + equals(Object obj) boolean
        + hashCode() int
        + getNumericValue(GameData col) throws IllegalArgumentException double
        + getStringValue(GameData col) throws IllegalArgumentException String
    }

    class GameData {
        <<enumeration>>
        NAME
        ID
        RATING
        DIFFICULTY
        RANK
        MIN_PLAYERS
        MAX_PLAYERS
        MIN_TIME
        MAX_TIME
        YEAR
        - String columnName
        + GameData(String columnName)
        + getColumnName() String
        + fromColumnName(String columnName) GameData
        + fromString(String name) GameData
    }

    class Operations {
        <<enumeration>>
        EQUALS
        NOT_EQUALS
        GREATER_THAN
        LESS_THAN
        GREATER_THAN_EQUALS
        LESS_THAN_EQUALS
        CONTAINS
        - String operator
        + Operations(String operator)
        + getOperator() String
        + fromOperator(String operator) Operations
        + getOperatorFromString(String operator) Operations
    }

    class IGameList {
        <<interface>>
        ~ String ADD_ALL
        ~ getGameNames  List~String~
        ~ clear() void
        ~ count() int
        ~ saveGame(String filename) void
        ~ addToList(String str, Stream~BoardGame~ filtered) void
        ~ removeFromList(String str) void
    }

    class GamesLoader {
        + String DELIMITER
        - GamesLoader()
        + static loadGamesFile(String filename) Set~BoardGame~ 
        - static toBoardGame(String line, Map<GameData, Integer> columnMap) BoardGame
        - static processHeader(String header) Map<GameData, Integer>
    }

    class ConsoleApp {
        - Scanner IN
        - String DEFAULT_FILENAME
        - Random RND
        - Scanner current
        - IGameList gameList
        - IPlanner planner
        + ConsoleApp(IGameList gameList, IPlanner planner)
        + start() void
        - randomNumber() void
        - processHelp() void
        - processFilter() void
        - printFilterStream(Stream~BoardGame~ games, GameData sortON) void
        - processListCommands() void
        - printCurrentList() void
        - nextCommand() ConsoleText
        - remainder() String
        - getInput(String format, Object... args) String
        - printOutput(String format, Object... output) void
    }

    class ConsoleText {
        <<enumeration>>
        WELCOME
        HELP
        INVALID
        GOODBYE
        PROMPT
        NO_FILTER
        NO_GAMES_LIST
        FILTERED_CLEAR
        LIST_HELP
        FILTER_HELP
        INVALID_LIST
        EASTER_EGG
        CMD_EASTER_EGG
        CMD_EXIT
        CMD_HELP
        CMD_QUESTION
        CMD_FILTER
        CMD_LIST
        CMD_SHOW
        CMD_ADD
        CMD_REMOVE
        CMD_CLEAR
        CMD_SAVE
        CMD_OPTION_ALL
        CMD_SORT_OPTION
        CMD_SORT_OPTION_DIRECTION_ASC
        CMD_SORT_OPTION_DIRECTION_DESC
        - Properties CTEXT
        + toString() String
        + static fromString(String str)$ ConsoleText
    }

    class BGArenaPlanner {
        - String DEFAULT_COLLECTION
        - BGArenaPlanner()
        + main(String[] args) void
    }

    Planner ..|> IPlanner: implements
    Planner --> "many" BoardGame: contains
    GameList ..|> IGameList: implements
    ConsoleApp --> ConsoleText : uses
    ConsoleApp --> IGameList : uses
    ConsoleApp --> IPlanner : uses
    BGArenaPlanner --> IPlanner : creates
    BGArenaPlanner --> IGameList : creates
    BGArenaPlanner --> ConsoleApp : creates
    BGArenaPlanner --> GamesLoader : uses
    Planner --> BoardGameSortStrategy : uses
```

## (FINAL DESIGN): Reflection/Retrospective

> [!IMPORTANT]
> The value of reflective writing has been highly researched and documented within computer science, from learning to information to showing higher salaries in the workplace. For this next part, we encourage you to take time, and truly focus on your retrospective.

Take time to reflect on how your design has changed. Write in *prose* (i.e. do not bullet point your answers - it matters in how our brain processes the information). Make sure to include what were some major changes, and why you made them. What did you learn from this process? What would you do differently next time? What was the most challenging part of this process? For most students, it will be a paragraph or two.

The major change is that I created helper functions in Planner to help with the filtering process. I turn the filters into a list of filter strings then check whether it is a numeric column or a string column. I added the BoardGameSortStrategy class to help with sorting the games.

The code base in this homework is larger than before, and it's harder to know how should BaordGame and Planner function before reading other files. At the beginning, I thought the filter string in Planner is only single filter, I was wrong after seeing how ConsoleApp called Planner. Next time, I should spend more time on how the whole program works, and how each class interact with each other.