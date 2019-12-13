package com.example.vesti.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.example.vesti.data.Post;
import java.util.ArrayList;



public class PostDao {

    private static final PostDao instance = new PostDao();

    private PostDao() {
    }

    public static PostDao getInstance() {
        return instance;
    }

    public ArrayList<Post> find(int category_id, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> posts = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM post WHERE category_id=? AND eng=? ORDER BY post_id DESC LIMIT 10");
            ps.setInt(1, category_id+1);
            ps.setInt(2, 0);
            rs = ps.executeQuery();
            while (rs.next()) {
                
               Post post = new Post(rs.getString("image_url"), rs.getString("title"), rs.getString("date"),rs.getString("content"));
                posts.add(post);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return posts;
    }
    
    
     public ArrayList<Post> findENG(int category_id, Connection con) throws SQLException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Post> posts = new ArrayList<>();
        try {
            ps = con.prepareStatement("SELECT * FROM post WHERE category_id=? AND eng=? ORDER BY post_id DESC LIMIT 10");
            ps.setInt(1, category_id+1);
            ps.setInt(2, 1);
            rs = ps.executeQuery();
            while (rs.next()) {
                
                Post post = new Post(rs.getString("image_url"), rs.getString("title"), rs.getString("date"),rs.getString("content"));
                posts.add(post);
            }
        } finally {
            ResourcesManager.closeResources(rs, ps);
        }
        return posts;
    }
  
}
