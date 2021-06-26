/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.CartDao;
import dao.CategoryDao;
import dao.OrderDao;
import dao.ProductDao;
import dao.UserDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Cart;
import model.Category;
import model.Order;
import model.Product;
import model.User;

/**
 *
 * @author TUAN
 */
@WebServlet(name = "LoginController", urlPatterns = {"/Login"})//xai post de luu du lieu an toan
public class LoginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet LoginController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet LoginController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        //processRequest(request, response);
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
                        /*CategoryDao cateDao=new CategoryDao();
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
                        req.setAttribute("order", order);*/
                        //quan ly all ko can gui gi het?
                        req.getRequestDispatcher("/admin/home.jsp").forward(req, resp);//login admin
                    }
                    else//login la client
                    {
                        UserDao uDao=new UserDao();
                        User user=uDao.getUserByUsername(username);
                        CartDao cartDao=new CartDao();
                        //Cart cart =cartDao.getCartByUserId(user.getId());
                        req.setAttribute("user", user);
                        req.getRequestDispatcher("/jsp/home.jsp").forward(req, resp);//gui user va cart den home
                    }
                }
                else//sai
                {
                    alertMsg = "Username or password sai!!!";
	            req.setAttribute("alert", alertMsg);
	            req.getRequestDispatcher("/jsp/login.jsp").forward(req, resp);
                }
                
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
