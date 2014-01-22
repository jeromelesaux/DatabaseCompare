package fr.axione.dbcompare.parser.dmd;

import fr.axione.dbcompare.model.dmditem.DmdProjectConstants;
import fr.axione.dbcompare.model.dmditem.Schema;

import java.io.File;

/**
 * Created by jlesaux on 21/01/14.
 */
public class DmdStructure {

    DmdProjectConstants dmdConstants;
    Schema schema;

    public Schema getSchema(String xmlRootFilePath) throws Exception {
        dmdConstants = new DmdProjectConstants(xmlRootFilePath);
        schema = new Schema();




        return schema;
    }


}
