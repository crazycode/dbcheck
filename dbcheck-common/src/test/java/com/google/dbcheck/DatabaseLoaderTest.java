package com.google.dbcheck;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.google.code.dbcheck.DatabaseLoader;
import com.google.code.dbcheck.model.Database;
import com.google.code.dbcheck.model.Table;
import com.google.dbcheck.data.OracleDatabase;

public class DatabaseLoaderTest {

    OracleDatabase oraDabatase = new OracleDatabase(new DatabaseLoader());

    @Test
    public void testOracleLoad() throws Exception {
        Database database = oraDabatase.loadDatabaseMeta();
        assertNotNull(database);
        assertTrue(database.getTables().size() > 0);

        for (Table t : database.getTables()) {
            assertTrue(t.getColumns().size() > 0);
            assertTrue(t.getIndexes().size() > 0);
        }

        assertTrue(database.getProcedures().size() == 0);

    }


}
