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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.not.androidace.databinding.FragmentOptionListBinding;
import com.not.androidace.placeholder.PlaceholderContent;
import com.not.androidace.service.QuestionService;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 *
 * Get Data and send  it to the adaptor
 * get selected response number and send data to QuestionService for marking
 */
public class OptionFragment extends Fragment {

    public static int responseNumber;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "OptionFragment";
    private FragmentOptionListBinding binding;
    private QuestionService mBoundQuestionService = null;
    private int mColumnCount = 1;
    private MyOptionRecyclerViewAdapter myOptionRecyclerViewAdapter;
    private List<PlaceholderContent.PlaceholderItem> items = new ArrayList<PlaceholderContent.PlaceholderItem>();

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundQuestionService = ((QuestionService.QuestionBinder)service).getService();
            fetchOptions();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBoundQuestionService = null;
        }
    };

    @SuppressWarnings("unused")
    public static OptionFragment newInstance(int columnCount) {
        OptionFragment fragment = new OptionFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);

        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OptionFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

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

        myOptionRecyclerViewAdapter = new MyOptionRecyclerViewAdapter(items);

        binding = FragmentOptionListBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonScoreNext.setOnClickListener(v ->
                moveToResults()
        );

        myOptionRecyclerViewAdapter.setOnItemClickListener((int position) -> myOptionRecyclerViewAdapter.setSelectedPosition(position));
    }
    public void moveToResults() {
        mBoundQuestionService.markQuestion(5, responseNumber);

        NavHostFragment.findNavController(OptionFragment.this)
                .navigate(R.id.ScoreFragment);
    }

    public void fetchOptions() {
        try {
            JSONObject questionOne = mBoundQuestionService.getQuestion(5);
            binding.textviewQuestion5.setText(questionOne.getString("question"));

            JSONArray options = questionOne.getJSONArray("options");
            items.add(0,
                    new PlaceholderContent.PlaceholderItem("0", options.getString(0), "")
            );
            items.add(1,
                    new PlaceholderContent.PlaceholderItem("1", options.getString(1), "")
            );
            items.add(2,
                    new PlaceholderContent.PlaceholderItem("2", options.getString(2), "")
            );
            items.add(3,
                    new PlaceholderContent.PlaceholderItem("3", options.getString(3), "")
            );

            View view = binding.listQuestionFiveOptions;
            // Set the adapter
            if (view instanceof RecyclerView) {
                Context context = view.getContext();
                RecyclerView recyclerView = (RecyclerView) view;
                if (mColumnCount <= 1) {
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                } else {
                    recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
                }

                recyclerView.setAdapter(myOptionRecyclerViewAdapter);
            }
        } catch(Exception exception) {
            Snackbar.make(this.getView(), "Something went wrong while preparing this part of the quiz", Snackbar.LENGTH_LONG)
                    .setAnchorView(R.id.button_question_2_next)
                    .setAction("Action", null).show();

            Log.e(TAG, "Question set-up failed: " + exception);
        }
    }
}
