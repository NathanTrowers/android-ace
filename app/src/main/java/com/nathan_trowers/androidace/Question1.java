package com.nathan_trowers.androidace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.nathan_trowers.androidace.Model.Question;

import java.util.ArrayList;

public class Question1 extends Fragment {
/**BEGIN***Variable Declaration*/
        private Button option1;     //Variable to cache first displayed option
        private Button option2;     //Variable to cache second displayed option
        private Button option3;     //Variable to cache third displayed option
        private Button option4;     //Variable to cache fourth displayed option
        private Button submit;      //Variable to cache the submit button's listener
        private Question question1; //Question 1
/**END***Variable Declaration*/

/*BEGIN***Setters*/
    public void setOption1(Button option1) {
        this.option1 = option1;
    }

    public void setOption2(Button option2) {
        this.option2 = option2;
    }

    public void setOption3(Button option3) {
        this.option3 = option3;
    }

    public void setOption4(Button option4) {
        this.option4 = option4;
    }

    public void setSubmit(Button submit) {
        this.submit = submit;
    }
/*END***Setters*/

    /****************This method calls the question's view and initiates listeners*/
   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View choicesView = inflater.inflate(R.layout.fragment_image_view_display, container, false);
        View questionView = inflater.inflate(R.layout.fragment_image_view_display, container, false);

        //get element references
        option1 = (Button) choicesView.findViewById(R.id.option1_button);
        option2 = (Button) choicesView.findViewById(R.id.option2_button);
        option3 = (Button) choicesView.findViewById(R.id.option3_button);
        option4 = (Button) choicesView.findViewById(R.id.option4_button);

    }

    /***************This method detects the answer that is selected*/
    /***************This method detects when the answer is submitted*/
}
