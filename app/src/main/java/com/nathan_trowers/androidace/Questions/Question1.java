package com.nathan_trowers.androidace.Questions;

import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;
import com.nathan_trowers.androidace.Model.Question;
import com.nathan_trowers.androidace.R;

import java.util.Random;

public class Question1 extends Fragment implements View.OnClickListener {
/**BEGIN***Variable Declaration*/
        private Button option1;     //Shows first displayed option
        private Button option2;     //Shows second displayed option
        private Button option3;     //Shows third displayed option
        private Button option4;     //Shows fourth displayed option
        private Button submit;      //Shows the submit button's listener
        private Button nextQ;//Shows the next question button's listener
        private String[] responses= new String[4];  //Cache answer options' text for question
        protected boolean isOpt1 = false;     //Show if option 1 is selected
        protected boolean isOpt2 = false;     //Show if option 2 is selected
        protected boolean isOpt3 = false;     //Show if option 3 is selected
        protected boolean isOpt4 = false;     //Show if option 4 is selected
        protected Question currentQuestion;   //Cache the question
        public int correctAnswers;         //Record the number of correct answers
/**END***Variable Declaration*/

    /****************This method calls the question's view and initiates listeners*/
   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View choicesView = inflater.inflate(R.layout.fragment_image_view_display, container, false);
//        View questionView = inflater.inflate(R.layout.fragment_image_view_display, container, false);

        return choicesView;
    }

    public void onViewCreated(@NonNull View choicesView, Bundle savedInstanceState) {
        super.onViewCreated(choicesView, savedInstanceState);

        //get element references
        option1 = (Button) choicesView.findViewById(R.id.option1_button);
        option2 = (Button) choicesView.findViewById(R.id.option2_button);
        option3 = (Button) choicesView.findViewById(R.id.option3_button);
        option4 = (Button) choicesView.findViewById(R.id.option4_button);
        submit = (Button) choicesView.findViewById(R.id.submitButton);
        nextQ = (Button) choicesView.findViewById(R.id.nextQuestionButton);

        //Change the displayed text on the option buttons
        showOptions(option1, option2, option3, option4);

        //set event listeners
        option1.setOnClickListener(this);
        option2.setOnClickListener(this);
        option3.setOnClickListener(this);
        option4.setOnClickListener(this);
        submit.setOnClickListener(this);
        nextQ.setOnClickListener(this);
    }

    /***************This method detects the button that is selected*/
    @Override
    public void onClick(View choicesView) {

        switch (choicesView.getId())
        {
            case R.id.option1_button:
            {
                option1.setBackgroundColor(fetchColor(R.color.selected));;
                isOpt1 = true;
                unselected(option2, option3, option4);
                isFalse(isOpt2, isOpt3, isOpt4);
                break;
            }

             case R.id.option2_button:
            {
                option2.setBackgroundColor(fetchColor(R.color.selected));;
                isOpt2 = true;
                unselected(option1, option3, option4);
                isFalse(isOpt1, isOpt3, isOpt4);
                break;
            }

             case R.id.option3_button:
            {
                option3.setBackgroundColor(fetchColor(R.color.selected));;
                isOpt3 = true;
                unselected(option1, option2, option4);
                isFalse(isOpt1, isOpt2, isOpt4);
                break;
            }

             case R.id.option4_button:
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
                Intent showNextQuery = new Intent(this.Question2.class);
                this.startActivity(showNextQuery);
            }


        }
    }


    /***************This method checks that an answer has been submitted and if the submitted one is correct.*/
    private void checkAnswer(View choicesView)
    {
        boolean nextQuestion = false;   //Validates the button click

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
    private void showOptions(Button option1, Button option2, Button option3, Button option4)
    {
        /**BEGIN***Variable Declaration*/
        Random randomNumber = new Random();
        int displayOrder;
        /**EMD***Variable Declaration*/

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

    /****************This method changes a button's style to show that it is the correct answer*/
    private void correct(Button element)
    {
        element.setBackgroundColor(fetchColor(R.color.colorPrimary));
        element.setTextColor(fetchColor(R.color.colorPrimaryDark));
    }

    /****************This method changes a button's style to show that the selected answer is wrong*/
    private void incorrect(Button element)
    {
        element.setBackgroundColor(fetchColor(R.color.wrongBackground));
        element.setTextColor(fetchColor(R.color.normal_text));
    }

    /****************This method changes a button's style to show that the selected answer is wrong*/
    private void showAnswer(Button opt1, Button opt2, Button opt3)
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

    /****************This method changes a button's style to show that the selected answer is wrong*/
    private void unselected(Button element1, Button element2, Button element3)
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
