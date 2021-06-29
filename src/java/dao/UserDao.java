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
import model.User;
import util.Util;

/**
 *
 * @author vdtru
 */
public class UserDao implements UserDaoInterface {

    private final DbConnection db = new DbConnection();

    static List<User> getUsersFromResultSet(ResultSet rs) throws SQLException {
        List<User> users = new ArrayList<>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setName(rs.getString("name"));
            user.setPhone(rs.getString("phone"));
            user.setEmail(rs.getString("email"));
            user.setImageUrl(rs.getString("imageUrl"));
            user.setCreatedAt(rs.getDate("createdAt").toLocalDate());
            user.setUpdatedAt(rs.getDate("updatedAt").toLocalDate());
            user.setRoleId(rs.getInt("roleId"));
            user.setRoleName(rs.getString("roleName"));
            users.add(user);
        }
        return users;
    }

    @Override
    public List<User> getMany() {
        List<User> users = new ArrayList<>();

        try {
            this.db.open();

            String query = ""
                    + "SELECT u.*, r.role AS roleName "
                    + "FROM users u "
                    + "JOIN roles r ON u.roleId = r.id";
            ResultSet rs = this.db.executeQuery(query);
            users = getUsersFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return users;
    }

    @Override
    public User getUserById(int id) {
        List<User> users = new ArrayList<>();

        try {
            this.db.open();

            String query = String.format(
                    ""
                    + "SELECT                       "
                    + "     u.*, r.role AS roleName "
                    + "FROM                         "
                    + "     users u                 "
                    + "JOIN                         "
                    + "     roles r                 "
                    + "ON                           "
                    + "     u.roleId = r.id         "
                    + "WHERE                        "
                    + "     u.id = %d               "
                    + "GROUP BY                     "
                    + "     u.id                    ",
                    id
            );
            ResultSet rs = this.db.executeQuery(query);
            users = getUsersFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return users.size() > 0 ? users.get(0) : null;
    }

    @Override
    public boolean createUser(User user) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "INSERT INTO                          "
                    + "     users                           "
                    + "     (id, username, password, name, phone, email, imageUrl, createdAt, updatedAt, roleId) "
                    + "VALUES                               "
                    + "     (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(user);
            paramValues.remove(paramValues.size() - 1);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }
        return rowsEffected > 0;
    }
   
    
    @Override
    public boolean updateUser(User user) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "UPDATE"
                    + "     users           "
                    + "SET                  "
                    + "     username = ?,   "
                    + "     password = ?,   "
                    + "     name = ?,       "
                    + "     phone = ?,      "
                    + "     email = ?,      "
                    + "     imageUrl = ?,   "
                    + "     createdAt = ?,  "
                    + "     updatedAt = ?,  "
                    + "     roleId= ?       "
                    + "WHERE                "
                    + "     id = ?          ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(user);
            paramValues.set(paramValues.size() - 1, paramValues.get(0));
            paramValues.remove(0);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean checkLogin(String username, String password) {
        int result = 0;

        try {
            this.db.open();

            String query = String.format(
                    ""
                    + "SELECT                                    "
                    + "     COUNT(*) AS result                   "
                    + "FROM                                      "
                    + "     users                                "
                    + "WHERE                                     "
                    + "     username = '%s' AND password = '%s'  "
                    + "GROUP BY                                  "
                    + "     id                                   ",
                    username, password
            );
            ResultSet rs = this.db.executeQuery(query);
            rs.next();
            result = rs.getInt("result");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return result > 0;
    }
    
    @Override
    public boolean checkadmin(String username, String password) {
        int result = 0;

        try {
            this.db.open();

            String query = String.format(
                    ""
                    + "SELECT                                    "
                    + "     COUNT(*) AS result                   "
                    + "FROM                                      "
                    + "     users                                "
                    + "WHERE                                     "
                    + "     username = '%s' AND password = '%s' AND roleId =1  "
                    + "GROUP BY                                  "
                    + "     id                                   ",
                    username, password
            );
            ResultSet rs = this.db.executeQuery(query);
            rs.next();
            result = rs.getInt("result");
            
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return result > 0;
    }
    
    @Override
    public boolean removeUserbyId(int id) {
        int rowsEffected = 0;

        try {
            this.db.open();

            // remove order
            String query = ""
                    + "DELETE FROM       "
                    + "     orders       "
                    + "WHERE userId = ?  ";

            PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            // remove user
            query = ""
                    + "DELETE FROM       "
                    + "     users        "
                    + "WHERE id = ?      ";

            stmt = this.db.getConnection().prepareStatement(query);
            stmt.setInt(1, id);

            // execute
            rowsEffected = stmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }
    @Override
    public User getUserByUsername(String username) {
        List<User> users = new ArrayList<>();

        try {
            this.db.open();

            String query = String.format(
                    ""
                    + "SELECT                       "
                    + "     u.*, r.role AS roleName "
                    + "FROM                         "
                    + "     users u                 "
                    + "JOIN                         "
                    + "     roles r                 "
                    + "ON                           "
                    + "     u.roleId = r.id         "
                    + "WHERE                        "
                    + "     u.usename = '%s'        "
                    + "GROUP BY                     "
                    + "     u.id                    ",
                    username
            );
            ResultSet rs = this.db.executeQuery(query);
            users = getUsersFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return users.size() > 0 ? users.get(0) : null;
    }
}
