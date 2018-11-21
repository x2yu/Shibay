package com.example.x2y.englishapp.schedule;

import java.util.Date;

public class Book {
    private String bookName = null ;  //书籍名称
    private int worldCount = 0;  //单词总量
    private int learnedWorldCount = 0;  //已学习的单词数
    private boolean isLearned = false;  //记录是否学习过
    private boolean isLearnning = false;  //记录是否正在学习
    private String studyStartTime = null;  //开始学习的时间
    private String studyDeadline = null;  //学习的结束时间

    public Book(String name){
        this.bookName = name;
    }

    public String getBookName(){
        return bookName;
    }

    public void setBookName(String bookName){
        this.bookName = bookName;
    }

    public int getWorldCount(){
        return this.worldCount;
    }

    public void setWorldCount(int worldCount){
        this.worldCount = worldCount;
    }

    public int getLearnedWorldCount(){
        return this.learnedWorldCount;
    }

    public void setLearnedWorldCount(int learnedWorldCount){
        this.learnedWorldCount = learnedWorldCount;
    }

    public boolean getIsLearnedState(){
        return isLearned;
    }

    public void setIsLearnedState(boolean isLearned){
        this.isLearned = isLearned;
    }

    public boolean getIsLearnningState(){
        return isLearnning;
    }

    public void setIsLearnningState(boolean isLearnning){
        this.isLearnning = isLearnning;
    }

    public String getStudyStartTime(){
        return this.studyStartTime;
    }

    public void setStudyStartTime(String studyStartTime){
        this.studyStartTime = studyStartTime;
    }

    public String getStudyDeadline(){
        return this.studyDeadline;
    }

    public void setStudyDeadline(String studyDeadline) {
        this.studyDeadline = studyDeadline;
    }
}
