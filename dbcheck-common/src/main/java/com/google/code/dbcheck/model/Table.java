package com.google.code.dbcheck.model;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.code.dbcheck.diff.DiffBuilder;
import com.google.code.dbcheck.diff.DiffValue;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("table")
public class Table extends MetaData implements Comparable<Table> {

    /**
     * 数据库表名称，英文，大小写敏感？
     */
    private String name;

    /**
     * 对应TABLE_TYPE.
     */
    private String type;

    /**
     * TABLE_CAT.
     */
    private String catelog;

    /**
     * SELF_REFERENCING_COL_NAME
     */
    private String selfReferencingColName;

    private List<TableColumn> columns;

    private List<TableIndex>  indexes;

    /**
     * REF_GENERATION
     */
    private String refGeneration;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCatelog() {
        return catelog;
    }

    public void setCatelog(String catelog) {
        this.catelog = catelog;
    }

    public String getSelfReferencingColName() {
        return selfReferencingColName;
    }

    public void setSelfReferencingColName(String selfReferencingColName) {
        this.selfReferencingColName = selfReferencingColName;
    }

    public String getRefGeneration() {
        return refGeneration;
    }

    public void setRefGeneration(String refGeneration) {
        this.refGeneration = refGeneration;
    }

    public List<TableColumn> getColumns() {
        return columns;
    }

    public void setColumns(List<TableColumn> columns) {
        this.columns = columns;
    }

    public List<TableIndex> getIndexes() {
        return indexes;
    }

    public void setIndexes(List<TableIndex> indexes) {
        this.indexes = indexes;
    }


    public void addColumn(TableColumn column) {
        if (this.getColumns() == null) {
            this.setColumns(new ArrayList<TableColumn>());
        }
        this.getColumns().add(column);
    }

    public void addIndex(TableIndex index) {
        if (this.getIndexes() == null) {
            this.setIndexes(new ArrayList<TableIndex>());
        }
        this.getIndexes().add(index);
    }

    // ================

    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Table))
            return false;
        Table castOther = (Table) other;
        return new EqualsBuilder().append(name, castOther.name)
                .append(type, castOther.type).append(catelog, castOther.catelog)
                .append(selfReferencingColName, castOther.selfReferencingColName)
                .append(refGeneration, castOther.refGeneration).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(type).append(catelog)
                .append(selfReferencingColName).append(refGeneration).toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", name)
                .append("type", type).append("catelog", catelog)
                .append("selfReferencingColName", selfReferencingColName).append("columns", columns)
                .append("indexes", indexes).append("refGeneration", refGeneration).toString();
    }

    @Override
    public String metaDataName() {
        return "Table";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final Table other) {
        return new CompareToBuilder().append(name, other.name).append(type, other.type).append(catelog, other.catelog)
                .append(selfReferencingColName, other.selfReferencingColName)
                .append(refGeneration, other.refGeneration).toComparison();
    }

    @Override
    public List<DiffValue> diff(MetaData other) {

        Table castOther = (Table) other;

        return new DiffBuilder().append("name", name, castOther.getName())
                .append("type", type, castOther.getType())
                .append("catelog", catelog, castOther.getCatelog())
                .append("selfReferencingColName", selfReferencingColName, castOther.getSelfReferencingColName())
                .append("refGeneration", refGeneration, castOther.getRefGeneration()).getDiffValues();
    }

}
