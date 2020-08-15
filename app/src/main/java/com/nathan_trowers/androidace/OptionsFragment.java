package com.nathan_trowers.androidace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.nathan_trowers.androidace.Model.Question;

import java.util.ArrayList;

public class OptionsFragment extends Fragment {

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Placeholder Inflater
        return inflater.inflate(R.layout.fragment_button_display, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


}

class layoutGenerator extends Fragment
{
    /**BEGIN***Variable Declaration*/
        View questionView ;
    View questionView ;
    View answerView ;
    View resultsView ;
    View questionView ;
    private CreateQuiz quiz = new CreateQuiz();                     //Object containing the quiz generating code
    private ArrayList<Question> questionBank = quiz.getQuizSet();   //ArrayList containing all questions
    private Question question1 = questionBank.get(0);               //Question 1
    /**END***Variable Declaration*/
}