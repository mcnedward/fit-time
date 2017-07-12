package com.mcnedward.fittime.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.repositories.ExerciseRepository;
import com.mcnedward.fittime.repositories.IExerciseRepository;

import java.util.List;

/**
 * Created by Edward on 7/11/2017.
 */

public class MainView extends LinearLayout {

    private Context mContext;
    private IExerciseRepository mExerciseRepository;
    private LinearLayout mExerciseContainer;

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
        mExerciseRepository = new ExerciseRepository(context);

        mExerciseContainer = (LinearLayout) findViewById(R.id.container_exercises);

        List<Exercise> exercises = mExerciseRepository.getAll();
        if (!exercises.isEmpty()) {

            for (Exercise exercise : exercises) {
                ExerciseView exerciseView = null;
                if (exercise.getType() == Exercise.TIMED) {
                    exerciseView = new TimedExerciseView(context, exercise);
                } else if (exercise.getType() == Exercise.REP) {
                    exerciseView = new RepExerciseView(context, exercise);
                }
                mExerciseContainer.addView(exerciseView);
            }
        }
    }

}
