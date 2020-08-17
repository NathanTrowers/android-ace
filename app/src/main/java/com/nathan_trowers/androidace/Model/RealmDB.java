package com.nathan_trowers.androidace.Model;

import android.app.Application;
import android.content.res.Resources;
import android.util.Log;

import com.nathan_trowers.androidace.R;

import java.io.InputStream;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmDB  extends Application {

    /*==================This class sets up database operations.==================*/
    @Override
    public void onCreate()
    {
        super.onCreate();
        Realm.init(this);

        RealmConfiguration AndroidQuiz = new RealmConfiguration.Builder()
                                                .name("AceQuiz.realm").build();

        Realm.setDefaultConfiguration(AndroidQuiz);

        //Import JSON file data
        importJsonDatabase(getResources());
    }

    private void importJsonDatabase(Resources resources)
    {
        //Connect to the Realm database
        Realm AceQuiz = Realm.getDefaultInstance();

        try
        {
            //Connect to the JSON file
            InputStream dataInflow = resources.openRawResource(R.raw.AndroidQuiz);
            //Transfer data from JSON file to Realm Database
            AceQuiz.createAllFromJson(Question.class, dataInflow);
            Log.d("Realm", "The JSON database import was successful.");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        finally
        {
            AceQuiz.close();
        }
    }

}
