/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import database.DbConnection;
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Cart;
import model.Order;
import model.OrderDetail;
import util.Util;

/**
 *
 * @author vdtru
 */
public class OrderDao implements OrderDaoInterface {

    private final DbConnection db = new DbConnection();

    static List<Order> getCategoriesFromResultSet(ResultSet rs) throws SQLException {
        List<Order> orders = new ArrayList<>();
        while (rs.next()) {
            Order order = new Order();
            order.setId(rs.getInt("id"));
            order.setUserId(rs.getInt("userId"));
            order.setDescription(rs.getString("description"));
            order.setAddress(rs.getString("address"));
            order.setPhone(rs.getString("phone"));
            order.setTotal(rs.getBigDecimal("total"));
            order.setCreatedAt(rs.getTimestamp("createdAt").toLocalDateTime());
            order.setIsFinished(rs.getInt("isFinished"));
            orders.add(order);
        }
        return orders;
    }

    @Override
    public List<Order> getMany() {
        List<Order> orders = new ArrayList<>();

        try {
            this.db.open();

            String query = "SELECT * FROM orders";
            ResultSet rs = this.db.executeQuery(query);
            orders = getCategoriesFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return orders;
    }

    @Override
    public Order getOrderById(int id) {
        List<Order> orders = new ArrayList<>();

        try {
            this.db.open();

            String query = String.format("SELECT * FROM orders WHERE id = %d", id);
            ResultSet rs = this.db.executeQuery(query);
            orders = getCategoriesFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return orders.size() > 0 ? orders.get(0) : null;
    }

    @Override
    public boolean createOrder(Order order) {
        try {
            this.db.open();

            // get cart by userId
            CartDao cartDao = new CartDao();
            List<Cart> currentCart = cartDao.getCartByUserId(order.getUserId());

            if (currentCart == null) {
                return false;
            }

            // insert to order
            String query = ""
                    + "INSERT INTO                                                  "
                    + "     orders (id, userId, description, address, phone, total, createdAt, isFinished) "
                    + "VALUES                                                       "
                    + "     (?, ?, ?, ?, ?, ?, ?, ?)                                ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(order);

            // execute
            int rowsEffected = this.db.executeUpdate(query, paramValues);

            if (rowsEffected <= 0) {
                return false;
            }

            // get last order
            query = "SELECT * FROM orders ORDER BY id DESC";
            ResultSet rs = this.db.executeQuery(query);
            if (rs == null) {
                return false;
            }

            List<Order> orders = getCategoriesFromResultSet(rs);
            if (orders.size() <= 0) {
                return false;
            }
            Order lastOrder = orders.get(0);

            // insert to order detail and calculate total
            BigDecimal total = new BigDecimal("0");

            OrderDetailDao orderDetailDao = new OrderDetailDao();

            for (Cart detail : currentCart) {
                OrderDetail orderDetail = new OrderDetail();

                orderDetail.setOrderId(lastOrder.getId());
                orderDetail.setProductId(detail.getProductId());
                orderDetail.setQuantity(detail.getQuantity());
                orderDetail.setDiscountPercent(detail.getDiscountPercent());
                orderDetail.setSubTotal(detail.getSubTotal());

                total = total.add(detail.getSubTotal());

                boolean result = orderDetailDao.createOrderDetail(orderDetail);
                if (result == false) {
                    return false;
                }
            }

            lastOrder.setTotal(total);

            // update Order
            boolean result = this.updateOrder(lastOrder);
            if (result == false) {
                return false;
            }

            // clean cart
            return cartDao.cleanCart(order.getUserId());
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }
        return false;
    }

    @Override
    public boolean toggleIsFinishedById(int id) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "UPDATE                   "
                    + "     orders              "
                    + "SET                      "
                    + "     isFinished = 1      "
                    + "WHERE id = ?             ";

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

    @Override
    public boolean updateOrder(Order order) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "UPDATE                   "
                    + "     orders              "
                    + "SET                      "
                    + "     userId = ?,         "
                    + "     description = ?,    "
                    + "     address = ?,        "
                    + "     phone = ?,          "
                    + "     total = ?,          "
                    + "     createdAt = ?,      "
                    + "     isFinished = ?      "
                    + "WHERE id = ?             ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(order);
            String id = paramValues.get(0);
            paramValues.remove(0);
            paramValues.add(id);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean removeOrderById(int id) {
        int rowsEffected = 0;

        try {
            this.db.open();

            OrderDetailDao detailDao = new OrderDetailDao();
            detailDao.removeOrderDetailByOrderId(id);

            String query = ""
                    + "DELETE FROM      "
                    + "     orders      "
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
