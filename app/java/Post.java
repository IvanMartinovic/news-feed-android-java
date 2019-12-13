package com.example.vesti;

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
         StringBuilder sb = new StringBuilder("Post{");
        sb.append("image_url='").append(image_url).append('\'');
        sb.append(", title='").append(title).append('\'');
        sb.append(", date='").append(date).append('\'');
        sb.append(", content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
