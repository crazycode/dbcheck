package com.google.dbcheck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.InputStream;
import java.util.List;
import org.junit.Test;

import com.google.code.dbcheck.DatabaseDiffer;
import com.google.code.dbcheck.XmlProcesser;
import com.google.code.dbcheck.diff.DiffResult;
import com.google.code.dbcheck.diff.UnexistsResult;
import com.google.code.dbcheck.model.Database;

public class DatabaseDifferTest {


    private final XmlProcesser processer = new XmlProcesser();

    private final DatabaseDiffer differ    = new DatabaseDiffer();

    @Test
    public void testCompareSame() {
        Database xmldb = loadFrom("/META-INF/dbcheck/default_cmdb.xml");
        Database same = loadFrom("/META-INF/dbcheck/same_cmdb.xml");

        List<DiffResult> results = differ.compare(same, xmldb);

        show(results);
        assertTrue(results.size() == 0);
    }


    @Test
    public void testCompareMoreTable() {
        Database xmldb = loadFrom("/META-INF/dbcheck/default_cmdb.xml");
        Database moreTableDB = loadFrom("/META-INF/dbcheck/moretable_cmdb.xml");

        assertEquals(xmldb.getTables().size(), moreTableDB.getTables().size() - 1);

        List<DiffResult> results = differ.compare(moreTableDB, xmldb);

        show(results);
        assertEquals(1, results.size());
        UnexistsResult r = (UnexistsResult) results.get(0);
        assertTrue(r.isDbExists()); // 运行数据库比XML中多一个表.
        assertEquals("AGENT_2", r.getMetaObject().getName());
    }

    @Test
    public void testCompareLessTable() {
        Database xmldb = loadFrom("/META-INF/dbcheck/default_cmdb.xml");
        Database lessTableDB = loadFrom("/META-INF/dbcheck/lesstable_cmdb.xml");

        assertEquals(xmldb.getTables().size(), lessTableDB.getTables().size() + 1);

        List<DiffResult> results = differ.compare(lessTableDB, xmldb);

        show(results);
        assertEquals(19, results.size());
        UnexistsResult r = (UnexistsResult) results.get(0);
        assertTrue(r.isXmlExists()); // 运行数据库比XML中少一个表.
        assertEquals("AGENT", r.getMetaObject().getName());
    }

    @Test
    public void testCompareTableProperties() {
        Database xmldb = loadFrom("/META-INF/dbcheck/default_cmdb.xml");
        Database runingdb = loadFrom("/META-INF/dbcheck/difftable_cmdb.xml");

        assertEquals(xmldb.getTables().size(), runingdb.getTables().size());

        List<DiffResult> results = differ.compare(runingdb, xmldb);

        show(results);
        assertEquals(1, results.size());
        DiffResult r = results.get(0);
        assertEquals(1, r.getDiffValues().size()); // 运行数据库比XML中多一个字段.
        assertEquals("AGENT", r.getMetaObject().getName());
    }

    @Test
    public void testCompareMoreColumn() {
        Database xmldb = loadFrom("/META-INF/dbcheck/default_cmdb.xml");
        Database moreTableDB = loadFrom("/META-INF/dbcheck/morecolumn_cmdb.xml");

        assertEquals(xmldb.getTables().size(), moreTableDB.getTables().size());

        List<DiffResult> results = differ.compare(moreTableDB, xmldb);

        show(results);
        assertEquals(1, results.size());
        UnexistsResult r = (UnexistsResult) results.get(0);
        assertTrue(r.isDbExists()); // 运行数据库比XML中多一个字段.
        assertEquals("AGENT.PLUSCOLUMN", r.getMetaObject().getShowName());
        assertEquals("PLUSCOLUMN", r.getMetaObject().getName());
    }

    @Test
    public void testCompareLessColumn() {
        Database xmldb = loadFrom("/META-INF/dbcheck/default_cmdb.xml");
        Database lessTableDB = loadFrom("/META-INF/dbcheck/lesscolumn_cmdb.xml");

        assertEquals(xmldb.getTables().size(), lessTableDB.getTables().size());

        List<DiffResult> results = differ.compare(lessTableDB, xmldb);

        show(results);
        assertEquals(1, results.size());
        UnexistsResult r = (UnexistsResult) results.get(0);
        assertTrue(r.isXmlExists()); // 运行数据库比XML中少一个字段.
        assertEquals("CREATED_AT", r.getMetaObject().getName());
        assertEquals("BASIC_CONFIG.CREATED_AT", r.getMetaObject().getShowName());
    }

    @Test
    public void testCompareMoreIndex() {
        Database xmldb = loadFrom("/META-INF/dbcheck/default_cmdb.xml");
        Database moreTableDB = loadFrom("/META-INF/dbcheck/moreindex_cmdb.xml");

        assertEquals(xmldb.getTables().size(), moreTableDB.getTables().size());

        List<DiffResult> results = differ.compare(moreTableDB, xmldb);

        show(results);
        assertEquals(1, results.size());
        UnexistsResult r = (UnexistsResult) results.get(0);
        assertTrue(r.isDbExists()); // 运行数据库比XML中多一个索引.
        assertEquals("VSERVER_POLICY.IDX_UPDATED_BY", r.getMetaObject().getShowName());
        assertEquals("IDX_UPDATED_BY", r.getMetaObject().getName());
    }

    @Test
    public void testCompareLessIndex() {
        Database xmldb = loadFrom("/META-INF/dbcheck/default_cmdb.xml");
        Database lessTableDB = loadFrom("/META-INF/dbcheck/lessindex_cmdb.xml");

        assertEquals(xmldb.getTables().size(), lessTableDB.getTables().size());

        List<DiffResult> results = differ.compare(lessTableDB, xmldb);

        show(results);
        assertEquals(1, results.size());
        UnexistsResult r = (UnexistsResult) results.get(0);
        assertTrue(r.isXmlExists()); // 运行数据库比XML中少一个索引.
        assertEquals("PK_WATCH_INFO", r.getMetaObject().getName());
    }

    private void show(List<DiffResult> results) {
        for (DiffResult result : results) {
            System.out.println(result.textShow());
        }
    }

    private Database loadFrom(String path) {
        InputStream is = this.getClass().getResourceAsStream(path);
        return processer.load("CMDBAS", is);
    }
}
