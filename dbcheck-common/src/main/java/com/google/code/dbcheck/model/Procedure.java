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

@XStreamAlias("procedure")
public class Procedure extends MetaData implements Comparable<Procedure> {

    private String name;

    private String remarks;

    /**
     * 过程的种类：
     * <ol>
     * <li>procedureResultUnknown - 不能确定是否将返回一个返回值。</li>
     * <li>procedureNoResult - 不返回一个返回值</li>
     * <li>procedureReturnsResult - 返回一个返回值</li>
     * </ol>
     */
    private short  procedureType;

    private List<ProcedureColumn> procedureColumns;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public short getProcedureType() {
        return procedureType;
    }

    public void setProcedureType(short procedureType) {
        this.procedureType = procedureType;
    }

    public List<ProcedureColumn> getProcedureColumns() {
        return procedureColumns;
    }

    public void setProcedureColumns(List<ProcedureColumn> procedureColumns) {
        this.procedureColumns = procedureColumns;
    }

    public void addColumn(ProcedureColumn column) {
        if (this.getProcedureColumns() == null) {
            this.setProcedureColumns(new ArrayList<ProcedureColumn>());
        }
        this.getProcedureColumns().add(column);
    }

    @Override
    public String metaDataName() {
        return "Procedure";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Procedure))
            return false;
        Procedure castOther = (Procedure) other;
        return new EqualsBuilder().append(name, castOther.name).append(remarks, castOther.remarks)
                .append(procedureType, castOther.procedureType)
                .isEquals();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(remarks).append(procedureType)
                .toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", name)
                .append("remarks", remarks).append("procedureType", procedureType)
                .append("procedureColumns", procedureColumns).toString();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final Procedure other) {
        return new CompareToBuilder().append(name, other.name).append(remarks, other.remarks)
                .append(procedureType, other.procedureType).toComparison();
    }

    @Override
    public List<DiffValue> diff(MetaData other) {
        Procedure castOther = (Procedure) other;
        return new DiffBuilder().append("name", name, castOther.getName())
                .append("remarks", remarks, castOther.getRemarks())
                .append("procedureType", procedureType, castOther.getProcedureType()).getDiffValues();
    }

}
