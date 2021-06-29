/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.ProductDao;
import java.util.List;
import javax.servlet.RequestDispatcher;
import model.Product;
/**
 *
 * @author TUAN
 */
@WebServlet("/productbycategory")
public class ProductByCategoryController extends HttpServlet{
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductDao pdao=new ProductDao();
                //cái này sắp xết theo category, ông gán cái thẻ category đó bằng idcategory( vd:<a href="ring?cid=${c.id}">ring</a> )
                String cid=request.getParameter("cid");
                int id=Integer.parseInt(cid);
                List<Product> productbycate=pdao.getProductbyIdCategory(id);
                request.setAttribute("productbycategory", productbycate);
		request.getRequestDispatcher("jsp/productbycategory.jsp").forward(request, response);
                 
    }
    
}
