package com.google.code.dbcheck;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.dbcheck.finder.ProcedureFinder;
import com.google.code.dbcheck.finder.TableFinder;
import com.google.code.dbcheck.model.Database;
import com.google.code.dbcheck.model.Procedure;
import com.google.code.dbcheck.model.ProcedureColumn;
import com.google.code.dbcheck.model.Table;
import com.google.code.dbcheck.model.TableColumn;
import com.google.code.dbcheck.model.TableIndex;

public class DatabaseLoader {
    /**
     * SLF4j日志记录.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseLoader.class);

    public Database load(String schemeName, Connection conn) throws SQLException {
        DatabaseMetaData dmd = conn.getMetaData();

        Database db = new Database();
        db.setName(schemeName);
        db.setIsReadOnly(conn.isReadOnly());
        db.setTables(getTables(schemeName, dmd));
        db.setProcedures(getProcedures(schemeName, dmd));

        TableFinder tableFinder = new TableFinder(db.getTables());
        ProcedureFinder procedureFinder = new ProcedureFinder(db.getProcedures());
        // add index & columns
        processTableIndexes(schemeName, dmd, db.getTables());
        processTableColumns(schemeName, dmd, tableFinder);
        processProcedureColumns(schemeName, dmd, procedureFinder);

        return db;
    }

    private void processProcedureColumns(String schemeName, DatabaseMetaData dmd, ProcedureFinder procedureFinder)
            throws SQLException {
        ResultSet rs = dmd.getProcedureColumns(null, schemeName, null, null);
        ResultSetMetaData rsmd = rs.getMetaData();
        Set<String> colSet = new HashSet<String>();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            colSet.add(rsmd.getColumnName(i + 1));
        }
        while (rs.next()) {
            /*
             * TODO: 以后加上以下字段:
             * COLUMN_DEF String => 列的默认值，当该值在单引号内时被解释为一个字符串（可以为 null）
             * CHAR_OCTET_LENGTH int => 基于二进制和字符的列的最大长度。对于任何其他数据类型，返回值为 NULL
             * ORDINAL_POSITION int => 顺序位置，对于过程的输入和输出参数该位置从 1 开始。如果此行描述过程的返回值，则返回值 0。对于结果集列，它是从 1
             * IS_NULLABLE String => ISO 规则用于确定列是否可以包括 null。
             */
            ProcedureColumn c = new ProcedureColumn();
            c.setProcedureName(getStringFromResultSet(rs, colSet, "PROCEDURE_NAME"));
            c.setName(getStringFromResultSet(rs, colSet, "COLUMN_NAME"));
            c.setColumnType(getShortFromResultSet(rs, colSet, "COLUMN_TYPE"));
            c.setDataType(getIntFromResultSet(rs, colSet, "DATA_TYPE"));
            c.setTypeName(getStringFromResultSet(rs, colSet, "TYPE_NAME"));
            c.setPrecision(getIntFromResultSet(rs, colSet, "PRECISION"));
            c.setLength(getIntFromResultSet(rs, colSet, "LENGTH"));
            c.setScale(getShortFromResultSet(rs, colSet, "SCALE"));
            c.setRadix(getShortFromResultSet(rs, colSet, "RADIX"));
            c.setNullable(getShortFromResultSet(rs, colSet, "NULLABLE"));
            c.setRemarks(getStringFromResultSet(rs, colSet, "REMARKS"));

            Procedure t = procedureFinder.findProcedure(c.getProcedureName());
            if (t != null)
                t.addColumn(c);
        }
    }

    private void processTableIndexes(String schemeName, DatabaseMetaData dmd, List<Table> tables) throws SQLException {
        for (Table t : tables) {
            ResultSet rs = dmd.getIndexInfo(null, schemeName, t.getName(), false, false);
            ResultSetMetaData rsmd = rs.getMetaData();
            Set<String> colSet = new HashSet<String>();
            for (int i = 0; i < rsmd.getColumnCount(); i++) {
                colSet.add(rsmd.getColumnName(i + 1));
            }
            while (rs.next()) {
                TableIndex index = new TableIndex();
                index.setTableName(getStringFromResultSet(rs, colSet, "TABLE_NAME"));
                index.setName(getStringFromResultSet(rs, colSet, "INDEX_NAME"));
                index.setType(getShortFromResultSet(rs, colSet, "TYPE"));
                index.setColumnName(getStringFromResultSet(rs, colSet, "COLUMN_NAME"));
                index.setAscOrDesc(getStringFromResultSet(rs, colSet, "ASC_OR_DESC"));
                index.setFilterCondition(getStringFromResultSet(rs, colSet, "FILTER_CONDITION"));
                index.setNonUnique(getBooleanFromResultSet(rs, colSet, "NON_UNIQUE"));
                index.setIndexQualifier(getStringFromResultSet(rs, colSet, "INDEX_QUALIFIER"));

                t.addIndex(index);
            }
            rs.close();
        }
    }

    private void processTableColumns(String schemeName, DatabaseMetaData dmd, TableFinder tableFinder) throws SQLException {
        ResultSet rs = dmd.getColumns(null, schemeName, null, null);
        ResultSetMetaData rsmd = rs.getMetaData();
        Set<String> colSet = new HashSet<String>();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            colSet.add(rsmd.getColumnName(i + 1));
        }
        while (rs.next()) {
            TableColumn column = new TableColumn();
            column.setTableName(getStringFromResultSet(rs, colSet, "TABLE_NAME"));
            column.setName(getStringFromResultSet(rs, colSet, "COLUMN_NAME"));
            column.setDataType(getIntFromResultSet(rs, colSet, "DATA_TYPE"));
            column.setColumnSize(getIntFromResultSet(rs, colSet, "COLUMN_SIZE"));
            column.setDecimalDigits(getIntFromResultSet(rs, colSet, "DECIMAL_DIGITS"));
            column.setNumPregRadix(getIntFromResultSet(rs, colSet, "NUM_PREC_RADIX"));
            column.setNullable(getIntFromResultSet(rs, colSet, "NULLABLE"));
            column.setRemarks(getStringFromResultSet(rs, colSet, "REMARKS"));
            column.setColumnDefaultValue(getStringFromResultSet(rs, colSet, "COLUMN_DEF"));
            column.setCharOctetLength(getIntFromResultSet(rs, colSet, "CHAR_OCTET_LENGTH"));
            column.setIsNullable(getStringFromResultSet(rs, colSet, "IS_NULLABLE"));
            column.setDataType(getIntFromResultSet(rs, colSet, "DATA_TYPE"));
            column.setIsAutoincrement(getStringFromResultSet(rs, colSet, "IS_AUTOINCREMENT"));

            Table t = tableFinder.findTable(column.getTableName());
            if (t != null)
                t.addColumn(column);
        }
    }

    private List<Table> getTables(String schemeName, DatabaseMetaData dmd) throws SQLException {
        List<Table> tables = new ArrayList<Table>();
        ResultSet rs = dmd.getTables(null, schemeName, null, new String[] { "TABLE", "VIEW", "ALIAS", "SYNONYM" });
        ResultSetMetaData rsmd = rs.getMetaData();
        Set<String> colSet = new HashSet<String>();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            colSet.add(rsmd.getColumnName(i + 1));
        }
        while (rs.next()) {
            Table t = new Table();
            t.setName(getStringFromResultSet(rs, colSet, "TABLE_NAME"));
            t.setCatelog(getStringFromResultSet(rs, colSet, "TABLE_CAT"));
            t.setType(getStringFromResultSet(rs, colSet, "TABLE_TYPE"));
            t.setSelfReferencingColName(getStringFromResultSet(rs, colSet, "SELF_REFERENCING_COL_NAME"));
            t.setRefGeneration(getStringFromResultSet(rs, colSet, "REF_GENERATION"));

            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("t:" + t.toString());
            }

            tables.add(t);
        }
        rs.close();
        return tables;
    }

    private List<Procedure> getProcedures(String schemeName, DatabaseMetaData dmd) throws SQLException {
        List<Procedure> procedures = new ArrayList<Procedure>();
        ResultSet rs = dmd.getProcedures(null, schemeName, null);
        ResultSetMetaData rsmd = rs.getMetaData();
        Set<String> colSet = new HashSet<String>();
        for (int i = 0; i < rsmd.getColumnCount(); i++) {
            colSet.add(rsmd.getColumnName(i + 1));
        }
        while (rs.next()) {
            Procedure p = new Procedure();
            p.setName(getStringFromResultSet(rs, colSet, "PROCEDURE_NAME"));
            p.setRemarks(getStringFromResultSet(rs, colSet, "REMARKS"));
            p.setProcedureType(getShortFromResultSet(rs, colSet, "PROCEDURE_TYPE"));
            procedures.add(p);
        }
        rs.close();
        return procedures;
    }

    private String getStringFromResultSet(ResultSet rs, Set<String> colSet, String name) throws SQLException {
        if (colSet.contains(name)) {
            return rs.getString(name);
        }
        return null;
    }

    private int getIntFromResultSet(ResultSet rs, Set<String> colSet, String name) throws SQLException {
        if (colSet.contains(name)) {
            return rs.getInt(name);
        }
        return 0;
    }

    private short getShortFromResultSet(ResultSet rs, Set<String> colSet, String name) throws SQLException {
        if (colSet.contains(name)) {
            return rs.getShort(name);
        }
        return 0;
    }

    private boolean getBooleanFromResultSet(ResultSet rs, Set<String> colSet, String name) throws SQLException {
        if (colSet.contains(name)) {
            return rs.getBoolean(name);
        }
        return false;
    }
}
