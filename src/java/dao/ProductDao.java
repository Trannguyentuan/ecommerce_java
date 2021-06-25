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
import model.Product;
import util.Util;

/**
 *
 * @author vdtru
 */
public class ProductDao implements ProductDaoInterface {

    private final DbConnection db = new DbConnection();

    static List<Product> getProductsFromResultSet(ResultSet rs) throws SQLException {
        List<Product> products = new ArrayList<>();
        while (rs.next()) {
            Product product = new Product();
            product.setId(rs.getInt("id"));
            product.setName(rs.getString("name"));
            product.setDescription(rs.getString("description"));
            product.setImageUrl(rs.getString("imageUrl"));
            product.setPrice(rs.getBigDecimal("price"));
            product.setQuantityInStock(rs.getInt("quantityInStock"));
            product.setSoldQuantity(rs.getInt("soldQuantity"));
            product.setCreatedAt(rs.getDate("createdAt").toLocalDate());
            product.setUpdatedAt(rs.getDate("updatedAt").toLocalDate());
            product.setCategoryId(rs.getInt("categoryId"));
            product.setCategoryName(rs.getString("categoryName"));
            products.add(product);
        }
        return products;
    }

    @Override
    public List<Product> getMany() {
        List<Product> products = new ArrayList<>();

        try {
            this.db.open();

            String query = ""
                    + "SELECT                           "
                    + "     p.*,c.name AS categoryName  "
                    + "FROM                             "
                    + "     products p                  "
                    + "JOIN                             "
                    + "     categories c                "
                    + "ON "
                    + "     p.categoryId = c.id         "
                    + "GROUP BY                         "
                    + "     p.id                        ";

            ResultSet rs = this.db.executeQuery(query);
            products = getProductsFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return products;
    }

    @Override
    public Product getProductById(int id) {
        List<Product> products = new ArrayList<>();

        try {
            this.db.open();

            String query = String.format(
                    ""
                    + "SELECT                           "
                    + "     p.*,c.name AS categoryName  "
                    + "FROM                             "
                    + "     products p                  "
                    + "JOIN                             "
                    + "     categories c                "
                    + "ON"
                    + "     p.categoryId = c.id         "
                    + "WHERE                            "
                    + "     id = %d                     "
                    + "GROUP BY                         "
                    + "     p.id                        ",
                    id
            );
            ResultSet rs = this.db.executeQuery(query);
            products = getProductsFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return products.size() > 0 ? products.get(0) : null;
    }

    @Override
    public List<Product> getNewProducts() {
        List<Product> products = new ArrayList<>();

        try {
            this.db.open();
            String query = String.format(
                    ""
                    + "SELECT                           "
                    + "     p.*,c.name AS categoryName  "
                    + "FROM                             "
                    + "     products p                  "
                    + "JOIN                             "
                    + "     categories c                "
                    + "ON                               "
                    + "     p.categoryId = c.id         "
                    + "GROUP BY                         "
                    + "     p.id                        "
                    + "ORDER BY                         "
                    + "     createdAt DESC              "
            );

            // execute
            ResultSet rs = this.db.executeQuery(query);

            // get products from result set
            products = getProductsFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return products;
    }

    @Override
    public List<Product> getBestSellerProducts() {
        List<Product> products = new ArrayList<>();

        try {
            this.db.open();
            String query = String.format(
                    ""
                    + "SELECT                           "
                    + "     p.*,c.name AS categoryName  "
                    + "FROM                             "
                    + "     products p                  "
                    + "JOIN                             "
                    + "     categories c                "
                    + "ON                               "
                    + "     p.categoryId = c.id         "
                    + "GROUP BY                         "
                    + "     p.id                        "
                    + "ORDER BY                         "
                    + "     p.soldQuantity DESC         "
            );

            // execute
            ResultSet rs = this.db.executeQuery(query);

            // get products from result set
            products = getProductsFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return products;
    }

    @Override
    public boolean createProduct(Product product) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "INSERT INTO                              "
                    + "     products                            "
                    + "     (id, name, description, imageUrl, price, quantityInStock, soldQuantity, createdAt, updatedAt, categoryId) "
                    + "VALUES                            "
                    + "     (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)      ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(product);
            paramValues.remove(paramValues.size() - 1);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }
        return rowsEffected > 0;
    }

    @Override
    public boolean updateProduct(Product product) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "UPDATE                     "
                    + "     products              "
                    + "SET                        "
                    + "     name = ?,             "
                    + "     description = ?,      "
                    + "     imageUrl = ?,         "
                    + "     price = ?,            "
                    + "     quantityInStock = ?,  "
                    + "     soldQuantity = ?,     "
                    + "     createdAt = ?,        "
                    + "     updatedAt = ?,        "
                    + "     categoryId = ?        "
                    + "WHERE                      "
                    + "     id = ?                ";

            // get and re-arrange
            List<String> paramValues = Util.getParameterValues(product);
            paramValues.set(paramValues.size() - 1, paramValues.get(0));
            paramValues.remove(0);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean removeProductById(int id) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "DELETE FROM      "
                    + "     products    "
                    + "WHERE id = ?     ";

            PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
            stmt.setInt(1, id);

            // execute
            rowsEffected = stmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean removeProductByCategoryId(int categoryId) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "DELETE FROM              "
                    + "     products            "
                    + "WHERE categoryId = ?     ";

            PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
            stmt.setInt(1, categoryId);

            // execute
            rowsEffected = stmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ProductDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

}
