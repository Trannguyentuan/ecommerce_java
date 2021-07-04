<%@page import="dao.ProductDao"%>
<%@page import="model.Product"%>
<%@page import="model.Category"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="dao.CategoryDao"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Quản lý bán hàng</title>
    
    <link type="text/css" href="files/css/main.css" rel="stylesheet" />
	<script type="text/javascript" src="files/js/jquery_min.js"></script>
    <script type="text/javascript" src="files/js/danhmuc.js"></script>
</head>

<body>
	<!--HEADER-->
    <div class="header">
        <a href="AdminProduct" title="Quản lý bán hàng" style="outline:none;"><h1>QUẢN LÝ BÁN HÀNG</h1></a>
        <div class="logout">
            Xin chào <c:out value="${admin}"/> | <a href="/Logout" title="Log out">Log out</a>
        </div> 
        <!--NAVIGATION BAR-->
        <ul class="navigation">
            <a href="AdminProduct" title="Danh mục sản phẩm" class="navigation-item-selected"><li>Danh mục sản phẩm</li></a>            
            <a href="AdminOrder" title="Quản lý đơn hàng"><li>Quản lý đơn hàng</li></a>
            <a href="AdminUser" title="Quản lý khách hàng"><li>Quản lý tài khoản</li></a>
        </ul><!--NAVIGATION BAR-->
    </div><!--END HEADER-->
    <!--CONTENT-->
    <div class="content">
        <!--DANH SACH
        <div class="danhsach">
        	<ul class="danhsach-header">
            	<li>ID</li>
                <li style="width:200px;">Tên sản phẩm</li>
                <li style="width:400px;">Mô tả</li>
                <li style="width:100px;">Quantity</li>
                <li style="width:100px;">Price</li>
                <li style="width:100px; float:right; text-align:right;">
                	<a href="AddProduct" name="btnThemDanhMuc" id="btnThemDanhMuc" title="Thêm danh mục mới">+ Thêm mới</a>
                </li>            </ul>
            
            <div class="danhsach-chitiet">
                -->
              <%!
            List<Product> p = new ArrayList<>();
        %>
        <%
            ProductDao pDao = new ProductDao();
            p = pDao.getMany();
        %>
        <a href="jsp/admin/addproduct.jsp" name="btnThemDanhMuc" id="btnThemDanhMuc" title="Thêm Product">+ Thêm mới</a>
        <table border="1" width="50%">
            <tr>
                <th>ID</th>
                <th>Tên sản phẩm</th>
                <th>Mô tả</th>
                <th>Quantity</th>
                <th>Price</th>               
            </tr>
            <c:forEach items="<%=p%>" var="a">
                <tr>
                    <td>${a.getId()}</td>
                    <td>${a.getName()}</td>
                    <td>${a.getDescription()}</td>
                    <td>${a.getQuantity()}</td>
                    <td>${a.getPrice()}</td>                  
                </tr>
            </c:forEach>
        </table>
            </div>
                        
        </div><!--END DANH SACH-->
    </div><!--END CONTENT-->
    />
    <!--FOOTER-->
    <div class="footer">
    	<p><b>Đồ án Lập trình ứng dụng JAVA - 12HCA2</b></p>
        <span style="line-height:18px;">
            <b>Nhóm thực hiện:</b><br/>
            Nguyễn Ngọc Khánh (1241350)<br/>
            Bùi Bá Lộc (1241363)<br/>
            Dương Diệu Pháp (1241378)<br/>
            Nguyễn Quốc Tuấn (1241341)
        </span>
    </div><!--END FOOTER-->
    
</body>
</html>