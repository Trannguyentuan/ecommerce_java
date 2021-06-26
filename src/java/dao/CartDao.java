/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import static dao.RoleDao.getRolesFromResultSet;
import database.DbConnection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Product;
import model.Role;
import util.Util;

/**
 *
 * @author vdtru
 */
public class CartDao implements CartDaoInterface {

    private final DbConnection db = new DbConnection();

    static List<Cart> getCartsFromResultSet(ResultSet rs) throws SQLException {
        List<Cart> carts = new ArrayList<>();
        while (rs.next()) {
            Cart cart = new Cart();
            cart.setUserId(rs.getInt("userId"));
            cart.setProductId(rs.getInt("productId"));
            cart.setProductImage(rs.getString("productImage"));
            cart.setProductName(rs.getString("productName"));
            cart.setQuantity(rs.getInt("quantity"));
            cart.setSubTotal(rs.getBigDecimal("subTotal"));
            carts.add(cart);
        }
        return carts;
    }

    @Override
    public List<Cart> getMany() {
        List<Cart> carts = new ArrayList<>();

        try {
            this.db.open();

            String query = "SELECT * FROM carts";
            ResultSet rs = this.db.executeQuery(query);
            carts = getCartsFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CartDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return carts;
    }

    @Override
    public Cart getCartByUserAndProductId(int userId, int productId) {
        List<Cart> carts = new ArrayList<>();

        try {
            this.db.open();

            String query = String.format("SELECT * FROM carts WHERE userId = %d AND productId = %d", userId, productId);
            ResultSet rs = this.db.executeQuery(query);
            carts = getCartsFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CartDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return carts.size() > 0 ? carts.get(0) : null;
    }

    @Override
    public boolean insertOrUpdateCart(int userId, int productId) {
        boolean result = false;

        try {
            this.db.open();

            Product product = (new ProductDao()).getProductById(productId);
            if (product == null) {
                return false;
            }

            Cart cart = this.getCartByUserAndProductId(userId, productId);

            if (cart == null) {
                // insert
                cart = new Cart();
                cart.setUserId(userId);
                cart.setProductId(productId);
                cart.setProductImage(product.getImageUrl());
                cart.setProductName(product.getName());
                cart.setQuantity(1);
                cart.setSubTotal(product.getPrice());

                result = this.createCart(cart);
            } else {
                // update + increase subTotal and quantity
                cart.setQuantity(cart.getQuantity() + 1);
                cart.setSubTotal(cart.getSubTotal().add(product.getPrice()));

                result = this.updateCart(cart);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CartDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return result;
    }

    @Override
    public boolean removeCartByUserAndProductId(int userId, int productId) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "DELETE FROM                          "
                    + "     carts                           "
                    + "WHERE                                "
                    + "     userId = ? AND productId = ?    ";

            PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
            stmt.setInt(1, userId);
            stmt.setInt(2, productId);

            // execute
            rowsEffected = stmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CartDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean cleanCart(int userId) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "DELETE FROM      "
                    + "     carts       "
                    + "WHERE            "
                    + "     userId = ?  ";

            PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
            stmt.setInt(1, userId);

            // execute
            rowsEffected = stmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CartDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean createCart(Cart cart) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "INSERT INTO                          "
                    + "     carts (userId, productId, productImage, productName, quantity, subTotal) "
                    + "VALUES                               "
                    + "     (?, ?, ?, ?, ?, ?)              ";

            // get params
            List<String> paramValues = Util.getParameterValues(cart);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CartDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean updateCart(Cart cart) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "UPDATE                               "
                    + "     carts                           "
                    + "SET                                  "
                    + "     productImage = ?,               "
                    + "     productName = ?,                "
                    + "     quantity = ?,                   "
                    + "     subTotal = ?                    "
                    + "WHERE                                "
                    + "     userId = ? AND productId = ?    ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(cart);
            paramValues.add(paramValues.get(0));
            paramValues.add(paramValues.get(1));
            paramValues.remove(0);
            paramValues.remove(0);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(CartDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }
}
