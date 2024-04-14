package com.not.androidace;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.snackbar.Snackbar;
import com.not.androidace.databinding.FragmentQuestionFourBinding;
import com.not.androidace.service.QuestionService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

public class QuestionFourFragment extends Fragment {

    private FragmentQuestionFourBinding binding;
    private static final String TAG = "QuestionFourFragment";
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
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState
    ) {
        Context currentContext = this.getContext();
        if(!(currentContext.bindService(new Intent(currentContext, QuestionService.class),
                mConnection, Context.BIND_AUTO_CREATE))) {
            Log.e(TAG, "Error: The Question service either doesn't exist " +
                    "or is inaccessible.");
        }

        binding = FragmentQuestionFourBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonQuestion5Next.setOnClickListener(v ->
                moveToNextQuestion()
        );

        binding.imageViewQuestion4Option1.setOnClickListener(v ->
                markSelected(v, R.id.imageView_question_4_option_1, 0)
        );
        binding.imageViewQuestion4Option2.setOnClickListener(v ->
                markSelected(v, R.id.imageView_question_4_option_2, 1)
        );
        binding.imageViewQuestion4Option3.setOnClickListener(v ->
                markSelected(v, R.id.imageView_question_4_option_3, 2)
        );
        binding.imageViewQuestion4Option4.setOnClickListener(v ->
                markSelected(v, R.id.imageView_question_4_option_4, 3)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void moveToNextQuestion() {
        mBoundQuestionService.markQuestion(4, responseNumber);

        NavHostFragment.findNavController(QuestionFourFragment.this)
                .navigate(R.id.ScoreFragment); //set to question 5 instead
    }

    public void showOptions() {
        try {
            JSONObject questionOne = mBoundQuestionService.getQuestion(4);
            binding.textviewQuestion4.setText(questionOne.getString("question"));

            JSONArray options = questionOne.getJSONArray("options");
            byte[] optionOneBytes = Base64.decode(options.getString(0), Base64.DEFAULT);
            byte[] optionTwoBytes = Base64.decode(options.getString(1), Base64.DEFAULT);
            byte[] optionThreeBytes = Base64.decode(options.getString(2), Base64.DEFAULT);
            byte[] optionFourBytes = Base64.decode(options.getString(3), Base64.DEFAULT);
            Bitmap optionOneBitmap = BitmapFactory.decodeByteArray(optionOneBytes, 0, optionOneBytes.length);
            Bitmap optionTwoBitmap = BitmapFactory.decodeByteArray(optionTwoBytes, 0, optionTwoBytes.length);
            Bitmap optionThreeBitmap = BitmapFactory.decodeByteArray(optionThreeBytes, 0, optionThreeBytes.length);
            Bitmap optionFourBitmap = BitmapFactory.decodeByteArray(optionFourBytes, 0, optionFourBytes.length);
            binding.imageViewQuestion4Option1.setImageBitmap(optionOneBitmap);
            binding.imageViewQuestion4Option2.setImageBitmap(optionTwoBitmap);
            binding.imageViewQuestion4Option3.setImageBitmap(optionThreeBitmap);
            binding.imageViewQuestion4Option4.setImageBitmap(optionFourBitmap);
        } catch(Exception exception) {
            Snackbar.make(this.getView(), "Something went wrong while preparing this part of the quiz", Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.button_question_5_next)
                    .setAction("Action", null).show();

            Log.e(TAG, "Question set-up failed: " + exception);
        }
    }

    public void markSelected(View view, int imageId, int option) {
        ImageView image = view.findViewById(imageId);
        Resources resources = getResources();
        image.setBackgroundColor(resources.getColor(R.color.black, resources.newTheme()));

        int accentColor = resources.getColor(R.color.accentColor, resources.newTheme());

        if (imageId == R.id.imageView_question_4_option_1) {
            binding.imageViewQuestion4Option1.setBackgroundColor(accentColor);
            binding.imageViewQuestion4Option3.setBackgroundColor(accentColor);
            binding.imageViewQuestion4Option4.setBackgroundColor(accentColor);
        }
        if (imageId == R.id.imageView_question_4_option_2) {
            binding.imageViewQuestion4Option1.setBackgroundColor(accentColor);
            binding.imageViewQuestion4Option3.setBackgroundColor(accentColor);
            binding.imageViewQuestion4Option4.setBackgroundColor(accentColor);
        }
        if (imageId == R.id.imageView_question_4_option_3) {
            binding.imageViewQuestion4Option1.setBackgroundColor(accentColor);
            binding.imageViewQuestion4Option2.setBackgroundColor(accentColor);
            binding.imageViewQuestion4Option4.setBackgroundColor(accentColor);
        }
        if (imageId == R.id.imageView_question_4_option_4) {
            binding.imageViewQuestion4Option1.setBackgroundColor(accentColor);
            binding.imageViewQuestion4Option2.setBackgroundColor(accentColor);
            binding.imageViewQuestion4Option3.setBackgroundColor(accentColor);
        }

        responseNumber = option;
    }
}
