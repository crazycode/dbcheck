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

@XStreamAlias("procedure-column")
public class ProcedureColumn extends MetaData implements Comparable<ProcedureColumn> {

    private String name;

    private String procedureName;

    /**
     * COLUMN_TYPE 列/参数的种类：
     * <ol>
     * <li>procedureColumnUnknown - 没人知道</li>
     * <li>procedureColumnIn - IN 参数</li>
     * <li>procedureColumnInOut - INOUT 参数</li>
     * <li>procedureColumnOut - OUT 参数</li>
     * <li>procedureColumnReturn - 过程返回值</li>
     * <li>procedureColumnResult - ResultSet 中的结果列</li>
     * <li>DATA_TYPE int => 来自 java.sql.Types 的 SQL 类型</li>
     * </ol>
     */
    private short columnType;

    private int dataType;

    private String typeName;

    private int    precision;

    private int    length;

    private short  scale;

    private short  radix;

    private short  nullable;

    private String remarks;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getProcedureName() {
        return procedureName;
    }

    public void setProcedureName(String procedureName) {
        this.procedureName = procedureName;
    }

    public short getColumnType() {
        return columnType;
    }

    public void setColumnType(short columnType) {
        this.columnType = columnType;
    }

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public int getPrecision() {
        return precision;
    }

    public void setPrecision(int precision) {
        this.precision = precision;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public short getScale() {
        return scale;
    }

    public void setScale(short scale) {
        this.scale = scale;
    }

    public short getRadix() {
        return radix;
    }

    public void setRadix(short radix) {
        this.radix = radix;
    }

    public short getNullable() {
        return nullable;
    }

    public void setNullable(short nullable) {
        this.nullable = nullable;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Override
    public String metaDataName() {
        return "Procedure Column";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof ProcedureColumn))
            return false;
        ProcedureColumn castOther = (ProcedureColumn) other;
        return new EqualsBuilder().append(name, castOther.name).append(procedureName, castOther.procedureName)
                .append(columnType, castOther.columnType).append(dataType, castOther.dataType)
                .append(typeName, castOther.typeName).append(precision, castOther.precision)
                .append(length, castOther.length).append(scale, castOther.scale).append(radix, castOther.radix)
                .append(nullable, castOther.nullable).append(remarks, castOther.remarks).isEquals();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(procedureName).append(columnType).append(dataType)
                .append(typeName).append(precision).append(length).append(scale).append(radix).append(nullable)
                .append(remarks).toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", name)
                .append("procedureName", procedureName).append("columnType", columnType).append("dataType", dataType)
                .append("typeName", typeName).append("precision", precision).append("length", length)
                .append("scale", scale).append("radix", radix).append("nullable", nullable).append("remarks", remarks)
                .toString();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final ProcedureColumn other) {
        return new CompareToBuilder().append(name, other.name).append(procedureName, other.procedureName)
                .append(columnType, other.columnType).append(dataType, other.dataType).append(typeName, other.typeName)
                .append(precision, other.precision).append(length, other.length).append(scale, other.scale)
                .append(radix, other.radix).append(nullable, other.nullable).append(remarks, other.remarks)
                .toComparison();
    }

    @Override
    public List<DiffValue> diff(MetaData other) {
        ProcedureColumn castOther = (ProcedureColumn) other;

        return new DiffBuilder().append("name", name, castOther.getName())
                .append("procedureName", procedureName, castOther.getProcedureName())
                .append("columnType", columnType, castOther.getColumnType())
                .append("dataType", dataType, castOther.getDataType())
                .append("typeName", typeName, castOther.getTypeName())
                .append("precision", precision, castOther.getPrecision())
                .append("length", length, castOther.getLength())
                .append("scale", scale, castOther.getScale())
                .append("radix", radix, castOther.getRadix())
                .append("nullable", nullable, castOther.getNullable())
                .append("remarks", remarks, castOther.getRemarks())
                .getDiffValues();
    }

    @Override
    public String getShowName() {
        return getProcedureName() + "." + getName();
    }
}
