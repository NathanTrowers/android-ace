package com.nathan_trowers.androidace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.nathan_trowers.androidace.Model.Question;
import com.nathan_trowers.androidace.Questions.Question1;

import java.util.ArrayList;

public class OptionsFragment extends Fragment {

    /**BEGIN***Variable Declaration*/
    Question currentQuestion ;
    View currentView;
    View resultsView ;
    private CreateQuiz quiz = new CreateQuiz();                     //Object containing the quiz generating code
    private ArrayList<Question> questionBank = quiz.getQuizSet();   //ArrayList containing all questions

    /*Initiate Question objects*/
    Question1 q1 = new Question1();
//        Question2 q2 = new Question2();
//        Question3 q3 = new Question3();
//        Question4 q4 = new Question4();
//        Question5 q5 = new Question5();
    /**END***Variable Declaration*/

    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState )
    {
        //Start the quiz
        currentQuestion =  questionBank.get(0);
        q1.getOptions(currentQuestion);
        currentView = q1.onCreateView(inflater, container, savedInstanceState);
//        for (int qNo = 0; qNo > 5; qNo++)
//        {
//            switch(qNo)
//            {
//                case 1:
//                {   /*To Do:
//                        optionPlacement
//                        questionPlacement*/
//                    break ;
//                }
//            }
//        }

        return currentView;
    }
//
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }

}
