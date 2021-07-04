/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import dao.ProductDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Product;

/**
 *
 * @author Tuan
 * them xoa sua product
 */
@WebServlet(name = "AdminProductController", urlPatterns = {"/AdminProduct"})
public class AdminProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //String admin=request.getAttribute("admin").toString();//get cái tên admin từ request.setAttribute("admin",admin);
        //String admin=request.getParameter("uid");//dùng này như kiểu lấy từ box trên jsp giống uid của user
        //if("uid"=="1")
        {
        ProductDao pdao=new ProductDao();
        
        List<Product> productbycate=pdao.getMany();
        request.setAttribute("product", productbycate);
	request.getRequestDispatcher("jsp/admin/admin.jsp").forward(request, response);
        }
        //response.sendRedirect("jsp/admin/admin.jsp");
       
    }
}
