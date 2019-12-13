package com.example.vesti.service;

import java.sql.Connection;
import java.sql.SQLException;
import com.example.vesti.dao.ResourcesManager;
import com.example.vesti.dao.CategoryDao;
import com.example.vesti.data.Category;
import com.example.vesti.exception.VestiException;
import java.util.ArrayList;

public class CategoryService {

    private static final CategoryService instance = new CategoryService();

    private CategoryService() {
    }

    public static CategoryService getInstance() {
        return instance;
    }

    public ArrayList<Category> findCategory () throws VestiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();

            return CategoryDao.getInstance().find(con);

        } catch (SQLException ex) {
            throw new VestiException("Failed to find categories", ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public ArrayList<Category> findCategoryENG () throws VestiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();

            return CategoryDao.getInstance().findENG(con);

        } catch (SQLException ex) {
            throw new VestiException("Failed to find categories", ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
}