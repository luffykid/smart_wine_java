package com.changfa.frame.data.entity.common;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "dict")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class Dict implements Serializable {
    private Integer id;
    private String tableName;
    private String columnName;
    private String stsId;
    private String stsWords;
    private String descri;
    private Integer seq;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "table_name")
    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    @Basic
    @Column(name = "column_name")
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @Basic
    @Column(name = "sts_id")
    public String getStsId() {
        return stsId;
    }

    public void setStsId(String stsId) {
        this.stsId = stsId;
    }

    @Basic
    @Column(name = "sts_words")
    public String getStsWords() {
        return stsWords;
    }

    public void setStsWords(String stsWords) {
        this.stsWords = stsWords;
    }

    @Basic
    @Column(name = "descri")
    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    @Basic
    @Column(name = "seq")
    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Dict that = (Dict) o;

        if (id != that.id) return false;
        if (tableName != null ? !tableName.equals(that.tableName) : that.tableName != null) return false;
        if (columnName != null ? !columnName.equals(that.columnName) : that.columnName != null) return false;
        if (stsId != null ? !stsId.equals(that.stsId) : that.stsId != null) return false;
        if (stsWords != null ? !stsWords.equals(that.stsWords) : that.stsWords != null) return false;
        if (descri != null ? !descri.equals(that.descri) : that.descri != null) return false;
        if (seq != null ? !seq.equals(that.seq) : that.seq != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (tableName != null ? tableName.hashCode() : 0);
        result = 31 * result + (columnName != null ? columnName.hashCode() : 0);
        result = 31 * result + (stsId != null ? stsId.hashCode() : 0);
        result = 31 * result + (stsWords != null ? stsWords.hashCode() : 0);
        result = 31 * result + (descri != null ? descri.hashCode() : 0);
        result = 31 * result + (seq != null ? seq.hashCode() : 0);
        return result;
    }
}
