package com.nathan_trowers.androidace;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nathan_trowers.androidace.Model.Question;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;
import io.realm.annotations.PrimaryKey;

public class CreateQuiz {
    /**BEGIN***Variable Declaration*/
    ArrayList<Question> quizSet = new ArrayList<Question>();    //Stores all five of the selected quiz questions.
    Question[] randomFourQuestions  = new Question[4];          //Stores four select questions
    /**END***Variable Declaration*/

    /*****************This method creates the questions to display.*/
    public ArrayList<Question> getQuizSet()
    {
        //Get the randomly selected questions
        randomFourQuestions = getRandomQuestions();

        //Get the set question
        Question questionFour = new Number11();

        quizSet.add(randomFourQuestions[0]);    //Question 1
        quizSet.add(randomFourQuestions[1]);    //Question 2
        quizSet.add(randomFourQuestions[2]);    //Question 3
        quizSet.add(questionFour);              //Question 4; this is always the same.
        quizSet.add(randomFourQuestions[3]);    //Question 5

        return quizSet;
    }

    /*************************This method randomly selects four of the database's questions*/
    private Question[] getRandomQuestions()
    {
    /*BEGIN***Variable Declaration*/
        Realm AceQuiz = Realm.getDefaultInstance();                             //Connect to the Realm database
        Random numberSelector = new Random();                                   //Selects random numbers
        Question[] randomFourQuestions  = new Question[4];                      //Stores four select questions
        int[] questionSelection = new int[4];                                   //Stores the randomly generated numbers.
        RealmQuery<Question> questionRetriever = AceQuiz.where(Question.class); //Cache the database records.
        RealmResults<Question> questionData = questionRetriever.findAll();      //Cache a query that returns all records.
    /*END***Variable Declaration*/

        //Get four random question numbers to select the questions with.
        for(int x : questionSelection)
        {
            questionSelection[x] = numberSelector.nextInt(10) + 1;

            randomFourQuestions[x] = questionData.get(questionSelection[x]);        //Retrieve the questions from the Realm database.
        }

        return randomFourQuestions;
    }
}

/*==================This class contains data on the quiz question 11, outside of of the database.==================*/
class Number11 extends Question
{

/*BEGIN***Variable Declaration*/
    @PrimaryKey
    private int questionNumber;
    private String question;
    private int answerPicture;
    private int[] optionPictures = new int[4];
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
    public int[] getOptionPictures() {
        return optionPictures;
    }

/*END***Getters*/

}
