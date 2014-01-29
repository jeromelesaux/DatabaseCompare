package fr.axione.dbcompare.Tool;


import com.martiansoftware.jsap.*;

import java.util.Iterator;


/**
 * Created by jlesaux on 28/01/14.
 */
public class CmdLineTool {

    public static void main(String[] args) throws JSAPException {
        JSAP jsap = setCmdLineOptions();

        JSAPResult jsapResult = jsap.parse(args);

        helpUsage(jsapResult,jsap);

        String[] types = jsapResult.getStringArray("type");
        for (int i = 0 ; i < types.length ; i++){
            System.out.println(types[i]);
        }



        System.exit(0);
    }


    protected static JSAP setCmdLineOptions() throws JSAPException {
        JSAP jsap = new JSAP();

//        Switch rightOpt = new Switch("rightSchema")
//                .setShortFlag('R')
//                .setLongFlag(JSAP.NO_LONGFLAG);
//
//        rightOpt.setHelp("Database to compare with. (R for right operand)");
//
//        jsap.registerParameter(rightOpt);
//
//
//        Switch leftOpt = new Switch("leftSchema")
//                .setShortFlag('L')
//                .setLongFlag(JSAP.NO_LONGFLAG);
//
//        leftOpt.setHelp("Database pivot. (L for left operand)");
//
//        jsap.registerParameter(leftOpt);

        FlaggedOption typeOpt = new FlaggedOption("type")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('T')
                .setAllowMultipleDeclarations(true)
                .setLongFlag(JSAP.NO_LONGFLAG);

        typeOpt.setHelp("Type (DMD Oracle datamodeler or connection string) \n values :\n - <dmd> for a DMD Oracle file from datamodeler \n - <conn> for a connection string");

        jsap.registerParameter(typeOpt);


        FlaggedOption userOption = new FlaggedOption("user")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('u')
                .setAllowMultipleDeclarations(true)
                .setLongFlag(JSAP.NO_LONGFLAG);

        userOption.setHelp("User name for database connection ");

        jsap.registerParameter(userOption);

        FlaggedOption passOption = new FlaggedOption("pass")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('p')
                .setAllowMultipleDeclarations(true)
                .setLongFlag(JSAP.NO_LONGFLAG);

        passOption.setHelp("Password for database connection ");

        jsap.registerParameter(passOption);

        FlaggedOption connectionStringOption = new FlaggedOption("connectionString")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('c')
                .setAllowMultipleDeclarations(true)
                .setLongFlag(JSAP.NO_LONGFLAG);

        connectionStringOption.setHelp("Connection String of the database instance : \n - for MySQL : 'jdbc:mysql://<url>/<db schema>'\n - for Oracle 'jdbc:oracle:thin:@<url>:<port>:<db schema>'");

        jsap.registerParameter(connectionStringOption);

        FlaggedOption dmdFile = new FlaggedOption("dmd_file")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('f')
                .setAllowMultipleDeclarations(true)
                .setLongFlag(JSAP.NO_LONGFLAG);

        dmdFile.setHelp("Path for the DMD datamodeler file");

        jsap.registerParameter(dmdFile);

        return jsap;
    }

    protected static void helpUsage(JSAPResult config, JSAP jsap) {
        if (!config.success()) {

            System.err.println();

            // print out specific error messages describing the problems
            // with the command line, THEN print usage, THEN print full
            // help.  This is called "beating the user with a clue stick."
            for (java.util.Iterator errs = config.getErrorMessageIterator();
                 errs.hasNext();) {
                System.err.println("Error: " + errs.next());
            }

            System.err.println();
            System.err.println("Usage: java "
                    +CmdLineTool.class.getName());
            System.err.println("                "
                    + jsap.getUsage());
            System.err.println();
            System.err.println(jsap.getHelp());
            System.exit(1);

        }

    }
}