/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.OrderDetail;

/**
 *
 * @author vdtru
 */
public interface OrderDetailDaoInterface {

    public List<OrderDetail> getMany();

    public OrderDetail getOrderDetailByOrderIdAndProductId(int orderId, int productId);

    public boolean createOrderDetail(OrderDetail orderDetail);

    public boolean updateOrderDetail(OrderDetail orderDetail);

    public boolean removeOrderDetailByOrderId(int orderId);

}
