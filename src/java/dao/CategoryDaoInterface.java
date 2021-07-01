/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.List;
import model.Category;

/**
 *
 * @author vdtru
 */
public interface CategoryDaoInterface {

    public List<Category> getMany();

    public Category getCategoryById(int id);

    public boolean updateCategory(Category category);

    public boolean createCategory(Category category);

    public boolean removeCategoryById(int id);
}
