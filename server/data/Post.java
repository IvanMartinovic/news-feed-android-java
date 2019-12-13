
package com.example.vesti.data;
import java.io.Serializable;

public class Post implements Serializable {
    
    
     private String image_url,title,date,content;

     
    public Post(){} 
    public Post(String image_url, String title, String date, String content) {
        this.image_url = image_url;
        this.title = title;
        this.date = date;
        this.content = content;
    }


    public String getImage_url() {
        return image_url;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getContent() {
        return content;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" + "image_url=" + image_url + ", title=" + title + ", date=" + date + ", content=" + content + '}';
    }
    
    
    
}
    

