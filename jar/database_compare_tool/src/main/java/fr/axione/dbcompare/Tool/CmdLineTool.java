package fr.axione.dbcompare.Tool;


import com.martiansoftware.jsap.*;
import com.martiansoftware.jsap.stringparsers.BooleanStringParser;
import fr.axione.dbcompare.analyse.Report;
import fr.axione.dbcompare.analyse.ReportItem;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.*;


/**
 * Created by jlesaux on 28/01/14.
 */
public class CmdLineTool {

    public static void main(String[] args) {
        try {
            JSAP jsap = setCmdLineOptions();
            CmdLineSchema rightSchema = new CmdLineSchema();
            CmdLineSchema leftSchema =  new CmdLineSchema();

            JSAPResult jsapResult = jsap.parse(args);

            if (args.length == 0 ){
                helpUsage(jsapResult,jsap,args);
            }

            helpUsage(jsapResult,jsap,args);

            String[] types = jsapResult.getStringArray("type");
            String[] dmdFilepaths = jsapResult.getStringArray("dmd_file");
            String[] users = jsapResult.getStringArray("user");
            String[] connectionStrings = jsapResult.getStringArray("connectionString");
            String[] passwords = jsapResult.getStringArray("pass");
            String[] outputs = jsapResult.getStringArray("output");

            if (types.length > 2) {
                throw new Exception("To many schemas, can compare only 2 schema at once. ");
            }

            if (types[0].toUpperCase().equals("DMD")) {
                leftSchema.setCmdlinetype(CMDLINETYPE.DMDFILE);
            }
            else {
                leftSchema.setCmdlinetype(CMDLINETYPE.DATABASECONNECTION);
            }
            if (types[1].toUpperCase().equals("CONN")) {
                rightSchema.setCmdlinetype(CMDLINETYPE.DATABASECONNECTION);
            }
            else {
                rightSchema.setCmdlinetype(CMDLINETYPE.DMDFILE);
            }

            if (leftSchema.getCmdlinetype() == CMDLINETYPE.DMDFILE) {
                leftSchema.setDmdFilepath(dmdFilepaths[0]);
            }
            else {
                leftSchema.setUrl(connectionStrings[0]);
                leftSchema.setUser(users[0]);
                leftSchema.setPassword(passwords[0]);
            }

            if (rightSchema.getCmdlinetype() == CMDLINETYPE.DMDFILE ) {
                if (leftSchema.getCmdlinetype() == CMDLINETYPE.DMDFILE) {
                    rightSchema.setDmdFilepath(dmdFilepaths[1]);
                }
                else {
                    rightSchema.setDmdFilepath(dmdFilepaths[0]);
                }
            }

            if (rightSchema.getCmdlinetype() == CMDLINETYPE.DATABASECONNECTION ) {
                if ( leftSchema.getCmdlinetype() == CMDLINETYPE.DMDFILE ) {
                    rightSchema.setUrl(connectionStrings[0]);
                    rightSchema.setUser(users[0]);
                    rightSchema.setPassword(passwords[0]);
                }
                else {
                    rightSchema.setUrl(connectionStrings[1]);
                    rightSchema.setUser(users[1]);
                    rightSchema.setPassword(passwords[1]);
                }
            }



            leftSchema.parseSchema();
            System.out.println("Parsing schema for " + leftSchema.toString());

            rightSchema.parseSchema();
            System.out.println("Parsing schema for " + rightSchema.toString());

            System.out.println("Comparing both schemas.");

            if ( ! leftSchema.getSchema().equals(rightSchema.getSchema()) ){
                if (outputs.length == 0 || outputs[0].equals("txt")) {
                    for (ReportItem item : leftSchema.getSchema().getErrors()) {
                        System.out.println(item.getDescription());
                    }
                }
                else {
                    Report report = new Report();
                    report.setErrors(leftSchema.getSchema().getErrors());
                    JAXBContext context = JAXBContext.newInstance(Report.class,ReportItem.class);
                    Marshaller marshaller = context.createMarshaller();
                    marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,true);
                    marshaller.marshal(report, new File("out.xml"));
                }
            }
            else {
                System.out.println("Both schemas are equals.");
            }

            System.exit(0);

        } catch ( Exception e) {
            System.out.println("An error occurs with error : " + e.getMessage());
            StringWriter writer = new StringWriter();
            PrintWriter printer = new PrintWriter(writer);
            e.printStackTrace(printer);
            System.out.println(writer.toString());
            System.exit(2);
        }
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
                .setLongFlag("type");

        typeOpt.setHelp("Type (DMD Oracle datamodeler or connection string) \n values :\n - <dmd> for a DMD Oracle file from datamodeler \n - <conn> for a connection string");

        jsap.registerParameter(typeOpt);


        FlaggedOption userOption = new FlaggedOption("user")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('u')
                .setAllowMultipleDeclarations(true)
                .setLongFlag("user");

        userOption.setHelp("User name for database connection ");

        jsap.registerParameter(userOption);

        FlaggedOption passOption = new FlaggedOption("pass")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('p')
                .setAllowMultipleDeclarations(true)
                .setLongFlag("pass");

        passOption.setHelp("Password for database connection ");

        jsap.registerParameter(passOption);

        FlaggedOption connectionStringOption = new FlaggedOption("connectionString")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('c')
                .setAllowMultipleDeclarations(true)
                .setLongFlag("connection-url");

        connectionStringOption.setHelp("Connection String of the database instance : \n - for MySQL : 'jdbc:mysql://<url>/<db schema>'\n - for Oracle 'jdbc:oracle:thin:@<url>:<port>:<db schema>'");

        jsap.registerParameter(connectionStringOption);

        FlaggedOption dmdFile = new FlaggedOption("dmd_file")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('f')
                .setAllowMultipleDeclarations(true)
                .setLongFlag("dmd-filepath");

        dmdFile.setHelp("Path for the DMD datamodeler file");
        jsap.registerParameter(dmdFile);

        FlaggedOption xmlOpt = new FlaggedOption("output")
                .setStringParser(JSAP.STRING_PARSER)
                .setRequired(false)
                .setShortFlag('o')
                .setAllowMultipleDeclarations(false)
                .setLongFlag("output");

        xmlOpt.setHelp("print report as a Xml file (option -o xml) otherwise -o txt).");
        jsap.registerParameter(xmlOpt);

        return jsap;
    }

    protected static void helpUsage(JSAPResult config, JSAP jsap, String[] args) {
        if (!config.success() || args.length == 0) {

            System.err.println();

            // print out specific error messages describing the problems
            // with the command line, THEN print usage, THEN print full
            // help.  This is called "beating the user with a clue stick."
            for (java.util.Iterator errs = config.getErrorMessageIterator();
                 errs.hasNext();) {
                System.err.println("Error: " + errs.next());
            }

            System.err.println();
            System.err.println("Usage:\n java -jar "
                    +CmdLineTool.class.getName());
            System.err.println("                "
                    + jsap.getUsage());
            System.err.println();
            System.err.println(jsap.getHelp());
            System.err.println("For instance : \n 'java -jar DBCompare.jar -T dmd  -f ST_AXIONE_FACTUFT.dmd -T conn -u userdb -p passwordUser  -c 'jdbc:oracle:thin:@127.0.0.1:1521:USERDB'\n"
                   +" will compare the pivot schema in dmd oracle file to the schema on ther server 127.0.0.1.");
            System.exit(1);

        }

    }
}
