package com.nathan_trowers.androidace;

import com.nathan_trowers.androidace.Model.Question;

import java.util.ArrayList;
import java.util.Random;

import io.realm.RealmResults;

public class CreateQuiz {
    /**BEGIN***Variable Declaration*/
    ArrayList<Question> quizSet = new ArrayList<Question>();
    int[] questionSelection = new int[4];
    /**END***Variable Declaration*/

    /*****************This method creates the questions to display.*/
    public Question getQuizSet()
    {

        return quizSet;
    }

    private Question getRandomQuestions()
    {
        /**BEGIN***Variable Declaration*/
        Random numberSelector = new Random();               //Selects random numbers
        Question randomlySelected = new Question();         //Stores a single random question/.
        Question[] randomFourQuestions  = new Question[4];  //Stores four select questions
        /**END***Variable Declaration*/

        //Get four random question numbers to select the questions with.
        for(int x : questionSelection)
        {
            questionSelection[x] = numberSelector.nextInt(10) + 1;
            RealmResults<Question> selection =
            randomlySelected.getQuestionNumber(questionSelection[x]);
        }

        //Retrieve the questions from the Realm database.


        return
    }
}
