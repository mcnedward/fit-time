package com.mcnedward.fittime.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.adapter.ExerciseListAdapter;
import com.mcnedward.fittime.exceptions.EntityDoesNotExistException;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.models.History;
import com.mcnedward.fittime.models.WorkSet;
import com.mcnedward.fittime.repositories.exercise.ExerciseRepository;
import com.mcnedward.fittime.repositories.workSet.WorkSetRepository;
import com.mcnedward.fittime.utils.Dates;
import com.mcnedward.fittime.utils.Extension;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Edward on 7/11/2017.
 */

public class MainView extends LinearLayout {

    private Context mContext;
    private TextView mNoExerciseTextView;
    private Button mLogExercisesButton;
    private ExerciseListAdapter mListAdapter;
    private ExerciseRepository mExerciseRepository;
    private WorkSetRepository mWorkSetRepository;

    public MainView(Context context) {
        super(context);
        initialize(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        mContext = context;
        inflate(context, R.layout.view_main, this);
        mExerciseRepository = new ExerciseRepository(context);
        mWorkSetRepository = new WorkSetRepository(context);

        ListView listView = findViewById(R.id.list_exercises);
        mNoExerciseTextView = findViewById(R.id.text_no_exercises);
        mLogExercisesButton = findViewById(R.id.button_log_history);

        List<Exercise> exercises = mExerciseRepository.getAll();
        mListAdapter = new ExerciseListAdapter(context, exercises);
        listView.setAdapter(mListAdapter);

        mLogExercisesButton.setOnClickListener(v -> logExercises());
        mLogExercisesButton.setOnLongClickListener(v -> {
            getHistory();
            return true;
        });

        if (exercises.isEmpty()) {
            mNoExerciseTextView.setVisibility(VISIBLE);
            mLogExercisesButton.setVisibility(GONE);
        }
    }

    private void logExercises() {
        String historyDate = Dates.getDatabaseDateStamp();
        for (Exercise exercise : mListAdapter.getExercises()) {
            // Use the current date for each work set's history, then update
            for (WorkSet workSet : exercise.getWorkSets()) {
                workSet.setWorkDate(historyDate);
                try {
                    mWorkSetRepository.update(workSet);
                } catch (EntityDoesNotExistException e) {
                    e.printStackTrace();
                    // TODO Handle this?
                }
            }
            // Clear out the work sets for the day
            exercise.clearWorkSets();
        }
    }

    public void getHistory() {
        History history = mExerciseRepository.getHistoryForCurrentDate();
        List<String> eList = new ArrayList<>();
        for (Exercise exercise : history.getExercises()) {
            List<String> wList = exercise.getWorkSets().stream().map(WorkSet::toString).collect(Collectors.toList());
            String e = exercise.getName() + " Sets: " + Extension.join(wList, ", ");
            eList.add(e);
        }
        Toast.makeText(mContext, "Exercises: " + Extension.join(eList, "\n"), Toast.LENGTH_LONG).show();
    }

    public void updateExercises(Exercise exercise) {
        mListAdapter.addExercise(exercise);

        if (mNoExerciseTextView.getVisibility() == VISIBLE) {
            mNoExerciseTextView.setVisibility(GONE);
            mLogExercisesButton.setVisibility(VISIBLE);
        }
    }

}
