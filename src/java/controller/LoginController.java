/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import dao.CategoryDao;
import dao.OrderDao;
import dao.ProductDao;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
/**
 *
 * @author TUAN
 */
import model.User;
import dao.UserDao;
import dao.CartDao;
import java.util.List;
import model.Category;
import model.Order;
import model.Product;
import model.Cart;
@WebServlet("/Login")
public class LoginController extends HttpServlet{
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        
        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
    }
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		  String username = req.getParameter("username");
	        
	        String password = req.getParameter("password");
                String alertMsg="";
                //bao chua nhap tk pass
	        if(username.isEmpty() || password.isEmpty()){
	            alertMsg = "Username and password can't be empty!";
	            req.setAttribute("alert", alertMsg);
	            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
	            return;
	        }
                UserDao userDao=new UserDao();
                boolean checklogin=userDao.checkLogin(username, password);
                boolean checkadmin=userDao.checkadmin(username, password);
                if(checklogin==true)
                {
                    if(checkadmin==true)
                    {
                        CategoryDao cateDao=new CategoryDao();
                        ProductDao proDao=new ProductDao();
                        OrderDao orderDao=new OrderDao();
                        List<User> user=userDao.getMany();
                        List<Category> cate=cateDao.getMany();
                        List<Product> pro=proDao.getMany();
                        List<Order> order=orderDao.getMany();
                        //gui het du lieu cho admin
                        req.setAttribute("user", user);
                        req.setAttribute("category", cate);
                        req.setAttribute("product", pro);
                        req.setAttribute("order", order);
                        req.getRequestDispatcher("/admin/login.jsp").forward(req, resp);//login admin
                    }
                    else//login la client
                    {
                        UserDao uDao=new UserDao();
                        int id=uDao.getId(username);
                        User user=uDao.getUserById(id);
                        CartDao cartDao=new CartDao();
                        Cart cart =cartDao.getCartbyId(id);
                        req.setAttribute("user", user);
                        req.setAttribute("cart", cart);
                        req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
                    }
                }
                else//sai
                {
                    alertMsg = "Username or password sai!!!";
	            req.setAttribute("alert", alertMsg);
	            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
                }
                
    }
}
