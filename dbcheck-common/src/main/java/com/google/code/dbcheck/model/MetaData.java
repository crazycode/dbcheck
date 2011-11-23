package com.google.code.dbcheck.model;

import java.util.List;

import com.google.code.dbcheck.diff.DiffValue;


public abstract class MetaData {
    private String name;

    /**
     * 用于在Diff时显示类型名字.
     * 
     * @return
     */
    public abstract String metaDataName();

    public abstract List<DiffValue> diff(MetaData otherMetaData);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShowName() {
        return getName();
    }
}
