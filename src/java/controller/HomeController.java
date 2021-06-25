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
/**
 *
 * @author TUAN
 */
import dao.ProductDao;
import java.util.List;
import javax.servlet.RequestDispatcher;
import model.Product;
@WebServlet("/home")
public class HomeController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
                ProductDao pdao=new ProductDao();
		List<Product> productList = pdao.getMany();
                List<Product> probestsell = pdao.getBestSellerProducts();
                //cái này sắp xết theo category, ông gán cái thẻ category đó bằng số như trong db
                String cate=request.getParameter("category");//gán bằng số để get id dễ
                int id=Integer.parseInt(cate);
                
                List<Product> productbycate=pdao.getProductbyIdCategory(id);
                
		request.setAttribute("listfullProduct", productList);
                request.setAttribute("listBestSell", probestsell);
                request.setAttribute("listByCategory", productbycate);
		request.getRequestDispatcher("jsp/home.jsp").forward(request, response);
}
}
