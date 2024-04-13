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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.not.androidace.databinding.FragmentScoreBinding;
import com.not.androidace.service.QuestionService;

public class ScoreFragment extends Fragment {

    private static final String TAG = "ScoreFragment";
    private FragmentScoreBinding binding;
    private QuestionService mBoundQuestionService = null;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundQuestionService = ((QuestionService.QuestionBinder)service).getService();
            setScoreAndMessage();
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

        binding = FragmentScoreBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonTakeNewQuiz.setOnClickListener(v ->
            NavHostFragment.findNavController(ScoreFragment.this)
                    .navigate(R.id.FirstFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void setScoreAndMessage() {
        Integer finalScore = (Integer) mBoundQuestionService.getNumberOfCorrectResponses();
        binding.textviewScore.setText(finalScore.toString());

        Resources resources = getResources();
        String buttonText = finalScore <= 2
                ? resources.getString(R.string.button_quiz_try_again_label)
                : resources.getString(R.string.button_quiz_take_another_quiz_label);
        binding.buttonTakeNewQuiz.setText(buttonText);

        String[] resultsMessages = resources.getStringArray(R.array.results_messages_array);
        switch(finalScore) {
            case 3:
                binding.textviewScoreMessage.setText(resultsMessages[1]);
                break;
            case 4:
                binding.textviewScoreMessage.setText(resultsMessages[2]);
                break;
            case 5:
                binding.textviewScoreMessage.setText(resultsMessages[3]);
                break;
            default:
                binding.textviewScoreMessage.setText(resultsMessages[0]);
        }

    }
}
