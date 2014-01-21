package fr.axione.dbcompare.common;

/**
 * Created by jlesaux on 20/01/14.
 */
public class StringUtils {


    public static String stringValueForBoolean(Boolean value) {
        if (value == null) {
            return "null";
        }
        else {
            return value ? "true" : "false";
        }
    }
}
