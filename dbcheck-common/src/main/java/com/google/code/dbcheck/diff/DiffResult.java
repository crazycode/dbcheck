package com.google.code.dbcheck.diff;

import java.util.List;

import com.google.code.dbcheck.model.MetaData;

public class DiffResult {

    private MetaData metaObject;

    private List<DiffValue> diffValues;

    public DiffResult(MetaData metaObject) {
        this.metaObject = metaObject;
    }

    public MetaData getMetaObject() {
        return metaObject;
    }

    public void setMetaObject(MetaData metaObject) {
        this.metaObject = metaObject;
    }

    public List<DiffValue> getDiffValues() {
        return diffValues;
    }

    public void setDiffValues(List<DiffValue> diffValues) {
        this.diffValues = diffValues;
    }

    public String textShow() {
        StringBuilder sb = new StringBuilder();
        sb.append(metaObject.metaDataName())
                .append("[").append(metaObject.getName()).append("]")
                .append(" Found Differences:\n");
        for (DiffValue value : diffValues) {
            sb.append("    ").append(value.getName())
                    .append(": Database=[")
                    .append(value.getRunningValue())
                    .append("], XML=[")
                    .append(value.getStoredValue())
                    .append("]\n");
        }
        return sb.toString();
    }
}
