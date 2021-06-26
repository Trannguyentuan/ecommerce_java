/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.User;

/**
 *
 * @author vdtru
 */
public interface UserDaoInterface {

    public List<User> getMany();

    public User getUserById(int id);

    public boolean createUser(User user);

    public boolean updateUser(User user);

    public boolean checkLogin(String username, String password);

    public boolean removeUserbyId(int id);
    
    public boolean checkadmin(String username, String password);
    
    public User getUserByUsername(String username);
}
