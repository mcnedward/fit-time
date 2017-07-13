package com.mcnedward.fittime.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.adapter.ExerciseListAdapter;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.repositories.ExerciseRepository;
import com.mcnedward.fittime.repositories.IExerciseRepository;

import java.util.List;

/**
 * Created by Edward on 7/11/2017.
 */

public class MainView extends LinearLayout {

    private Context mContext;
    private LinearLayout mExerciseContainer;
    private TextView mNoExerciseTextView;
    private ExerciseListAdapter mListAdapter;

    public MainView(Context context) {
        super(context);
        initialize(context);
    }

    public MainView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        this.mContext = context;
        inflate(mContext, R.layout.view_main, this);
        IExerciseRepository mExerciseRepository = new ExerciseRepository(context);

        ListView listView = findViewById(R.id.list_exercises);
        mNoExerciseTextView = findViewById(R.id.text_no_exercises);

        List<Exercise> exercises = mExerciseRepository.getAll();
        mListAdapter = new ExerciseListAdapter(mContext, exercises);
        listView.setAdapter(mListAdapter);

//        if (!exercises.isEmpty()) {
//            for (Exercise exercise : exercises) {
//                ExerciseView exerciseView = null;
//                if (exercise.getType() == Exercise.TIMED) {
//                    exerciseView = new TimedExerciseView(context, exercise);
//                } else if (exercise.getType() == Exercise.REP) {
//                    exerciseView = new RepExerciseView(context, exercise);
//                }
//                mExerciseContainer.addView(exerciseView);
//            }
//        } else {
//            mNoExerciseTextView.setVisibility(VISIBLE);
//        }
    }

    public void updateExercises(Exercise exercise) {
        ExerciseView exerciseView = null;
        if (exercise.getType() == Exercise.TIMED) {
            exerciseView = new TimedExerciseView(mContext, exercise);
        } else if (exercise.getType() == Exercise.REP) {
            exerciseView = new RepExerciseView(mContext, exercise);
        }

        mListAdapter.addExercise(exercise);

        if (mNoExerciseTextView.getVisibility() == VISIBLE)
            mNoExerciseTextView.setVisibility(GONE);
    }

}
