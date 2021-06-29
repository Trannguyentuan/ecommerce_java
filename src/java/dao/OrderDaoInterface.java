/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Order;
import model.Product;

/**
 *
 * @author vdtru
 */
public interface OrderDaoInterface {

    public List<Order> getMany();

    public Order getOrderById(int id);

    public boolean createOrder(Order order);

    public boolean toggleIsFinishedById(int id);

    public boolean updateOrder(Order order);

    public boolean removeOrderById(int id);
}
