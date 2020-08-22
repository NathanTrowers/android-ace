package com.nathan_trowers.androidace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.FragmentActivity;

import com.nathan_trowers.androidace.Model.Number11;
import com.nathan_trowers.androidace.Model.Question;
import com.nathan_trowers.androidace.Question_Activity_Fragments.Question1;
import com.nathan_trowers.androidace.Question_Activity_Fragments.Question2;
import com.nathan_trowers.androidace.Question_Activity_Fragments.Question3;
import com.nathan_trowers.androidace.Question_Activity_Fragments.Question4;

import java.util.ArrayList;

public class QuestionFragment extends FragmentActivity {

    /**BEGIN***Variable Declaration*/
    Question currentQuestion;                                       //Cache the question assigned to each variable
    View currentView;
    View resultsView ;
    private CreateQuiz quiz = new CreateQuiz();                    //Object containing the quiz generating code
    public ArrayList<Question> questionBank = quiz.getQuizSet();   //ArrayList containing all questions
    public Number11 questionFour = new Number11();
    public static int qNo = CreateQuiz.qNo;                        //Trigger for moving to the  next question.
    public static int correctAnswers = CreateQuiz.correctAnswers;

    /*Initiate Question objects*/
    private Question1 q1 = new Question1();
    private Question2 q2 = new Question2();
    private Question3 q3 = new Question3();
    private Question4 q4 = new Question4();
    private Question5 q5 = new Question5();

    /**END***Variable Declaration*/
    /*************************************************************************************WARNING: figure out if an unused 'onCeateView' method is a bad thing.*/
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState )
    {/**********************************************************************************************************************WORK ON CREATING THE CLASSES IN RED.*/

    //Start the quiz
        switch(qNo)
        {
            case 0:
            {
                q1.getOptions(questionBank.get(qNo));
                currentView = q1.onCreateView(inflater, container, savedInstanceState);
                break;
            }
            case 1:
            {
                q2.getOptions(questionBank.get(qNo));
                currentView = q2.onCreateView(inflater, container, savedInstanceState);
                break ;
            }
            case 2:
            {
                q3.getOptions(questionBank.get(qNo));
                currentView = q3.onCreateView(inflater, container, savedInstanceState);
                break ;
            }
            case 3:
            {
                q4.getOptions(questionFour);
                currentView = q4.onCreateView(inflater, container, savedInstanceState);
                break ;
            }
            case 4:
            {
                q5.getOptions(questionBank.get(qNo-1));
                currentView = q5.onCreateView(inflater, container, savedInstanceState);
                break ;
            }
        }


        return currentView;
    }

//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//    }

}