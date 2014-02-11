package fr.axione.dbcompare.model.dmditem;

import fr.axione.dbcompare.model.StringUtils;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;


/**
 * Created by jlesaux on 22/01/14.
 */
public class DmdProjectConstants {
    String rootPatternXmlFile;
    String relDirectoryPath;
    String objectsLocalFilePath;
    String schemaObjectFilePath;
    String objectsLocalPhysFilepath;
    String physDirectoryPath;
    final static String OBJECTLOCALNAME = File.separator + "Objects.local";

    public DmdProjectConstants() {

    }
    public DmdProjectConstants(String rootXmlFilePath) throws Exception {
        this();
        rootPatternXmlFile=rootXmlFilePath;
        File relDirectory = new File(rootPatternXmlFile.replace(".dmd","") + File.separator + "rel" + File.separator);
        File[] filesInRelDirectory  = relDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return true;
                }
                return false;
            }
        }
        );
        if (filesInRelDirectory == null  || filesInRelDirectory.length == 0 ) {
            throw new Exception("No directory found in directory " + relDirectory.getPath());
        }



        relDirectoryPath = StringUtils.replaceLast(filesInRelDirectory[0].toURI().getRawPath(),"/");

        File physDirectory = new File(relDirectoryPath + File.separator + "phys" + File.separator);
        File[] filesInPhysDirectory  = physDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if (pathname.isDirectory()) {
                    return true;
                }
                return false;
            }
        }
        );

        if (filesInPhysDirectory == null  || filesInPhysDirectory.length == 0 ) {
            throw new Exception("No directory found in directory " + physDirectory.getPath());
        }

        physDirectoryPath = StringUtils.replaceLast(filesInPhysDirectory[0].toURI().getRawPath(),"/");

        objectsLocalFilePath = relDirectoryPath + OBJECTLOCALNAME;
        objectsLocalPhysFilepath = physDirectoryPath + OBJECTLOCALNAME;

        schemaObjectFilePath = relDirectoryPath + ".xml";

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

    public String getSchemaObjectFilePath() {
        return schemaObjectFilePath.replace("\\","/");
    }

    public String getObjectsLocalPhysFilepath() {
        return objectsLocalPhysFilepath;
    }

    public void setObjectsLocalPhysFilepath(String objectsLocalPhysFilepath) {
        this.objectsLocalPhysFilepath = objectsLocalPhysFilepath;
    }

    public String getPhysDirectoryPath() {
        return physDirectoryPath;
    }

    public void setPhysDirectoryPath(String physDirectoryPath) {
        this.physDirectoryPath = physDirectoryPath;
    }

    public void setSchemaObjectFilePath(String schemaObjectFilePath) {
        this.schemaObjectFilePath = schemaObjectFilePath;
    }
}
