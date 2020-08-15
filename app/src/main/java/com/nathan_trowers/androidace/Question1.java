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
        private Question question1; //Question 1
/**END***Variable Declaration*/

/*BEGIN***Getters*/
    public Button getOption1() {
        return option1;
    }

    public Button getOption2() {
        return option2;
    }

    public Button getOption3() {
        return option3;
    }

    public Button getOption4() {
        return option4;
    }
/*END***Getters*/

    /****************This method calls the question's view and initiates listeners*/
   @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_image_view_display, container, false);

        //get element references
        option1 = (Button) view.findViewById(R.id.);
    }
}
