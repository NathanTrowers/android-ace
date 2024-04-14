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
import com.not.androidace.databinding.FragmentQuestionThreeBinding;
import com.not.androidace.service.QuestionService;

import org.json.JSONArray;
import org.json.JSONObject;

public class QuestionThreeFragment extends Fragment {

    private FragmentQuestionThreeBinding binding;
    private static final String TAG = "QuestionThreeFragment";
    private QuestionService mBoundQuestionService = null;
    private int responseNumber = 0;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundQuestionService = ((QuestionService.QuestionBinder)service).getService();
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

        binding =  FragmentQuestionThreeBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonQuestion4Next.setOnClickListener(v ->
                moveToNextQuestion()
        );

        binding.radioButtonQuestion3Option1.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) ->
                markSelected(compoundButton, isChecked, R.id.checkBox_question_2_option_1, 0)
        );
        binding.radioButtonQuestion3Option2.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) ->
                markSelected(compoundButton, isChecked, R.id.checkBox_question_2_option_2, 1)
        );
        binding.radioButtonQuestion3Option3.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) ->
                markSelected(compoundButton, isChecked, R.id.checkBox_question_2_option_3, 2)
        );
        binding.radioButtonQuestion3Option4.setOnCheckedChangeListener((CompoundButton compoundButton, boolean isChecked) ->
                markSelected(compoundButton, isChecked, R.id.checkBox_question_2_option_4, 3)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void moveToNextQuestion() {
        mBoundQuestionService.markQuestion(3, responseNumber);

        NavHostFragment.findNavController(QuestionThreeFragment.this)
                .navigate(R.id.ScoreFragment);//set to question 4 instead
    }

    public void showOptions() {
        try {
            JSONObject questionOne = mBoundQuestionService.getQuestion(3);
            binding.textviewQuestion3.setText(questionOne.getString("question"));

            JSONArray options = questionOne.getJSONArray("options");
            binding.radioButtonQuestion3Option1.setText(options.getString(0));
            binding.radioButtonQuestion3Option2.setText(options.getString(1));
            binding.radioButtonQuestion3Option3.setText(options.getString(2));
            binding.radioButtonQuestion3Option4.setText(options.getString(3));

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
            binding.radioButtonQuestion3Option1.setChecked(isChecked);
            binding.radioButtonQuestion3Option2.setChecked(false);
            binding.radioButtonQuestion3Option3.setChecked(false);
            binding.radioButtonQuestion3Option4.setChecked(false);
        }
        if (checkBoxId == R.id.checkBox_question_2_option_2) {
            binding.radioButtonQuestion3Option1.setChecked(false);
            binding.radioButtonQuestion3Option2.setChecked(isChecked);
            binding.radioButtonQuestion3Option3.setChecked(false);
            binding.radioButtonQuestion3Option4.setChecked(false);
        }
        if (checkBoxId == R.id.checkBox_question_2_option_3) {
            binding.radioButtonQuestion3Option1.setChecked(false);
            binding.radioButtonQuestion3Option2.setChecked(false);
            binding.radioButtonQuestion3Option3.setChecked(isChecked);
            binding.radioButtonQuestion3Option4.setChecked(false);
        }
        if (checkBoxId == R.id.checkBox_question_2_option_4) {
            binding.radioButtonQuestion3Option1.setChecked(false);
            binding.radioButtonQuestion3Option2.setChecked(false);
            binding.radioButtonQuestion3Option3.setChecked(false);
            binding.radioButtonQuestion3Option4.setChecked(isChecked);
        }

        responseNumber = option;
    }
}
