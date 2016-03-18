package com.appsculture.models;

/**
 * Model class to store data of  Vocabulary.
 *
 * Created by shobharam.piplode on 3/16/2016.
 */
public class Word
{
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getVarient() {
        return varient;
    }

    public void setVarient(int varient) {
        this.varient = varient;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    int id;
    String word;
    int varient;
    String meaning;
    double ratio;
    String imageUrl;

}
