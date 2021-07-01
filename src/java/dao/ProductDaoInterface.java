/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Product;

/**
 *
 * @author vdtru
 */
public interface ProductDaoInterface {

    public List<Product> getMany();

    public Product getProductById(int id);

    public List<Product> getNewProducts();

    public List<Product> getBestSellerProducts();

    public boolean createProduct(Product product);

    public boolean updateProduct(Product product);

    public boolean removeProductById(int id);

    public boolean removeProductByCategoryId(int categoryId);
    
    public List<Product> getProductbyIdCategory(int id);
}
