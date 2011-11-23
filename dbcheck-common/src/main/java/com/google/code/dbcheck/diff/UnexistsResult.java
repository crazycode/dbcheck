package com.google.code.dbcheck.diff;

import com.google.code.dbcheck.model.MetaData;

public class UnexistsResult extends DiffResult {

    public static final int DBEXISTS  = 1;
    public static final int XMLEXISTS = 2;

    private boolean         dbExists  = false;

    private boolean         xmlExists = false;

    public UnexistsResult(MetaData metaObject, int b) {
        super(metaObject);
        if (DBEXISTS == b)
            dbExists = true;
        if (XMLEXISTS == b)
            xmlExists = true;
    }

    @Override
    public String textShow() {
        StringBuilder sb = new StringBuilder();
        sb.append(getMetaObject().metaDataName());
        sb.append(" [").append(getMetaObject().getShowName()).append("]");
        if (dbExists) {
            sb.append(" NOT FOUND in XML! Please run \"mvn dbcheck:dump\" to update XML file.");
        }
        if (xmlExists) {
            sb.append(" NOT FOUND in Database! Maybe You forget execute some DDL scripts!");
        }
        return sb.toString();
    }

    public boolean isDbExists() {
        return dbExists;
    }

    public void setDbExists(boolean dbExists) {
        this.dbExists = dbExists;
    }

    public boolean isXmlExists() {
        return xmlExists;
    }

    public void setXmlExists(boolean xmlExists) {
        this.xmlExists = xmlExists;
    }

}
