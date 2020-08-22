package com.nathan_trowers.androidace.Model;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import androidx.core.content.res.ResourcesCompat;

import com.nathan_trowers.androidace.R;

import io.realm.annotations.PrimaryKey;

/*==================This class contains data on the quiz question 11, outside of of the database.==================*/
public class Number11 extends Question{

    /*BEGIN***Variable Declaration*/
    @PrimaryKey
    private int questionNumber;
    private String question;
    private int answerPicture;
    private Drawable[] optionPictures = new Drawable[4];
    /*END***Variable Declaration*/


    //Constructor
    public Number11()
    {
        this.questionNumber = 11;
        this.question = "Which is a widget?";
        this.answerPicture = R.drawable.seek_bar;
        this.optionPictures[0] = R.drawable.button;
        this.optionPictures[1] = R.drawable.chip;
        this.optionPictures[2] = R.drawable.seek_bar;
        this.optionPictures[3] = R.drawable.text_view;
    }

    /*BEGIN***Getters*/

    //Question Number
    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }

    //Question
    public String getQuestion() {
        return question;
    }

    //Answer Picture
    public int getAnswerPicture() {
        return answerPicture;
    }

    ///Option Pictures
    public Drawable[] getOptionPictures() {
        return optionPictures;
    }

    /*END***Getters*/

    private fetchDrawable(int image)
    {
        Context context = this;/****************************************************WORKING HERE:create a method that accepts a new image. and caches it.*/
        Resources res = context.getResources();
        ResourcesCompat.getDrawable(res, image, null);
    }

}

