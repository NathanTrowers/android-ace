package com.not.androidace;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.not.androidace.databinding.FragmentSecondBinding;
import com.not.androidace.service.QuestionService;

import org.json.JSONArray;
import org.json.JSONObject;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private static final String TAG = "SecondFragment";
    private QuestionService mBoundQuestionService = null;
    private int responseNumber = 0;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundQuestionService = ((QuestionService.QuestionBinder)service).getService();
            mBoundQuestionService.createQuestionSet();
            showOptions();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundQuestionService = null;
        }
    };

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Context currentContext = this.getContext();
        if(!(currentContext.bindService(new Intent(currentContext, QuestionService.class),
                mConnection, Context.BIND_AUTO_CREATE))) {
            Log.e(TAG, "Error: The Question service either doesn't exist " +
                    "or is inaccessible.");
        }

        binding = FragmentSecondBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonQuestion2Next.setOnClickListener(v ->
            moveToNextQuestion()
        );

        binding.buttonQuestion1Option1.setOnClickListener(v ->
            markSelected(v, R.id.button_question_1_option_1, 0)
        );
        binding.buttonQuestion1Option2.setOnClickListener(v ->
            markSelected(v, R.id.button_question_1_option_2, 1)
        );
        binding.buttonQuestion1Option3.setOnClickListener(v ->
            markSelected(v, R.id.button_question_1_option_3, 2)
        );
        binding.buttonQuestion1Option4.setOnClickListener(v ->
            markSelected(v, R.id.button_question_1_option_4, 3)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void moveToNextQuestion() {
        mBoundQuestionService.markQuestion(1, responseNumber);

        NavHostFragment.findNavController(SecondFragment.this)
                .navigate(R.id.ScoreFragment);//set to question 2 instead
    }

    public void showOptions() {
        try {
            JSONObject questionOne = mBoundQuestionService.getQuestion(1);
            binding.textviewQuestion1.setText(questionOne.getString("question"));

            JSONArray options = questionOne.getJSONArray("options");
            binding.buttonQuestion1Option1.setText(options.getString(0));
            binding.buttonQuestion1Option2.setText(options.getString(1));
            binding.buttonQuestion1Option3.setText(options.getString(2));
            binding.buttonQuestion1Option4.setText(options.getString(3));

        } catch(Exception exception) {
            Snackbar.make(this.getView(), "Something went wrong while preparing this part of the quiz", Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.button_question_2_next)
                    .setAction("Action", null).show();

            Log.e(TAG, "Question set-up failed: " + exception);
        }
    }

    public void markSelected(View view, int buttonId, int option) {
        Button button = view.findViewById(buttonId);
        Resources resources = getResources();
        button.setBackgroundColor(resources.getColor(R.color.black, resources.newTheme()));

        int primaryColor = resources.getColor(R.color.primaryColor, resources.newTheme());

        if (buttonId == R.id.button_question_1_option_1) {
            binding.buttonQuestion1Option2.setBackgroundColor(primaryColor);
            binding.buttonQuestion1Option3.setBackgroundColor(primaryColor);
            binding.buttonQuestion1Option4.setBackgroundColor(primaryColor);
        }
        if (buttonId == R.id.button_question_1_option_2) {
            binding.buttonQuestion1Option1.setBackgroundColor(primaryColor);
            binding.buttonQuestion1Option3.setBackgroundColor(primaryColor);
            binding.buttonQuestion1Option4.setBackgroundColor(primaryColor);
        }
        if (buttonId == R.id.button_question_1_option_3) {
            binding.buttonQuestion1Option1.setBackgroundColor(primaryColor);
            binding.buttonQuestion1Option2.setBackgroundColor(primaryColor);
            binding.buttonQuestion1Option4.setBackgroundColor(primaryColor);
        }
        if (buttonId == R.id.button_question_1_option_4) {
            binding.buttonQuestion1Option1.setBackgroundColor(primaryColor);
            binding.buttonQuestion1Option2.setBackgroundColor(primaryColor);
            binding.buttonQuestion1Option3.setBackgroundColor(primaryColor);
        }

        responseNumber = option;
    }
}
