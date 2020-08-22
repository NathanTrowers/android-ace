package com.nathan_trowers.androidace;

import android.graphics.Bitmap;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nathan_trowers.androidace.Model.Number11;
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
    private ArrayList<Question> quizSet = new ArrayList<Question>();    //Stores all five of the selected quiz questions.
    private Question[] randomFourQuestions  = new Question[4];          //Stores four select questions
    public static int qNo = 0;                  //Trigger for moving to the  next question.
    public static int correctAnswers = 0;
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

