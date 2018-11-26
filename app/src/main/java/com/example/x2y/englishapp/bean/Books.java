package com.example.x2y.englishapp.bean;

//单个词典
/**
 * Created by Lab on 2018/11/16.
 */

public class Books {
    private String name;
    private int imageId;
    public Books(String name,int imageId){
        this.name=name;
        this.imageId=imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
