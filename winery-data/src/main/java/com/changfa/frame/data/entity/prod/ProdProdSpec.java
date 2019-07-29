package com.changfa.frame.data.entity.prod;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.voodoodyne.jackson.jsog.JSOGGenerator;

import javax.persistence.*;

/**
 * Created by Administrator on 2018/11/8.
 */
@Entity
@Table(name = "prod_prod_spec")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonIdentityInfo(generator = JSOGGenerator.class)
public class ProdProdSpec {
    private Integer id;
    private Integer prodId;
    private Integer prodSpecId;

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
    @Column(name = "prod_id")
    public Integer getProdId() {
        return prodId;
    }

    public void setProdId(Integer prodId) {
        this.prodId = prodId;
    }

    @Basic
    @Column(name = "prod_spec_id")
    public Integer getProdSpecId() {
        return prodSpecId;
    }

    public void setProdSpecId(Integer prodSpecId) {
        this.prodSpecId = prodSpecId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProdProdSpec that = (ProdProdSpec) o;

        if (id != that.id) return false;
        if (prodId != null ? !prodId.equals(that.prodId) : that.prodId != null) return false;
        if (prodSpecId != null ? !prodSpecId.equals(that.prodSpecId) : that.prodSpecId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (prodId != null ? prodId.hashCode() : 0);
        result = 31 * result + (prodSpecId != null ? prodSpecId.hashCode() : 0);
        return result;
    }
}
