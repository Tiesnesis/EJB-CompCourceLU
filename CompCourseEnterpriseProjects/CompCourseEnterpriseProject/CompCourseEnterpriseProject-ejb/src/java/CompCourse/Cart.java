/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompCourse;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Edgars
 */
@Entity
@Table(name = "CART")
@NamedQueries({
    @NamedQuery(name = "Cart.findAll", query = "SELECT c FROM Cart c"),
    @NamedQuery(name = "Cart.findByUserID", query = "SELECT c FROM Cart c WHERE c.userID = :fuzzyID"),
    @NamedQuery(name = "Cart.findByProductID", query = "SELECT c FROM Cart c WHERE c.invID = :fuzzyID")
})

public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @Column(name = "USER_ID")
    private Integer userID;
    @Column(name = "INV_ID")
    private Integer invID;
    @Column(name = "COUNT")
    private Integer count;

    public Cart() {
    }

    public Cart(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUser(Integer userID) {
        this.userID = userID;
    }

    public Integer getInvID() {
        return invID;
    }

    public void setInvID(Integer count) {
        this.invID = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CompCourse.Cart[ id=" + id + " ]";
    }
    
}
