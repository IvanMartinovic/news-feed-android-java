package com.example.vesti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.vesti.data.Category;
import java.util.ArrayList;




public class CategoryDao {

    private static final CategoryDao instance = new CategoryDao();

    private CategoryDao() {
    }

    public static CategoryDao getInstance() {
        return instance;
    }

    public ArrayList<Category> find(Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Category> categories = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM category");
            rs = ps.executeQuery();
            while (rs.next()) {
                
               Category category = new Category(rs.getString("name"), rs.getString("img_url"), rs.getString("color"));
                categories.add(category);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return categories;
    }
    
     public ArrayList<Category> findENG(Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Category> categories = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM category");
            rs = ps.executeQuery();
            while (rs.next()) {
                
               Category category = new Category(rs.getString("name_eng"), rs.getString("img_url"), rs.getString("color"));
                categories.add(category);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return categories;
    }
}