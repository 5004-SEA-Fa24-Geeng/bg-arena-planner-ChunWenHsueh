# Report

Submitted report to be manually graded. We encourage you to review the report as you read through the provided
code as it is meant to help you understand some of the concepts. 

## Technical Questions

1. What is the difference between == and .equals in java? Provide a code example of each, where they would return different results for an object. Include the code snippet using the hash marks (```) to create a code block.

== compares the memory location of the object, while .equals compares the value of the object.

```java
// your code here
String s1 = new String("hello");
String s2 = new String("hello");

System.out.println(s1 == s2); // false
System.out.println(s1.equals(s2)); // true
```

For example, in the above code, s1 and s2 are two different objects in memory, so == returns false, but they have the same value, so .equals returns true.

2. Logical sorting can be difficult when talking about case. For example, should "apple" come before "Banana" or after? How would you sort a list of strings in a case-insensitive manner? 

The default sorting would compare the ASCII code, hence, "Banana" will come before "apple". We could sort a list of string in a case-insensitive manner by using the `compareToIgnoreCase` method in the `String` class. 

3. In our version of the solution, we had the following code (snippet)
    ```java
    public static Operations getOperatorFromStr(String str) {
        if (str.contains(">=")) {
            return Operations.GREATER_THAN_EQUALS;
        } else if (str.contains("<=")) {
            return Operations.LESS_THAN_EQUALS;
        } else if (str.contains(">")) {
            return Operations.GREATER_THAN;
        } else if (str.contains("<")) {
            return Operations.LESS_THAN;
        } else if (str.contains("=="))...
    ```
    Why would the order in which we checked matter (if it does matter)? Provide examples either way proving your point. 

If we first check for ">", then both ">" and ">=" will be matched. Hence, we should check for ">= " first. The same applies to "<" and "<=".

4. What is the difference between a List and a Set in Java? When would you use one over the other? 

List is an ordered collection that allows duplicates, while Set is an unordered collection that does not allow duplicates. We would use List when we need to maintain the order of elements and allow duplicates, while we would use Set when we want to ensure that all elements are unique and do not care about the order.


5. In [GamesLoader.java](src/main/java/student/GamesLoader.java), we use a Map to help figure out the columns. What is a map? Why would we use a Map here? 

A Map is a collection that stores key-value pairs, where each key is unique and maps to a single value. We would use a Map here to easily look up the column names based on the index. In the GamesLoader.java, we use a Map to store the column names and their corresponding index, allowing us to quickly access the column name based on the index.


6. [GameData.java](src/main/java/student/GameData.java) is actually an `enum` with special properties we added to help with column name mappings. What is an `enum` in Java? Why would we use it for this application?

An enum is used to define collections of constants. We could use it for this application to define the column names as constants, instead of using magic strings throughout the code. This makes the code more readable and less error-prone.

7. Rewrite the following as an if else statement inside the empty code block.
    ```java
    switch (ct) {
        case CMD_QUESTION: // same as help
        case CMD_HELP:
            processHelp();
            break;
        case INVALID:
        default:
            CONSOLE.printf("%s%n", ConsoleText.INVALID);
    }
    ``` 

    ```java
    // your code here, don't forget the class name that is dropped in the switch block..
    if (ct == CMD_QUESTION || ct == CMD_HELP) {
        processHelp();
    } else {
        CONSOLE.printf("%s%n", ConsoleText.INVALID);
    }
    ```

## Deeper Thinking

ConsoleApp.java uses a .properties file that contains all the strings
that are displayed to the client. This is a common pattern in software development
as it can help localize the application for different languages. You can see this
talked about here on [Java Localization – Formatting Messages](https://www.baeldung.com/java-localization-messages-formatting).

Take time to look through the console.properties file, and change some of the messages to
another language (probably the welcome message is easier). It could even be a made up language and for this - and only this - alright to use a translator. See how the main program changes, but there are still limitations in 
the current layout. 

Post a copy of the run with the updated languages below this. Use three back ticks (```) to create a code block. 

```text
*******歡迎來到桌遊競技場規劃器。*******

這是一個幫助人們規劃他們想在桌遊競技場玩的遊戲的工具。

要開始使用，請在下方輸入您的第一個指令，或輸入 ? 或 help 查看指令選項。
```

Now, thinking about localization - we have the question of why does it matter? The obvious
one is more about market share, but there may be other reasons.  I encourage
you to take time researching localization and the importance of having programs
flexible enough to be localized to different languages and cultures. Maybe pull up data on the
various spoken languages around the world? What about areas with internet access - do they match? Just some ideas to get you started. Another question you are welcome to talk about - what are the dangers of trying to localize your program and doing it wrong? Can you find any examples of that? Business marketing classes love to point out an example of a car name in Mexico that meant something very different in Spanish than it did in English - however [Snopes has shown that is a false tale](https://www.snopes.com/fact-check/chevrolet-nova-name-spanish/). As a developer, what are some things you can do to reduce 'hick ups' when expanding your program to other languages?

As a reminder, deeper thinking questions are meant to require some research and to be answered in a paragraph for with references. The goal is to open up some of the discussion topics in CS, so you are better informed going into industry. 

Localization is important because it makes the software more accessible to users who speak different languages. It can also help to improve the user experience by providing a more familiar interface. However, localization is not that simple. For example, simplified chinese and traditional chinese are very similar (every simplified chinese character can map to a traditional chinese character), but they cannot be translated directly. Let's use the word "video" as an example. Is can be translated to "视频" in simplified chinese, and we can also translate "视频" to "視頻" in traditional chinese. However, in traditional chinese, we would translate "video" to "影片" instead of "視頻". In traditional chinese speaking countries, some people may object to others using the word "视频" because of political affiliation. After all, we should be careful when localizing our program, and we should always respect the culture of the users.