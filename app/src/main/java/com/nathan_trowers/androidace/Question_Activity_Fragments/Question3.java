package com.nathan_trowers.androidace.Question_Activity_Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.snackbar.Snackbar;
import com.nathan_trowers.androidace.CreateQuiz;
import com.nathan_trowers.androidace.Model.Question;
import com.nathan_trowers.androidace.QuestionFragment;
import com.nathan_trowers.androidace.R;

import java.util.Random;

public class Question3 extends FragmentActivity implements View.OnClickListener {
    /**BEGIN***Variable Declaration*/
    private TextView question;  //Shows the question proposed to the user.
    private RadioButton option1;     //Shows first displayed option
    private RadioButton option2;     //Shows second displayed option
    private RadioButton option3;     //Shows third displayed option
    private RadioButton option4;     //Shows fourth displayed option
    private Button submit;      //Shows the submit radioButton's listener
    private Button nextQ;//Shows the next question radioButton's listener
    private String[] responses= new String[4];  //Cache answer options' text for question
    protected boolean isOpt1 = false;     //Show if option 1 is selected
    protected boolean isOpt2 = false;     //Show if option 2 is selected
    protected boolean isOpt3 = false;     //Show if option 3 is selected
    protected boolean isOpt4 = false;     //Show if option 4 is selecte
    protected Question currentQuestion ;   //Cache the question
    public int correctAnswers  = CreateQuiz.correctAnswers;         //Record the number of correct answers
/**END***Variable Declaration*/


    /****************This method calls the question's view and initiates listeners*/
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View choicesView = inflater.inflate(R.layout.fragment_radio_button_display, container, false);

        //get element references
        question = (TextView) choicesView.findViewById(R.id.question_textTextView);
        option1 = (RadioButton) choicesView.findViewById(R.id.option1_radioButton);
        option2 = (RadioButton) choicesView.findViewById(R.id.option2_radioButton);
        option3 = (RadioButton) choicesView.findViewById(R.id.option3_radioButton);
        option4 = (RadioButton) choicesView.findViewById(R.id.option4_radioButton);
        submit = (Button) choicesView.findViewById(R.id.submitButton);
        nextQ = (Button) choicesView.findViewById(R.id.nextQuestionButton);


        //Change the displayed text on the option radioButtons
        showOptions(question, option1, option2, option3, option4);

        //set event listeners
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
        submit.setOnClickListener(this);
        nextQ.setOnClickListener(this);

        return choicesView;
    }

    /***************This method detects the radioButton that is selected*/
    @Override
    public void onClick(View choicesView) {

        switch (choicesView.getId())
        {
            case R.id.option1_radioButton:
            {
                option1.setBackgroundColor(fetchColor(R.color.selected));;
                isOpt1 = true;
                unselected(option2, option3, option4);
                isFalse(isOpt2, isOpt3, isOpt4);
                break;
            }
            case R.id.option2_radioButton:
            {
                option2.setBackgroundColor(fetchColor(R.color.selected));;
                isOpt2 = true;
                unselected(option1, option3, option4);
                isFalse(isOpt1, isOpt3, isOpt4);
                break;
            }

            case R.id.option3_radioButton:
            {
                option3.setBackgroundColor(fetchColor(R.color.selected));;
                isOpt3 = true;
                unselected(option1, option2, option4);
                isFalse(isOpt1, isOpt2, isOpt4);
                break;
            }

            case R.id.option4_radioButton:
            {
                option4.setBackgroundColor(fetchColor(R.color.selected));;
                isOpt4 = true;
                unselected(option1, option2, option3);
                isFalse(isOpt1, isOpt2, isOpt3);
                break;
            }
            case R.id.submitButton:    //On answer submission
            {
                checkAnswer(choicesView);
                break;
            }
            case R.id.nextQuestionButton:
            {
                CreateQuiz.qNo += 1;/*Breakpoint this variable in each class*/
                Intent showNextQuery = new Intent(this, QuestionFragment.class);
                this.startActivity(showNextQuery);
            }

        }
    }

    /***************This method checks that an answer has been submitted and if the submitted one is correct.*/
    private void checkAnswer(View choicesView)
    {
        boolean nextQuestion = false;   //Validates the radioButton click

        if (isOpt1 = true)
        {
            if(option1.getText().equals(currentQuestion.getAnswer()))
            {
                correct(option1);
                correctAnswers += 1;
            }
            else
            {
                incorrect(option1);
                showAnswer(option2, option3, option4);
            }
            nextQuestion = true;
        }
        else if (isOpt2 = true)
        {
            if(option2.getText().equals(currentQuestion.getAnswer()))
            {
                correct(option2);
                correctAnswers += 1;
            }
            else
            {
                incorrect(option2);
                showAnswer(option1, option3, option4);
            }
            nextQuestion = true;
        }
        else if (isOpt3 = true)
        {
            if(option3.getText().equals(currentQuestion.getAnswer()))
            {
                correct(option3);
                correctAnswers += 1;
            }
            else
            {
                incorrect(option3);
                showAnswer(option1, option2, option4);
            }
            nextQuestion = true;
        }
        else if (isOpt4 = true)
        {
            if(option4.getText().equals(currentQuestion.getAnswer()))
            {
                correct(option1);
                correctAnswers += 1;
            }
            else
            {
                incorrect(option4);
                showAnswer(option1, option2, option3);
            }
            nextQuestion = true;
        }
        else
        {
            Snackbar reminder = Snackbar
                    .make(choicesView,"Choose an answer before moving on.", Snackbar.LENGTH_SHORT);
            reminder.show();
        }

        if(nextQuestion == true)
        {
            nextQ = submit;
            nextQ.setId(R.id.nextQuestionButton);
            nextQ.setText("Next Question");
            nextQ.setTextColor(fetchColor(R.color.colorAccent));
        }
    }

    /****************This method caches the answer options to be displayed*/
    public void getOptions(Question currentQuestion)
    {
        responses = currentQuestion.getOptions();   //Cache text strings
        this.currentQuestion = currentQuestion;
    }

    /****************This method sets the answer options on display*/
    private void showOptions(TextView question, RadioButton option1, RadioButton option2, RadioButton option3, RadioButton option4)
    {
        /**BEGIN***Variable Declaration*/
        Random randomNumber = new Random();
        int displayOrder;
        /**EMD***Variable Declaration*/

        //Set the question's text
        question.setText(currentQuestion.getQuestion());

        //Randomly select option text display order
        displayOrder = randomNumber.nextInt(3) + 1;

        switch(displayOrder)
        {
            case 1:
            {
                option1.setText(responses[0]);
                option2.setText(responses[1]);
                option3.setText(responses[2]);
                option4.setText(responses[3]);
                break;
            }
            case 2:
            {
                option1.setText(responses[3]);
                option2.setText(responses[0]);
                option3.setText(responses[1]);
                option4.setText(responses[2]);
                break;
            }
            case 3:
            {
                option1.setText(responses[2]);
                option2.setText(responses[3]);
                option3.setText(responses[0]);
                option4.setText(responses[1]);
                break;
            }
            case 4:
            {
                option1.setText(responses[1]);
                option2.setText(responses[2]);
                option3.setText(responses[3]);
                option4.setText(responses[0]);
                break;
            }
        }
    }

    /****************This method calls other methods to return a color*/
    private int fetchColor(int color)
    {
        return getResources().getColor(color);
    }

    /****************This method changes a radioButton's style to show that it is the correct answer*/
    private void correct(RadioButton element)
    {
        element.setBackgroundColor(fetchColor(R.color.colorPrimary));
        element.setTextColor(fetchColor(R.color.colorPrimaryDark));
    }

    /****************This method changes a radioButton's style to show that the selected answer is wrong*/
    private void incorrect(RadioButton element)
    {
        element.setBackgroundColor(fetchColor(R.color.wrongBackground));
        element.setTextColor(fetchColor(R.color.normal_text));
    }

    /****************This method changes a radioButton's style to show that the selected answer is wrong*/
    private void showAnswer(RadioButton opt1, RadioButton opt2, RadioButton opt3)
    {
        if(opt1.getText().equals(currentQuestion.getAnswer()))
        {
            correct(opt1);
        }
        else if(opt2.getText().equals(currentQuestion.getAnswer()))
        {
            correct(opt2);
        }
        else
        {
            correct(opt3);
        }
    }

    /****************This method changes a radioButton's style to show that the selected answer is wrong*/
    private void unselected(RadioButton element1, RadioButton element2, RadioButton element3)
    {
        element1.setBackgroundColor(fetchColor(R.color.colorAccent));
        element2.setBackgroundColor(fetchColor(R.color.colorAccent));
        element3.setBackgroundColor(fetchColor(R.color.colorAccent));
    }

    /****************This method changes the isOpt validator's to false.*/
    private void isFalse(boolean false1, boolean false2, boolean false3)
    {
        false1 = false;
        false2 = false;
        false3 = false;
    }
}
