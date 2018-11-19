package com.example.x2y.englishapp.bean;

import java.util.List;

public class CardMode {
    private String name;
    private String word;
    private String wordMean;
    private String sentence;
    private int year;
    private List<String> images;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWordMean() {
        return wordMean;
    }

    public void setWordMean(String wordMean) {
        this.wordMean = wordMean;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public CardMode(String name, String word, String wordMean, String sentence, int year, List<String> images) {
        this.word =word;
        this.wordMean = wordMean;
        this.sentence = sentence;
        this.name = name;
        this.year = year;
        this.images = images;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getImages() {
        return images;
    }
}
