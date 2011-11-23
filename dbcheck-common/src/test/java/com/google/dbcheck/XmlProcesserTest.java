package com.google.dbcheck;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.io.InputStream;
import org.junit.Test;

import com.google.code.dbcheck.DatabaseLoader;
import com.google.code.dbcheck.XmlProcesser;
import com.google.code.dbcheck.model.Database;
import com.google.code.dbcheck.model.Table;
import com.google.dbcheck.data.OracleDatabase;

public class XmlProcesserTest {

    OracleDatabase              oraDabatase = new OracleDatabase(new DatabaseLoader());

    XmlProcesser processer   = new XmlProcesser();

    @Test
    public void testLoad() throws Exception {
        InputStream is = this.getClass().getResourceAsStream("/META-INF/dbcheck/default_cmdb.xml");
        Database database = processer.load("CMDBAS", is);
        assertNotNull(database);
        assertTrue(database.getTables().size() > 0);

        for (Table t : database.getTables()) {
            assertTrue(t.getColumns().size() > 0);
            assertTrue(t.getIndexes().size() > 0);
        }

        assertTrue(database.getProcedures().size() == 0);
    }

    @Test
    public void testSave() throws Exception {
        Database database = oraDabatase.loadDatabaseMeta();
        assertNotNull(database);

        File file = new File("/tmp/cmdb.xml");
        if (file.exists()) {
            file.delete();
        }
        assertFalse(file.exists());

        processer.save("CMDBAS", database, file);

        assertTrue(file.exists());
    }

}
