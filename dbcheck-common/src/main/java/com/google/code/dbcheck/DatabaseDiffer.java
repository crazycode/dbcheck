package com.google.code.dbcheck;

import java.util.ArrayList;
import java.util.List;

import com.google.code.dbcheck.diff.DiffResult;
import com.google.code.dbcheck.diff.UnexistsResult;
import com.google.code.dbcheck.finder.TableFinder;
import com.google.code.dbcheck.model.Database;
import com.google.code.dbcheck.model.MetaData;
import com.google.code.dbcheck.model.Table;
import com.google.code.dbcheck.model.TableColumn;
import com.google.code.dbcheck.model.TableIndex;

public class DatabaseDiffer {

    public List<DiffResult> compare(Database runningDatabase, Database storedDatabase) {
        List<DiffResult> results = new ArrayList<DiffResult>();
        TableFinder storedTableFinder = new TableFinder(storedDatabase.getTables());
        // TODO: 暂不处理存储过程
        // ProcedureFinder storedProcedureFinder = new
        // ProcedureFinder(storedDatabase.getProcedures());

        // 数据库比较
        checkDiffValues(results, runningDatabase, storedDatabase);

        // 比较表
        for (Table table : runningDatabase.getTables()) {
            Table storedTable = storedTableFinder.findTable(table.getName());
            if (storedTable != null) {
                storedTableFinder.markTableExists(table.getName());
                checkDiffValues(results, table, storedTable);

                // 比较字段
                for (TableColumn column : table.getColumns()) {
                    TableColumn storedColumn = storedTableFinder.findColumn(table.getName(), column.getName());
                    if (storedColumn != null) {
                        storedTableFinder.markColumnExists(table.getName(), column.getName());
                        checkDiffValues(results, column, storedColumn);
                    } else {
                        UnexistsResult r = new UnexistsResult(column, UnexistsResult.DBEXISTS);
                        results.add(r);
                    }
                }

                // 比较索引
                for (TableIndex index : table.getIndexes()) {
                    TableIndex storedIndex = storedTableFinder.findIndex(table.getName(), index.getName());
                    if (storedIndex != null) {
                        storedTableFinder.markIndexExists(table.getName(), index.getName());
                        checkDiffValues(results, index, storedIndex);
                    } else {
                        UnexistsResult r = new UnexistsResult(index, UnexistsResult.DBEXISTS);
                        results.add(r);
                    }
                }

            } else {
                UnexistsResult r = new UnexistsResult(table, UnexistsResult.DBEXISTS);
                results.add(r);
            }

        }

        // 处理XML中存在的表
        for (String tableName : storedTableFinder.getNotExistsTableNameSet()) {
            Table storedTable = storedTableFinder.findTable(tableName);
            UnexistsResult r = new UnexistsResult(storedTable, UnexistsResult.XMLEXISTS);
            results.add(r);
        }
        for (String fullColumnName : storedTableFinder.getNotExistsColumnNameSet()) {
            TableColumn storedTableColumn = storedTableFinder.findColumn(fullColumnName);
            UnexistsResult r = new UnexistsResult(storedTableColumn, UnexistsResult.XMLEXISTS);
            results.add(r);
        }
        for (String fullIndexName : storedTableFinder.getNotExistsIndexNameSet()) {
            TableIndex storedTableIndex = storedTableFinder.findIndex(fullIndexName);
            UnexistsResult r = new UnexistsResult(storedTableIndex, UnexistsResult.XMLEXISTS);
            results.add(r);
        }

        return results;
    }

    private void checkDiffValues(List<DiffResult> results, MetaData dbMetaData, MetaData xmlMetaData) {
        if (dbMetaData != null) {
            if (dbMetaData.equals(xmlMetaData)) {
                return;
            }
            DiffResult r = new DiffResult(dbMetaData);
            r.setDiffValues(dbMetaData.diff(xmlMetaData));
            results.add(r);
        } else {
            throw new RuntimeException("Db MetaData Can't be NULL!");
        }
    }

}
