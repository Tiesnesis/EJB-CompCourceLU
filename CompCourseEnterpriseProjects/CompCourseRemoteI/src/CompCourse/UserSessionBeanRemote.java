/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CompCourse;

import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author Edgars
 */
@Remote
public interface UserSessionBeanRemote {
    public boolean logIn(String fName, String lName);

    public List getAllParts();
    
    public List getAllUsers();

    public Double calculateTotal();

    public Double calculateBalance();

    public Double calculateReserved();

    public boolean addPart(Integer id, String descr, Double price, Integer coun);
    
    public boolean addUser(Integer id, String name, String LastName);

    public boolean reservePart(Integer id, Integer count);

    public List searchPartByDescription(String text);
    
    public List searchUserByName(String text);
    
    public List searchUserByLName(String text);
    
    public boolean deleteUserByID(Integer id);
    
    public boolean deletePartByID(Integer id);
    
    public boolean addItemToCart(Integer id, Integer userID, Integer invID, Integer count);
    
    public boolean deleteItemFromCart(Integer id);
    
    public boolean buyByUserID(Integer id);
    
    public List findAllUserItemsInCartByUserID(Integer id);
    
    
    public List findAllItemsInCartByItemID(Integer id);
    
    public List getAllCartItems();

}
