package com.example.vesti;



import java.io.Serializable;

public class Category implements Serializable {

private String name,img_url,color;



    public Category(){}
    public Category(String name, String img_url, String color) {
        this.name = name;
        this.img_url = img_url;
        this.color = color;
    }


    public String getName() {
        return name;
    }

    public String getImg_url() {
        return img_url;
    }

    public String getColor() {
        return color;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Category{");
        sb.append("name='").append(name).append('\'');
        sb.append(", img_url='").append(img_url).append('\'');
        sb.append(", color='").append(color).append('\'');
        sb.append('}');
        return sb.toString();
    }


}
