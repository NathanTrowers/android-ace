package com.nathan_trowers.androidace;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class QuestionFragment extends Fragment{

    @Override
    public View onCreateView(@Nullable
            LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        //Get the view to display

        View questionView = inflater.inflate(R.layout.fragment_question, container, false);

        return questionView;
    }

    ////////////////////////////TAke care of this after the options
    public void questionPlacement()
    {

    }
//    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//
//    }

}