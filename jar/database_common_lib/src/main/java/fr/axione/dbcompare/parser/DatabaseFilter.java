package fr.axione.dbcompare.parser;

import java.util.regex.Pattern;

/**
 * Created by jlesaux on 27/01/14.
 */
public class DatabaseFilter {
    String tablePattern;
    String columnPattern;
    String viewPattern;
    Pattern tableRegexPattern;
    Pattern columnRegexPattern;
    Pattern viewRegexPattern;
    Boolean isRegex;

    public DatabaseFilter() {

    }
    public DatabaseFilter(String tablePattern, String columnPattern, String viewPattern){
        this.tablePattern = tablePattern;
        this.columnPattern = columnPattern;
        this.viewPattern = viewPattern;
        this.isRegex =false;
    }
    public DatabaseFilter(String tablePattern, String columnPattern, String viewPattern, Boolean isRegex){
        this(tablePattern,columnPattern,viewPattern);
        this.isRegex = isRegex;
        if (isRegex) {
            if (tablePattern != null){
                String tableRegex = "/" + tablePattern.replaceAll("%",".*") + "/";
                tableRegexPattern = Pattern.compile(tableRegex);
            }
            if (columnPattern != null) {
                String columnRegex = "/" + columnPattern.replaceAll("%",".*") + "/";
                columnRegexPattern = Pattern.compile(columnRegex);
            }
            if (viewPattern !=null) {
                String viewRegex = "/" + viewPattern.replaceAll("%",".*") + "/";
                viewRegexPattern = Pattern.compile(viewRegex);
            }
        }
    }

    public String getTablePattern() {
        return tablePattern;
    }

    public void setTablePattern(String tablePattern) {
        this.tablePattern = tablePattern;
    }

    public String getColumnPattern() {
        return columnPattern;
    }

    public void setColumnPattern(String columnPattern) {
        this.columnPattern = columnPattern;
    }

    public String getViewPattern() {
        return viewPattern;
    }

    public void setViewPattern(String viewPattern) {
        this.viewPattern = viewPattern;
    }


    public Boolean tableMatchesRegex(String input) {
        if (tableRegexPattern == null ){
            return true;
        }
        return tableRegexPattern.matcher(input).find();
    }

    public Boolean columnMatchesRegex(String input) {
        if (columnRegexPattern == null ){
            return true;
        }
        return columnRegexPattern.matcher(input).find();
    }

    public Boolean viewMatchesRegex(String input) {
        if (viewRegexPattern == null ){
            return true;
        }
        return viewRegexPattern.matcher(input).find();
    }
}
