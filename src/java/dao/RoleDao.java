/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import database.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import util.Util;

/**
 *
 * @author vdtru
 */
public class RoleDao implements RoleDaoInterface {

    private final DbConnection db = new DbConnection();

    static List<Role> getRolesFromResultSet(ResultSet rs) throws SQLException {
        List<Role> roles = new ArrayList<>();
        while (rs.next()) {
            Role role = new Role();
            role.setId(rs.getInt("id"));
            role.setRole(rs.getString("role"));
            roles.add(role);
        }
        return roles;
    }

    @Override
    public List<Role> getMany() {
        List<Role> roles = new ArrayList<>();

        try {
            this.db.open();

            String query = "SELECT * FROM roles";
            ResultSet rs = this.db.executeQuery(query);
            roles = getRolesFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return roles;
    }

    @Override
    public Role getRoleById(int id) {
        List<Role> roles = new ArrayList<>();

        try {
            this.db.open();

            String query = String.format("SELECT * FROM roles WHERE id = %d", id);
            ResultSet rs = this.db.executeQuery(query);
            roles = getRolesFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return roles.size() > 0 ? roles.get(0) : null;
    }

    @Override
    public boolean createRole(Role role) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "INSERT INTO           "
                    + "     roles (id, role) "
                    + "VALUES                "
                    + "     (?, ?)           ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(role);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean updateRole(Role role) {

        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "UPDATE        "
                    + "     roles    "
                    + "SET           "
                    + "     role = ? "
                    + "WHERE         "
                    + "     id = ?   ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(role);
            String id = paramValues.get(0);
            paramValues.remove(0);
            paramValues.add(id);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean removeRoleById(int id) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "DELETE FROM      "
                    + "     roles       "
                    + "WHERE id = ?     ";

            PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
            stmt.setInt(1, id);

            // execute
            rowsEffected = stmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(RoleDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }
}
