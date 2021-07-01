/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Role;

/**
 *
 * @author vdtru
 */
public interface RoleDaoInterface {

    public List<Role> getMany();

    public Role getRoleById(int id);

    public boolean createRole(Role role);

    public boolean updateRole(Role role);

    public boolean removeRoleById(int id);
}
