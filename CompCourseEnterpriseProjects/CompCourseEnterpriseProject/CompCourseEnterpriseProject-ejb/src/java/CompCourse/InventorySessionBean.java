/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompCourse;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Edgars
 */
@Stateless
public class InventorySessionBean implements InventorySessionBeanLocal {

    @PersistenceContext(unitName = "CompCourseEnterpriseProject-ejbPU")
    private EntityManager em;

    @Override
    public Double calculateTotal() {
        List<Inventory> tmpList = em.createNamedQuery("Inventory.findAll").getResultList();
        double tmpTotal = 0;
        for (Inventory tmpInvItem : tmpList) {
            tmpTotal += tmpInvItem.getPrice() * tmpInvItem.getInstock();
        }
        return tmpTotal;
    }

    @Override
    public Double calculateReserved() {
         List<Inventory> tmpList = em.createNamedQuery("Inventory.findAll").getResultList();
        double tmpReserved = 0;
        for (Inventory tmpInvItem : tmpList) {
            tmpReserved += tmpInvItem.getPrice() * tmpInvItem.getReserved();
        }
        return tmpReserved;
    }

    @Override
    public Double calculateBalance() {
        return calculateTotal() - calculateReserved();
    }
    
}
