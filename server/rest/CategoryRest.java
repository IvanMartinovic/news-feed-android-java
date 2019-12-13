
package com.example.vesti.rest;



import javax.ws.rs.GET;


import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import com.example.vesti.data.Category;
import com.example.vesti.exception.VestiException;
import com.example.vesti.service.CategoryService;
import java.util.ArrayList;



@Path("category")
public class CategoryRest {
    
    private final CategoryService categoryService = CategoryService.getInstance();
    
    @GET
    @Path("/getCategories")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Category> getCategories() throws VestiException {
        return categoryService.findCategory();
    }
    
    @GET
    @Path("/getCategoriesENG")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Category> getCategoriesENG() throws VestiException {
        return categoryService.findCategoryENG();
    } 
}
