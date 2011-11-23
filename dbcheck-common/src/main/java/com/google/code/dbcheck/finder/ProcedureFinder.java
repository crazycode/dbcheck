package com.google.code.dbcheck.finder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.code.dbcheck.model.Procedure;
import com.google.code.dbcheck.model.ProcedureColumn;

public class ProcedureFinder {
    private final Map<String, Procedure>       procedureMap;

    private final Map<String, ProcedureColumn> columnMap;

    public ProcedureFinder(List<Procedure> procedures) {
        this.procedureMap = new HashMap<String, Procedure>();
        this.columnMap = new HashMap<String, ProcedureColumn>();
        for (Procedure procedure : procedures) {
            procedureMap.put(procedure.getName(), procedure);
            for (ProcedureColumn column : procedure.getProcedureColumns()) {
                columnMap.put(procedure.getName() + "." + column.getName(), column);
            }
        }
    }

    public Procedure findProcedure(String procedureName) {
        assert procedureMap != null;
        return procedureMap.get(procedureName);
    }

    public ProcedureColumn findColumn(String procedureName, String columnName) {
        assert columnMap != null;
        return columnMap.get(procedureName + "." + columnName);
    }

}
