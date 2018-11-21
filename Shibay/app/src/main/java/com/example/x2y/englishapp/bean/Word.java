package com.example.x2y.englishapp.bean;

public class Word {
    private String name;
    private String mean;
    public Word(String name,String mean){
        this.name = name;
        this.mean = mean;
    }

    public String getName(){
        return name;
    }
    public String getMean(){
        return mean;
    }

    @Override
    public String toString() {
        return "单词："+name+" 释义："+mean;
    }
}
