package com.not.androidace;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.not.androidace.databinding.FragmentQuestionTwoBinding;
import com.not.androidace.service.QuestionService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class QuestionTwoFragment extends Fragment {

    private FragmentQuestionTwoBinding binding;
    private static final String TAG = "QuestionThreeFragment";
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
            @NotNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        Context currentContext = this.getContext();
        if(!(currentContext.bindService(new Intent(currentContext, QuestionService.class),
                mConnection, Context.BIND_AUTO_CREATE))) {
            Log.e(TAG, "Error: The Question service either doesn't exist " +
                    "or is inaccessible.");
        }

        binding = FragmentQuestionTwoBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonQuestion3Next.setOnClickListener(v ->
                moveToNextQuestion()
        );

        binding.checkBoxQuestion2Option1.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) ->
                markSelected(compoundButton, isChecked, R.id.checkBox_question_2_option_1, 0)
        );
        binding.checkBoxQuestion2Option2.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) ->
                markSelected(compoundButton, isChecked, R.id.checkBox_question_2_option_2, 1)
        );
        binding.checkBoxQuestion2Option3.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) ->
                markSelected(compoundButton, isChecked, R.id.checkBox_question_2_option_3, 2)
        );
        binding.checkBoxQuestion2Option4.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) ->
                markSelected(compoundButton, isChecked, R.id.checkBox_question_2_option_4, 3)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void moveToNextQuestion() {
        mBoundQuestionService.markQuestion(2, responseNumber);

        NavHostFragment.findNavController(QuestionTwoFragment.this)
                .navigate(R.id.ScoreFragment);//set to question 3 instead
    }

    public void showOptions() {
        try {
            JSONObject questionOne = mBoundQuestionService.getQuestion(2);
            binding.textviewQuestion2.setText(questionOne.getString("question"));

            JSONArray options = questionOne.getJSONArray("options");
            binding.checkBoxQuestion2Option1.setText(options.getString(0));
            binding.checkBoxQuestion2Option2.setText(options.getString(1));
            binding.checkBoxQuestion2Option3.setText(options.getString(2));
            binding.checkBoxQuestion2Option4.setText(options.getString(3));

        } catch(Exception exception) {
            Snackbar.make(this.getView(), "Something went wrong while preparing this part of the quiz", Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.button_question_2_next)
                    .setAction("Action", null).show();

            Log.e(TAG, "Question set-up failed: " + exception);
        }
    }

    public void markSelected(CompoundButton compoundButton, boolean isChecked, int checkBoxId,
                             int option
    ) {
        if (checkBoxId == R.id.checkBox_question_2_option_1) {
            binding.checkBoxQuestion2Option1.setChecked(isChecked);
            binding.checkBoxQuestion2Option2.setChecked(false);
            binding.checkBoxQuestion2Option3.setChecked(false);
            binding.checkBoxQuestion2Option4.setChecked(false);
        }
        if (checkBoxId == R.id.checkBox_question_2_option_2) {
            binding.checkBoxQuestion2Option1.setChecked(false);
            binding.checkBoxQuestion2Option2.setChecked(isChecked);
            binding.checkBoxQuestion2Option3.setChecked(false);
            binding.checkBoxQuestion2Option4.setChecked(false);
        }
        if (checkBoxId == R.id.checkBox_question_2_option_3) {
            binding.checkBoxQuestion2Option1.setChecked(false);
            binding.checkBoxQuestion2Option2.setChecked(false);
            binding.checkBoxQuestion2Option3.setChecked(isChecked);
            binding.checkBoxQuestion2Option4.setChecked(false);
        }
        if (checkBoxId == R.id.checkBox_question_2_option_4) {
            binding.checkBoxQuestion2Option1.setChecked(false);
            binding.checkBoxQuestion2Option2.setChecked(false);
            binding.checkBoxQuestion2Option3.setChecked(false);
            binding.checkBoxQuestion2Option4.setChecked(isChecked);
        }

        responseNumber = option;
    }
}
