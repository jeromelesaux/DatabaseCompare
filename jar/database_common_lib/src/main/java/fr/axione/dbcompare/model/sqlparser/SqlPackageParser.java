package fr.axione.dbcompare.model.sqlparser;

import fr.axione.dbcompare.model.common.ColumnType;
import fr.axione.dbcompare.model.common.ProcedureColumnType;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jlesaux on 12/02/14.
 */
public class SqlPackageParser {
    SqlPackage sqlPackage;
    String codeSql;
    /*
        Regex package name and catalogue : [P|p][A|a][C|c][K|k][A|a][G|g][E|e]\s+([^.]+).([^\s]+)\s+(([[A|a][S|s])|([I|i][S|s]])).*
        Regex procedure Oracle : ^\s?((P|p)(R|r)(O|o)(C|c)(E|e)(D|d)(U|u)(R|r)(E|e)[^;]*)
        Regex function Oracle : ^\s?((F|f)(U|u)(N|n)(C|c)(T|t)(I|i)(O|o)(N|n)[^;]*)
        Regex to get all arguments : \(([^\)]*)\)
     */

    public SqlPackageParser(String codeSql) {
        this.codeSql = codeSql;
        sqlPackage = new SqlPackage();
    }

    public void parse() {
         parsePackage();
    }

    protected void parsePackage() {
        Pattern packagePattern = Pattern.compile("[P|p][A|a][C|c][K|k][A|a][G|g][E|e]\\s+([^.]+).([^\\s]+)\\s+(([[A|a][S|s])|([I|i][S|s]])).*");
        Matcher packageMatcher = packagePattern.matcher(codeSql);
        if (packageMatcher.find()){
            String catalogue = packageMatcher.group(1);
            String packageName = packageMatcher.group(2);
            sqlPackage.setCatalogue(catalogue);
            sqlPackage.setName(packageName);
        }
        parseProcedure();
        parseFunction();
    }

    protected void parseProcedure() {
        Pattern procedurePattern = Pattern.compile("[P|p][R|r][O|o][C|c][E|e][D|d][U|u][R|r][E|e]([^;|*]*)");
        Matcher procedureMatcher = procedurePattern.matcher(codeSql);
        while (procedureMatcher.find()) {
            String procedureDeclaration = procedureMatcher.group(1);
            String procedureName = parseProcedureFunctionName(procedureDeclaration);
            if (procedureName != null && procedureName.length()>0) {
                SqlProcedureFunction procedure = new SqlProcedureFunction();
                procedure.setName(procedureName);
                procedure.setArguments(parseArguments(procedureDeclaration));
                sqlPackage.getProcedures().add(procedure);
            }
        }
    }

    protected void parseFunction() {
        Pattern functionPattern = Pattern.compile("[F|f][U|u][N|n][C|c][T|t][I|i][O|o][N|n]([^;|*]*)");
        Matcher functionMatcher = functionPattern.matcher(codeSql);
        while (functionMatcher.find()) {
            String procedureDeclaration = functionMatcher.group(1);
            String procedureName = parseProcedureFunctionName(procedureDeclaration);
            if (procedureName != null) {
                SqlProcedureFunction function = new SqlProcedureFunction();
                function.setName(procedureName);
                function.setArguments(parseArguments(procedureDeclaration));
                sqlPackage.getFunctions().add(function);
            }
        }
    }

    protected String parseProcedureFunctionName(String declaration) {
        String name = null;
        Pattern namePattern = Pattern.compile("^\\s+([^(|\\s]*)");
        Matcher nameMatcher = namePattern.matcher(declaration);
        if (nameMatcher.find()){
            name = nameMatcher.group(1);
        }
        return name;
    }

    protected List<SqlArgument> parseArguments(String declaration) {
        List<SqlArgument> arguments = new ArrayList<SqlArgument>();
        Pattern argsPattern = Pattern.compile("\\(([^)]*)");
        Matcher argsMatcher = argsPattern.matcher(declaration);
        while (argsMatcher.find()) {
            String argsList = argsMatcher.group(1).replaceAll("\\s+"," ");
            String[] args = argsList.split(",");
            for (int i=0; i < args.length;i++) {
                Pattern argPattern = Pattern.compile("^\\s?([^\\s]+)\\s([i][n]|[o][u][t]|[I][N]|[O][U][T]\\s]+)?\\s?([^$\\s]*)");
                Matcher argMatcher = argPattern.matcher(args[i]);
                while (argMatcher.find()) {
                    SqlArgument sqlArg = new SqlArgument(argMatcher.group(1));
                    String inOutOpt = argMatcher.group(2);
                    String type = argMatcher.group(3).toUpperCase();
                    if (type.contains("%ROWTYPE") || type.contains("%TYPE")) {
                        sqlArg.setColumnPointer(type.replace("%ROWTYPE","").replace("%TYPE",""));
                    }
                    else {
                        try {
                            type = type.replace(' ','_').replace('/','_');
                            ColumnType columnType = ColumnType.valueOf(type);
                            sqlArg.setType(columnType == null ? ColumnType.UNKNOWN : columnType);
                        }   catch (IllegalArgumentException e ) {
                            sqlArg.setType(ColumnType.UNKNOWN);
                        }
                    }
                    if (inOutOpt!=null) {
                        inOutOpt = inOutOpt.toUpperCase();
                        if (inOutOpt.contains("OUT") && inOutOpt.contains("IN")) {
                            sqlArg.setArgType(ProcedureColumnType.PROCEDURECOLUMNINOUT);
                        }
                        else if (inOutOpt.contains("OUT")) {
                            sqlArg.setArgType(ProcedureColumnType.PROCEDURECOLUMNOUT);
                        }
                        else if (inOutOpt.contains("IN")) {
                            sqlArg.setArgType(ProcedureColumnType.PROCEDURECOLUMNIN);
                        }
                        else {
                            sqlArg.setArgType(ProcedureColumnType.PROCEDURECOLUMNUNKNOWN);
                        }
                    }

                    arguments.add(sqlArg);
                }
            }

        }

        return arguments;
    }

    public SqlPackage getSqlPackage() {
        return sqlPackage;
    }

    public void setSqlPackage(SqlPackage sqlPackage) {
        this.sqlPackage = sqlPackage;
    }

    public String getCodeSql() {
        return codeSql;
    }

    public void setCodeSql(String codeSql) {
        this.codeSql = codeSql;
    }
}
