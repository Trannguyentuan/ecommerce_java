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
import model.Category;
import util.Util;

/**
 *
 * @author vdtru
 */
public class CategoryDao implements CategoryDaoInterface {

    private final DbConnection db = new DbConnection();

    static List<Category> getCategoriesFromResultSet(ResultSet rs) throws SQLException {
        List<Category> categories = new ArrayList<>();
        while (rs.next()) {
            Category category = new Category();
            category.setId(rs.getInt("id"));
            category.setName(rs.getString("name"));
            category.setImageUrl(rs.getString("imageUrl"));
            category.setCreatedAt(rs.getDate("createdAt").toLocalDate());
            category.setUpdatedAt(rs.getDate("updatedAt").toLocalDate());
            categories.add(category);
        }
        return categories;
    }

    @Override
    public List<Category> getMany() {
        List<Category> categories = new ArrayList<>();

        try {
            this.db.open();

            String query = "SELECT * FROM categories";
            ResultSet rs = this.db.executeQuery(query);
            categories = getCategoriesFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return categories;
    }

    @Override
    public Category getCategoryById(int id) {

        List<Category> categories = new ArrayList<>();

        try {
            this.db.open();

            String query = String.format(""
                    + "SELECT *         "
                    + "FROM             "
                    + "     categories  "
                    + "WHERE id = %d    ",
                    id
            );
            ResultSet rs = this.db.executeQuery(query);
            categories = getCategoriesFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return categories.size() > 0 ? categories.get(0) : null;
    }

    @Override
    public boolean updateCategory(Category category) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "UPDATE               "
                    + "     categories      "
                    + "SET                  "
                    + "     name = ?,       "
                    + "     imageUrl = ?,   "
                    + "     createdAt = ?,  "
                    + "     updatedAt = ?   "
                    + "WHERE id = ?         ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(category);
            String id = paramValues.get(0);
            paramValues.remove(0);
            paramValues.add(id);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean createCategory(Category category) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "INSERT INTO                                                  "
                    + "     categories (id, name, imageUrl, createdAt, updatedAt)   "
                    + "VALUES                                                       "
                    + "     (?, ?, ?, ?, ?)                                         ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(category);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CategoryDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean removeCategoryById(int id) {
        int rowsEffected = 0;

        try {
            this.db.open();

            ProductDao detailDao = new ProductDao();
            detailDao.removeProductByCategoryId(id);

            String query = ""
                    + "DELETE FROM      "
                    + "     categories      "
                    + "WHERE id = ?     ";

            PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
            stmt.setInt(1, id);

            // execute
            rowsEffected = stmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }
}
