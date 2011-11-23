package com.google.code.dbcheck.finder;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.code.dbcheck.model.Table;
import com.google.code.dbcheck.model.TableColumn;
import com.google.code.dbcheck.model.TableIndex;

public class TableFinder {

    private final Map<String, Table>       tableMap;

    private final Map<String, TableColumn> columnMap;

    private final Map<String, TableIndex>  indexMap;

    /**
     * 不存在表的集合.
     * 初始时包括所有的，通过mark方法从这里删除.
     */
    private final Set<String>              notExistsTableNameSet;
    private final Set<String>              notExistsColumnNameSet;
    private final Set<String>              notExistsIndexNameSet;

    public TableFinder(List<Table> tables) {
        this.tableMap = new HashMap<String, Table>();
        this.columnMap = new HashMap<String, TableColumn>();
        this.indexMap = new HashMap<String, TableIndex>();
        this.notExistsTableNameSet = new HashSet<String>();
        this.notExistsColumnNameSet = new HashSet<String>();
        this.notExistsIndexNameSet = new HashSet<String>();
        for (Table table : tables) {
            tableMap.put(table.getName(), table);
            notExistsTableNameSet.add(table.getName());
            if (table.getColumns() != null) {
                for (TableColumn column : table.getColumns()) {
                    String key = table.getName() + "." + column.getName();
                    columnMap.put(key, column);
                    notExistsColumnNameSet.add(key);
                }
            }
            if (table.getIndexes() != null) {
                for (TableIndex index : table.getIndexes()) {
                    String key = table.getName() + "." + index.getName();
                    indexMap.put(key, index);
                    notExistsIndexNameSet.add(key);
                }
            }
        }
    }

    public Table findTable(String tableName) {
        assert tableMap != null;
        return tableMap.get(tableName);
    }

    public TableColumn findColumn(String tableName, String columnName) {
        return findColumn(tableName + "." + columnName);
    }

    public TableColumn findColumn(String fullColumnName) {
        assert columnMap != null;
        return columnMap.get(fullColumnName);
    }

    public TableIndex findIndex(String tableName, String indexName) {
        return findIndex(tableName + "." + indexName);
    }

    public TableIndex findIndex(String fullIndexName) {
        assert indexMap != null;
        return indexMap.get(fullIndexName);
    }

    public void markTableExists(String tableName) {
        this.notExistsTableNameSet.remove(tableName);
    }

    public void markColumnExists(String tableName, String columnName) {
        this.notExistsColumnNameSet.remove(tableName + "." + columnName);
    }

    public void markIndexExists(String tableName, String indexName) {
        this.notExistsIndexNameSet.remove(tableName + "." + indexName);
    }

    public Set<String> getNotExistsTableNameSet() {
        return notExistsTableNameSet;
    }

    public Set<String> getNotExistsColumnNameSet() {
        return notExistsColumnNameSet;
    }

    public Set<String> getNotExistsIndexNameSet() {
        return notExistsIndexNameSet;
    }

}
