package com.not.androidace.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class QuestionService extends Service {
    private final IBinder mBinder = new QuestionBinder();
    private static final String TAG = "QuestionService";
    private JSONArray allQuestionsAndAnswers;
    private JSONArray questionSet = new JSONArray();
    private int numberOfCorrectResponses;

    public class QuestionBinder extends Binder {
        public QuestionService getService() {
            return QuestionService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            InputStream inputStream = getAssets().open("android_quiz.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            String json = new String(buffer, StandardCharsets.UTF_8);
            allQuestionsAndAnswers = new JSONArray(json);

            Log.i(TAG, "All questions loaded");
        } catch(Exception exception) {
            Log.e(TAG, "An error occurred while loading the questions:");
            throw new RuntimeException(exception);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "Start request received.");

        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.i(TAG, "Stopping service.");

        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    public void createQuestionSet() {
        Random randomNumberGenerator = new Random();
        try {
            switch (randomNumberGenerator.nextInt(4)) {
                case 0:
                    questionSet.put(0, allQuestionsAndAnswers.get(0));
                    questionSet.put(1, allQuestionsAndAnswers.get(1));
                    questionSet.put(2, allQuestionsAndAnswers.get(2));
                    questionSet.put(4, allQuestionsAndAnswers.get(3));
                    break;
                case 1:
                    questionSet.put(0, allQuestionsAndAnswers.get(4));
                    questionSet.put(1, allQuestionsAndAnswers.get(5));
                    questionSet.put(2, allQuestionsAndAnswers.get(6));
                    questionSet.put(4, allQuestionsAndAnswers.get(7));
                    break;
                case 2:
                    questionSet.put(0, allQuestionsAndAnswers.get(8));
                    questionSet.put(1, allQuestionsAndAnswers.get(9));
                    questionSet.put(2, allQuestionsAndAnswers.get(1));
                    questionSet.put(4, allQuestionsAndAnswers.get(2));
                    break;
                case 3:
                    questionSet.put(0, allQuestionsAndAnswers.get(0));
                    questionSet.put(1, allQuestionsAndAnswers.get(1));
                    questionSet.put(2, allQuestionsAndAnswers.get(6));
                    questionSet.put(4, allQuestionsAndAnswers.get(7));
                    break;
                default:
                    questionSet.put(0, allQuestionsAndAnswers.get(2));
                    questionSet.put(1, allQuestionsAndAnswers.get(3));
                    questionSet.put(2, allQuestionsAndAnswers.get(8));
                    questionSet.put(4, allQuestionsAndAnswers.get(4));
            }
            questionSet.put(3, allQuestionsAndAnswers.get(10));

            Log.i(TAG, "Questions successfully retrieved");
        } catch(Exception exception) {
            Log.e(TAG, "An error occurred while choosing the questions for this quiz.");
            throw new RuntimeException(exception);
        }
    }

    public JSONObject getQuestion(int questionNumber) {
        try {
            Log.i(TAG, "Request for question " + questionNumber + " received.");

            return questionSet.getJSONObject(questionNumber-1);
        } catch(Exception exception) {
            Log.e(TAG, "An error occurred while getting the next quiz question.");
            throw new RuntimeException(exception);
        }
    }

    public void markQuestion(int questionNumber, int responseNumber) {
        try {
            JSONObject currentQuestion = questionSet.getJSONObject(questionNumber-1);
            JSONArray options = currentQuestion.getJSONArray("options");

            if (currentQuestion.getString("answer").equals(options.getString(responseNumber))) {
                numberOfCorrectResponses += 1;
            }
            Log.i(TAG, "A question was marked.");
        } catch(Exception exception) {
            Log.e(TAG, "An error occurred while marking a question.");
            throw new RuntimeException(exception);
        }
    }

    public int getNumberOfCorrectResponses() {
        Log.i(TAG, "Quiz results ready");

        return numberOfCorrectResponses;
    }
}
