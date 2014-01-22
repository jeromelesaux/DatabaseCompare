package fr.axione.dbcompare.model.dmditem;

import java.io.File;
import java.io.FilenameFilter;


/**
 * Created by jlesaux on 22/01/14.
 */
public class DmdProjectConstants {
    String rootPatternXmlFile;
    String relDirectoryPath;
    String objectsLocalFilePath;
    final static String OBJECTLOCALNAME = File.separator + "Objects.local";

    public DmdProjectConstants() {

    }
    public DmdProjectConstants(String rootXmlFilePath) throws Exception {
        this();
        rootPatternXmlFile=rootXmlFilePath;
        File relDirectory = new File(rootPatternXmlFile + File.pathSeparator + "rel" );
        String[] filesInRelDirectory  = relDirectory.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                if (dir.isDirectory()) {
                    return true;
                }
                return false;
            }
        });
        if (filesInRelDirectory.length == 0 ) {
            throw new Exception("No directory found in directory " + relDirectory.getPath());
        }

        relDirectoryPath = filesInRelDirectory[0];
        objectsLocalFilePath = relDirectoryPath + OBJECTLOCALNAME;


    }

    public String getRootPatternXmlFile() {
        return rootPatternXmlFile;

    }

    public void setRootPatternXmlFile(String rootPatternXmlFile) {
        this.rootPatternXmlFile = rootPatternXmlFile;
    }

    public String getRelDirectoryPath() {
        return relDirectoryPath;
    }

    public void setRelDirectoryPath(String relDirectoryPath) {
        this.relDirectoryPath = relDirectoryPath;
    }

    public String getObjectsLocalFilePath() {
        return objectsLocalFilePath;
    }

    public void setObjectsLocalFilePath(String objectsLocalFilePath) {
        this.objectsLocalFilePath = objectsLocalFilePath;
    }
}