package core.utils;

import java.util.function.Predicate;

public class DefaultNewName {

    /**
     * Function to avoid name collisions
     * @param defaultName name preceding numbers
     * @param condition passed in lambda to check whether the name collides
     * @return collision-free name
     */
    public static String get(String defaultName, Predicate<String> condition) {
        if (condition.test(defaultName))
            return defaultName;

        int count = 1;

        while (!condition.test(defaultName + count)) {
            count++;
        }
        return defaultName + count;
    }
}
