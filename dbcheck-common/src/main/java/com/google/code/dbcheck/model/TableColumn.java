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

/**
 * Column表述。
 * 注意因为开发人员可能随时加入列，这里不关心列的顺序。
 * 
 * @author <a href="mailto:crazycode@gmail.com">crazycode</a>
 */
@XStreamAlias("table-column")
public class TableColumn extends MetaData implements Comparable<TableColumn> {

    private String name;

    private String tableName;

    /**
     * 来自java.sql.Types的SQL类型.
     */
    private int    dataType;

    /**
     * COLUMN_SIZE.
     */
    private int    columnSize;

    private int    decimalDigits;

    private int    numPregRadix;

    private int    nullable;

    /**
     * COLUMN_DEF.
     */
    private String columnDefaultValue;

    /**
     * 对于 char 类型，该长度是列中的最大字节数
     */
    private int    charOctetLength;

    /**
     * ISO 规则用于确定列是否包括 null。
     * <ol>
     * <li>YES --- 如果参数可以包括 NULL</li>
     * <li>NO --- 如果参数不可以包括 NULL</li>
     * <li>空字符串 --- 如果不知道参数是否可以包括 null</li>
     * </ol>
     */
    private String isNullable;

    private short  sourceDataType;

    private String remarks;

    private String isAutoincrement;

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

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public int getColumnSize() {
        return columnSize;
    }

    public void setColumnSize(int columnSize) {
        this.columnSize = columnSize;
    }

    public int getDecimalDigits() {
        return decimalDigits;
    }

    public void setDecimalDigits(int decimalDigits) {
        this.decimalDigits = decimalDigits;
    }

    public int getNumPregRadix() {
        return numPregRadix;
    }

    public void setNumPregRadix(int numPregRadix) {
        this.numPregRadix = numPregRadix;
    }

    public int getNullable() {
        return nullable;
    }

    public void setNullable(int nullable) {
        this.nullable = nullable;
    }

    public String getColumnDefaultValue() {
        return columnDefaultValue;
    }

    public void setColumnDefaultValue(String columnDefaultValue) {
        this.columnDefaultValue = columnDefaultValue;
    }

    public int getCharOctetLength() {
        return charOctetLength;
    }

    public void setCharOctetLength(int charOctetLength) {
        this.charOctetLength = charOctetLength;
    }

    public String getIsNullable() {
        return isNullable;
    }

    public void setIsNullable(String isNullable) {
        this.isNullable = isNullable;
    }

    public short getSourceDataType() {
        return sourceDataType;
    }

    public void setSourceDataType(short sourceDataType) {
        this.sourceDataType = sourceDataType;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getIsAutoincrement() {
        return isAutoincrement;
    }

    public void setIsAutoincrement(String isAutoincrement) {
        this.isAutoincrement = isAutoincrement;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof TableColumn))
            return false;
        TableColumn castOther = (TableColumn) other;
        return new EqualsBuilder().append(name, castOther.name).append(tableName, castOther.tableName)
                .append(dataType, castOther.dataType).append(columnSize, castOther.columnSize)
                .append(decimalDigits, castOther.decimalDigits).append(numPregRadix, castOther.numPregRadix)
                .append(nullable, castOther.nullable).append(columnDefaultValue, castOther.columnDefaultValue)
                .append(charOctetLength, castOther.charOctetLength).append(isNullable, castOther.isNullable)
                .append(sourceDataType, castOther.sourceDataType).append(remarks, castOther.remarks)
                .append(isAutoincrement, castOther.isAutoincrement).isEquals();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(tableName).append(dataType).append(columnSize)
                .append(decimalDigits).append(numPregRadix).append(nullable).append(columnDefaultValue)
                .append(charOctetLength).append(isNullable).append(sourceDataType).append(remarks)
                .append(isAutoincrement).toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", name)
                .append("tableName", tableName).append("dataType", dataType).append("columnSize", columnSize)
                .append("decimalDigits", decimalDigits).append("numPregRadix", numPregRadix)
                .append("nullable", nullable).append("columnDefaultValue", columnDefaultValue)
                .append("charOctetLength", charOctetLength).append("isNullable", isNullable)
                .append("sourceDataType", sourceDataType).append("remarks", remarks)
                .append("isAutoincrement", isAutoincrement).toString();
    }

    @Override
    public String metaDataName() {
        return "Table Column";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final TableColumn other) {
        return new CompareToBuilder().append(name, other.name).append(tableName, other.tableName)
                .append(dataType, other.dataType).append(columnSize, other.columnSize)
                .append(decimalDigits, other.decimalDigits).append(numPregRadix, other.numPregRadix)
                .append(nullable, other.nullable).append(columnDefaultValue, other.columnDefaultValue)
                .append(charOctetLength, other.charOctetLength).append(isNullable, other.isNullable)
                .append(sourceDataType, other.sourceDataType).append(remarks, other.remarks)
                .append(isAutoincrement, other.isAutoincrement).toComparison();
    }

    @Override
    public List<DiffValue> diff(MetaData other) {
        TableColumn castOther = (TableColumn) other;

        return new DiffBuilder().append("name", name, castOther.getName())
                .append("tableName", tableName, castOther.getTableName())
                .append("dataType", dataType, castOther.getDataType())
                .append("columnSize", columnSize, castOther.getColumnSize())
                .append("decimalDigits", decimalDigits, castOther.getDecimalDigits())
                .append("numPregRadix", numPregRadix, castOther.getNumPregRadix())
                .append("nullable", nullable, castOther.getNullable())
                .append("columnDefaultValue", columnDefaultValue, castOther.getColumnDefaultValue())
                .append("charOctetLength", charOctetLength, castOther.getCharOctetLength())
                .append("isNullable", isNullable, castOther.getIsNullable())
                .append("sourceDataType", sourceDataType, castOther.getSourceDataType())
                .append("remarks", remarks, castOther.getRemarks())
                .append("isAutoincrement", isAutoincrement, castOther.getIsAutoincrement()).getDiffValues();
    }

    @Override
    public String getShowName() {
        return getTableName() + "." + getName();
    }
}
