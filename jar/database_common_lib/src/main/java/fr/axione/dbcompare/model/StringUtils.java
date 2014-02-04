package fr.axione.dbcompare.model;

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

    public static String replaceLast(String str, String value) {
        int index = str.lastIndexOf(value);
        return str.substring(0,index);
    }
}
