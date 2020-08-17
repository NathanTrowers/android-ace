package com.nathan_trowers.androidace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nathan_trowers.androidace.Model.Question;
import com.nathan_trowers.androidace.Questions.Question1;
import com.nathan_trowers.androidace.Questions.Question2;

import java.util.ArrayList;

public class OptionsFragment extends Fragment {

    /**BEGIN***Variable Declaration*/
    Question currentQuestion;                                       //Cache the question assigned to each variable
    View currentView;
    View resultsView ;
    private CreateQuiz quiz = new CreateQuiz();                     //Object containing the quiz generating code
    public ArrayList<Question> questionBank = quiz.getQuizSet();   //ArrayList containing all questions

    /*Initiate Question objects*/
    private Question1 q1 = new Question1();
    private Question2 q2 = new Question2();
    //       private Question3 q3 = new Question3();
//       private Question4 q4 = new Question4();
//       private Question5 q5 = new Question5();
    public static int qNo = CreateQuiz.qNo;                  //Trigger for moving to the  next question.
    public static int correctAnswers = CreateQuiz.correctAnswers;
    /**END***Variable Declaration*/

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState )
    {
        //Start the quiz
        currentQuestion =  questionBank.get(0);
        q1.getOptions(currentQuestion);
        currentView = q1.onCreateView(inflater, container, savedInstanceState);


/************************************************************************************************************************WORKING HERE: Trying to connect the questions together logically*/

        return currentView;
    }

    public static void questionSelector()
    {
        switch(qNo)
        {
            case 0:
            {   /*To Do:
                    optionPlacement
                    questionPlacement*/
                break ;
            }
        }
    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

}
