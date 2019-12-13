
package com.example.vesti.rest;



import javax.ws.rs.GET;


import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.example.vesti.data.Post;
import com.example.vesti.exception.VestiException;
import com.example.vesti.service.PostService;
import java.util.ArrayList;

@Path("post")
public class PostRest {
    
    private final PostService postService = PostService.getInstance();
    
    @GET
    @Path("/{category_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Post> getPosts(@PathParam("category_id") int category_id) throws VestiException {
        return postService.findPost(category_id);
    }
    
    @GET
    @Path("/ENG/{category_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Post> getPostsENG(@PathParam("category_id") int category_id) throws VestiException {
        return postService.findPostENG(category_id);
    }
    
    
    
}
