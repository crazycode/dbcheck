package com.google.code.dbcheck;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.dbcheck.model.Database;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XmlProcesser {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlProcesser.class);

    public Database load(String schemeName, InputStream inputStream) {
        XStream xs = new XStream(new DomDriver());
        xs.autodetectAnnotations(true);
        xs.alias("database", Database.class);
        Database database = null; // new Database();

        try {
            database = (Database) xs.fromXML(inputStream);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return database;
    }

    public void save(String schemeName, Database database, File file) throws Exception {
        if (!file.exists()) {
            File path = file.getParentFile();
            path.mkdirs();
        }

        XStream xs = new XStream();
        xs.autodetectAnnotations(true);

        FileOutputStream fos = null;
        // Write to a file in the file system
        try {
            fos = new FileOutputStream(file);
            xs.toXML(database, fos);
        } catch (Exception e) {
            if (LOGGER.isWarnEnabled()) {
                LOGGER.warn("Save Database Failure.", e);
            }
        }
    }
}
