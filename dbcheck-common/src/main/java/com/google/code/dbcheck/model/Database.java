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
 * 数据库.
 * 映射单个数据库Scheme，或DataSource。
 * 
 * @author <a href="mailto:crazycode@gmail.com">crazycode</a>
 */
@XStreamAlias("database")
public class Database extends MetaData implements Comparable<Database> {

    /**
     * 数据库名称.
     * 指Scheme名称，不同环境可以不同。
     */
    private String               name;

    /**
     * 数据库连接是否处于只读模式.
     * 防止配置错误.
     */
    private Boolean              isReadOnly;

    private List<Table>          tables;

    private List<Procedure> procedures;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsReadOnly() {
        return isReadOnly;
    }

    public void setIsReadOnly(Boolean isReadOnly) {
        this.isReadOnly = isReadOnly;
    }

    public List<Table> getTables() {
        return tables;
    }

    public void setTables(List<Table> tables) {
        this.tables = tables;
    }

    public List<Procedure> getProcedures() {
        return procedures;
    }

    public void setProcedures(List<Procedure> procedures) {
        this.procedures = procedures;
    }

    @Override
    public String metaDataName() {
        return "Database";
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object other) {
        if (!(other instanceof Database))
            return false;
        Database castOther = (Database) other;
        return new EqualsBuilder().append(name, castOther.name).append(isReadOnly, castOther.isReadOnly).isEquals();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(name).append(isReadOnly).toHashCode();
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("name", name)
                .append("isReadOnly", isReadOnly).append("tables", tables).append("procedures", procedures).toString();
    }

    @Override
    public List<DiffValue> diff(MetaData other) {
        Database castOther = (Database) other;
        return new DiffBuilder().append("name", name, castOther.getName())
                .append("isReadOnly", isReadOnly, castOther.getIsReadOnly()).getDiffValues();

    }

    /*
     * (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    public int compareTo(final Database other) {
        return new CompareToBuilder().append(name, other.name).append(isReadOnly, other.isReadOnly).toComparison();
    }

}
