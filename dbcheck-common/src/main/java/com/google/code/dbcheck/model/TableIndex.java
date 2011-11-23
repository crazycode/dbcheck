package com.google.code.dbcheck.model;

import java.util.List;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.google.code.dbcheck.diff.DiffBuilder;
import com.google.code.dbcheck.diff.DiffValue;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("table-index")
public class TableIndex extends MetaData implements Comparable<TableIndex> {

    private String  name;

    private String  tableName;

    /**
     * NON_UNIQUE
     */
    private Boolean nonUnique;

    /**
     * TYPE.
     */
    private short   type;

    /**
     * INDEX_QUALIFIER
     */
    private String  indexQualifier;

    /**
     * ORDINAL_POSITION
     */
    private short   ordinalPosition;

    private String  columnName;

    private String  ascOrDesc;

    /**
     * FILTER_CONDITION.
     */
    private String  filterCondition;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Boolean getNonUnique() {
        return nonUnique;
    }

    public void setNonUnique(Boolean nonUnique) {
        this.nonUnique = nonUnique;
    }

    public short getType() {
        return type;
    }

    public void setType(short type) {
        this.type = type;
    }

    public String getIndexQualifier() {
        return indexQualifier;
    }

    public void setIndexQualifier(String indexQualifier) {
        this.indexQualifier = indexQualifier;
    }

    public short getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(short ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getAscOrDesc() {
        return ascOrDesc;
    }

    public void setAscOrDesc(String ascOrDesc) {
        this.ascOrDesc = ascOrDesc;
    }

    public String getFilterCondition() {
        return filterCondition;
    }

    public void setFilterCondition(String filterCondition) {
        this.filterCondition = filterCondition;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof TableIndex))
            return false;
        TableIndex castOther = (TableIndex) other;
        return new EqualsBuilder().append(name, castOther.name).append(tableName, castOther.tableName)
                .append(nonUnique, castOther.nonUnique).append(type, castOther.type)
                .append(indexQualifier, castOther.indexQualifier).append(ordinalPosition, castOther.ordinalPosition)
                .append(columnName, castOther.columnName).append(ascOrDesc, castOther.ascOrDesc)
                .append(filterCondition, castOther.filterCondition).isEquals();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(tableName).append(nonUnique).append(type)
                .append(indexQualifier).append(ordinalPosition).append(columnName).append(ascOrDesc)
                .append(filterCondition).toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", name)
                .append("tableName", tableName).append("nonUnique", nonUnique).append("type", type)
                .append("indexQualifier", indexQualifier).append("ordinalPosition", ordinalPosition)
                .append("columnName", columnName).append("ascOrDesc", ascOrDesc)
                .append("filterCondition", filterCondition).toString();
    }

    @Override
    public String metaDataName() {
        return "Table Index";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final TableIndex other) {
        return new CompareToBuilder().append(name, other.name).append(tableName, other.tableName)
                .append(nonUnique, other.nonUnique).append(type, other.type)
                .append(indexQualifier, other.indexQualifier).append(ordinalPosition, other.ordinalPosition)
                .append(columnName, other.columnName).append(ascOrDesc, other.ascOrDesc)
                .append(filterCondition, other.filterCondition).toComparison();
    }

    @Override
    public List<DiffValue> diff(MetaData other) {
        TableIndex castOther = (TableIndex) other;

        return new DiffBuilder().append("name", name, castOther.getName())
                .append("tableName", tableName, castOther.getTableName())
                .append("nonUnique", nonUnique, castOther.getNonUnique())
                .append("type", type, castOther.getType())
                .append("indexQualifier", indexQualifier, castOther.getIndexQualifier())
                .append("ordinalPosition", ordinalPosition, castOther.getOrdinalPosition())
                .append("columnName", columnName, castOther.getColumnName())
                .append("ascOrDesc", ascOrDesc, castOther.getAscOrDesc())
                .append("filterCondition", filterCondition, castOther.getFilterCondition()).getDiffValues();
    }

    @Override
    public String getShowName() {
        return getTableName() + "." + getName();
    }
}
