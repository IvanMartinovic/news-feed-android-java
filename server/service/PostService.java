package com.example.vesti.service;

import java.sql.Connection;
import java.sql.SQLException;
import com.example.vesti.dao.ResourcesManager;
import com.example.vesti.dao.PostDao;
import com.example.vesti.data.Post;
import com.example.vesti.exception.VestiException;
import java.util.ArrayList;
public class PostService {

    private static final PostService instance = new PostService();

    private PostService() {
    }

    public static PostService getInstance() {
        return instance;
    }


    public ArrayList<Post> findPost (int category_id) throws VestiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();

            return PostDao.getInstance().find(category_id, con);

        } catch (SQLException ex) {
            throw new VestiException("Failed to find posts with category " + category_id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    
    public ArrayList<Post> findPostENG (int category_id) throws VestiException {
        Connection con = null;
        try {
            con = ResourcesManager.getConnection();

            return PostDao.getInstance().findENG(category_id, con);

        } catch (SQLException ex) {
            throw new VestiException("Failed to find posts with category " + category_id, ex);
        } finally {
            ResourcesManager.closeConnection(con);
        }
    }
    


}


