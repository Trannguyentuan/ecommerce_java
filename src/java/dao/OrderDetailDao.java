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
import model.OrderDetail;
import util.Util;

/**
 *
 * @author vdtru
 */
public class OrderDetailDao implements OrderDetailDaoInterface {

    private final DbConnection db = new DbConnection();

    static List<OrderDetail> getOrderDetailsFromResultSet(ResultSet rs) throws SQLException {
        List<OrderDetail> orderDetails = new ArrayList<>();
        while (rs.next()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(rs.getInt("orderId"));
            orderDetail.setProductId(rs.getInt("productId"));
            orderDetail.setQuantity(rs.getInt("quantity"));
            orderDetail.setDiscountPercent(rs.getBigDecimal("discountPercent"));
            orderDetail.setSubTotal(rs.getBigDecimal("subTotal"));
            orderDetails.add(orderDetail);
        }
        return orderDetails;
    }

    @Override
    public List<OrderDetail> getMany() {
        List<OrderDetail> orderDetails = new ArrayList<>();

        try {
            this.db.open();

            String query = "SELECT * FROM order_details";
            ResultSet rs = this.db.executeQuery(query);
            orderDetails = getOrderDetailsFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDetailDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return orderDetails;
    }

    @Override
    public OrderDetail getOrderDetailByOrderIdAndProductId(int orderId, int productId) {
        List<OrderDetail> orderDetails = new ArrayList<>();

        try {
            this.db.open();

            String query = String.format(""
                    + "SELECT * "
                    + "FROM                                 "
                    + "     order_details                   "
                    + "WHERE                                "
                    + "     orderId = %d AND productId = %d ",
                    orderId, productId
            );
            ResultSet rs = this.db.executeQuery(query);
            orderDetails = getOrderDetailsFromResultSet(rs);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDetailDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return orderDetails.size() > 0 ? orderDetails.get(0) : null;
    }

    @Override
    public boolean createOrderDetail(OrderDetail orderDetail) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "INSERT INTO                                                                  "
                    + "     order_details (orderId, productId, quantity, discountPercent, subTotal) "
                    + "VALUES                                                                       "
                    + "     (?, ?, ?, ?, ?)                                                         ";

            // get params
            List<String> paramValues = Util.getParameterValues(orderDetail);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDetailDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean updateOrderDetail(OrderDetail orderDetail) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "UPDATE                               "
                    + "     order_details                          "
                    + "SET                                  "
                    + "     quantity = ?,                   "
                    + "     discountPercent = ?,            "
                    + "     subTotal = ?                    "
                    + "WHERE                                "
                    + "     orderId = ? AND productId = ?   ";

            // get params and re-arrange
            List<String> paramValues = Util.getParameterValues(orderDetail);
            paramValues.add(paramValues.get(0));
            paramValues.add(paramValues.get(1));
            paramValues.remove(0);
            paramValues.remove(0);

            // execute
            rowsEffected = this.db.executeUpdate(query, paramValues);

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDetailDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

    @Override
    public boolean removeOrderDetailByOrderId(int orderId) {
        int rowsEffected = 0;

        try {
            this.db.open();

            String query = ""
                    + "DELETE FROM          "
                    + "     order_details   "
                    + "WHERE                "
                    + "     orderId = ?     ";

            PreparedStatement stmt = this.db.getConnection().prepareStatement(query);
            stmt.setInt(1, orderId);

            // execute
            rowsEffected = stmt.executeUpdate();

        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(OrderDetailDao.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            db.close();
        }

        return rowsEffected > 0;
    }

}
