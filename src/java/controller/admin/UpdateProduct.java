/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dao.ProductDao;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Product;

/**
 *
 * @author Tuan
 */
@WebServlet(name = "UpdateProduct", urlPatterns = {"/UpdateProduct"})
public class UpdateProduct extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //làm cái table nó edit trên đó được luôn trừ cái id
        Product product=new Product();
        String cid=request.getParameter("cid");
        String pid=request.getParameter("pid");
        //Ông set như này trên jsp request.setAttribute("product", product);, nếu ko đc báo tui xài getParameter
        product=(Product) (request.getAttribute("product"));
        
        ProductDao pdao=new ProductDao();
        pdao.updateProduct(product);
        response.sendRedirect("admin.jsp");
    }

}
