/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompCourse;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.StatefulTimeout;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Edgars
 */
@Stateful
@StatefulTimeout(unit = TimeUnit.MINUTES, value = 30)
public class UserSessionBean implements UserSessionBeanRemote {

    @PersistenceContext(unitName = "CompCourseEnterpriseProject-ejbPU")
    private EntityManager em;

    @EJB
    private InventorySessionBeanLocal invSess;

    private User currUser;
    
    @Override
    public boolean logIn(String fName, String lName) {
        System.out.println("logIn invoked");
        
	if (lName == null || lName.equals("")) return false;
        
        Query tmpFindAllQry =  em.createNamedQuery("User.findAll");
        List<User> tmpList = tmpFindAllQry.getResultList();
        for (User tmpUser : tmpList) {
            if (tmpUser.getLastName().equals(lName)) {
        	currUser = tmpUser;
		return true;
            }
        }
	
        currUser = new User(tmpList.size() + 1);
        currUser.setFirstName(fName);
        currUser.setLastName(lName);
        em.persist(currUser);
        return true;
    }
    
    @Override
    public boolean addPart(Integer id, String descr, Double price, Integer count) {
        if (id < 1) return false;
        
        // find by primary key
        Inventory foundInvItem = em.find(Inventory.class, id);

        if (foundInvItem == null) { //not found - create new one
            Inventory tmpInventoryItem = new Inventory(id);
            tmpInventoryItem.setDescription(descr);
            tmpInventoryItem.setPrice(price);
            tmpInventoryItem.setInstock(count);
            tmpInventoryItem.setReserved(0);
            em.persist(tmpInventoryItem);
        }
        else { // found - add quantity in stock
            foundInvItem.setInstock(foundInvItem.getInstock() + count);
            em.persist(foundInvItem);
        }

        return true;
    }
    
    @Override
    public boolean addUser(Integer id, String name, String lastName) {
        if (id < 1) return false;
        User tmpInventoryItem = new User(id);
        tmpInventoryItem.setFirstName(name);
        tmpInventoryItem.setLastName(lastName);
        em.persist(tmpInventoryItem);
        return true;
    }
    
    @Override
    public boolean addItemToCart(Integer id, Integer userID, Integer invID, Integer count) {
        if (id < 1) return false;
        Cart tmpInventoryItem = new Cart(id);
        tmpInventoryItem.setUser(userID);
        tmpInventoryItem.setInvID(invID);
        tmpInventoryItem.setCount(count);
        em.persist(tmpInventoryItem);
        return true;
    }
    
    @Override
    public boolean reservePart(Integer id, Integer count) {
        if (id < 1) return false;
        
        // find by primary key
        Inventory foundInvItem = em.find(Inventory.class, id);
        if (foundInvItem == null) { //not found - return false
            return false;
        }
        else { // found - add quantity in stock
            foundInvItem.setReserved(foundInvItem.getReserved() + count);
            em.persist(foundInvItem);
        }

        return true;
    }
    
    
    @Override
    public List<Inventory> searchPartByDescription(String descr) {
        if (descr == null) return null;
        
        Query tmpFindFuzzySearch = em.createNamedQuery("Inventory.findFuzzySearch");
        tmpFindFuzzySearch.setParameter("fuzzyDescr", descr);
        return tmpFindFuzzySearch.getResultList();
    }
    
    @Override
    public List<User> searchUserByName(String name) {
        if (name == null) return null;
        
        Query tmpFindFuzzySearch = em.createNamedQuery("User.findFuzzyNameSearch");
        tmpFindFuzzySearch.setParameter("fuzzyName", name);
        return tmpFindFuzzySearch.getResultList();
    }
    
    @Override
    public List<User> searchUserByLName(String name) {
        if (name == null) return null;
        
        Query tmpFindFuzzySearch = em.createNamedQuery("User.findFuzzyLNameSearch");
        tmpFindFuzzySearch.setParameter("fuzzyName", name);
        return tmpFindFuzzySearch.getResultList();
    }
    
    @Override
    public List<Cart> findAllUserItemsInCartByUserID(Integer id){
        if (id == null) return null;
        
        Query tmpFindFuzzySearch = em.createNamedQuery("Cart.findByUserID");
        tmpFindFuzzySearch.setParameter("fuzzyID", id);
        return tmpFindFuzzySearch.getResultList();
    }
    
    @Override
    public List<Cart> findAllItemsInCartByItemID(Integer id){
        if (id == null) return null;
        
        Query tmpFindFuzzySearch = em.createNamedQuery("Cart.findByProductID");
        tmpFindFuzzySearch.setParameter("fuzzyID", id);
        return tmpFindFuzzySearch.getResultList();
    }
    
    @Override
    public boolean deleteUserByID(Integer id) {
//        if (id == null) return false;
//        
//        Query tmpFindFuzzySearch = em.createNamedQuery("User.deleteByID");
//        tmpFindFuzzySearch.setParameter("fuzzyID", id);
//        return true;
        
        if (id < 1) return false;
        
        // find by primary key
        User foundInvItem = em.find(User.class, id);
        if (foundInvItem == null) { //not found - return false
            return false;
        }
        else {
            em.remove(foundInvItem);
        }

        return true;
    }
    
     @Override
    public boolean deletePartByID(Integer id) {
//        if (id == null) return false;
//        
//        Query tmpFindFuzzySearch = em.createNamedQuery("Inventory.deleteByID");
//        tmpFindFuzzySearch.setParameter("fuzzyID", id);
//        return true;
        if (id < 1) return false;
        
        // find by primary key
        Inventory foundInvItem = em.find(Inventory.class, id);
        if (foundInvItem == null) { //not found - return false
            return false;
        }
        else {
            em.remove(foundInvItem);
        }

        return true;
    }
    
    @Override
    public boolean deleteItemFromCart(Integer id){
        if (id < 1) return false;
        
        // find by primary key
        Cart foundCartItem = em.find(Cart.class, id);
        if (foundCartItem == null) { //not found - return false
            return false;
        }
        else {
            em.remove(foundCartItem);
        }

        return true;
    }
    
    @Override
    public boolean buyByUserID(Integer id){
        if (id < 1) return false;
        
        // find by primary key
        Query tmpFindFuzzySearch = em.createNamedQuery("Cart.findByUserID");
        tmpFindFuzzySearch.setParameter("fuzzyID", id);
        List<Cart> itemList = tmpFindFuzzySearch.getResultList();
        if (itemList == null) { //not found - return false
            return false;
        }
        else {
            for(Cart cart : itemList) {
                em.remove(cart);
            }
            
        }

        return true;
    }
    
    @Override
    public List<Inventory> getAllParts() {
        return em.createNamedQuery("Inventory.findAll").getResultList();
    }

    @Override
    public List<Inventory> getAllUsers() {
        return em.createNamedQuery("User.findAll").getResultList();
    }
    
    @Override
    public List<Cart> getAllCartItems(){
        return em.createNamedQuery("Cart.findAll").getResultList();
    }
    
    @Override
    public Double calculateTotal() {
        return invSess.calculateTotal();
    }

    @Override
    public Double calculateBalance() {
        return invSess.calculateBalance();
    }

    @Override
    public Double calculateReserved() {
        return invSess.calculateReserved();
    }

}
