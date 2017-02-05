package com.mcnedward.fittime.activities.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.activities.AddExerciseActivity;
import com.mcnedward.fittime.exceptions.EntityAlreadyExistsException;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.repositories.ExerciseRepository;
import com.mcnedward.fittime.repositories.IExerciseRepository;
import com.mcnedward.fittime.views.AddExerciseView;
import com.mcnedward.fittime.views.ExerciseView;
import com.mcnedward.fittime.views.RepExerciseView;
import com.mcnedward.fittime.views.TimedExerciseView;

import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public class MainFragment extends BaseFragment {
    private static final String TAG = MainFragment.class.getName();

    private IExerciseRepository mExerciseRepository;
    private LinearLayout mExerciseContainer;
    private AddExerciseView mAddExerciseView;
    private int mExerciseCount;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mExerciseRepository = new ExerciseRepository(view.getContext());
        initialize(view);
        return view;
    }

    private void initialize(View view) {
        mExerciseContainer = (LinearLayout) view.findViewById(R.id.container_exercises);

        List<Exercise> exercises = mExerciseRepository.getAll();
        mExerciseCount = exercises.size();
        if (!exercises.isEmpty()) {

            for (Exercise exercise : exercises) {
                ExerciseView exerciseView = null;
                if (exercise.getType() == Exercise.TIMED) {
                    exerciseView = new TimedExerciseView(view.getContext(), exercise);
                } else if (exercise.getType() == Exercise.REP) {
                    exerciseView = new RepExerciseView(view.getContext(), exercise);
                }
                mExerciseContainer.addView(exerciseView);
            }
        }
        if (mExerciseCount < 5) {
            mAddExerciseView = new AddExerciseView(view.getContext(), this);
            mExerciseContainer.addView(mAddExerciseView);
        }
    }

    public void addExerciseToView(Exercise exercise) {
        try {
            mExerciseRepository.save(exercise);
        } catch (EntityAlreadyExistsException e) {
            Toast.makeText(getContext(), "That exercise is already added!", Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.getMessage(), e);
            return;
        }
        mExerciseContainer.removeView(mAddExerciseView);
        if (exercise.getType() == Exercise.TIMED) {
            mExerciseContainer.addView(new TimedExerciseView(getContext(), exercise));
        } else if (exercise.getType() == Exercise.REP) {
            mExerciseContainer.addView(new RepExerciseView(getContext(), exercise));
        }
        mExerciseCount++;
        if (mExerciseCount >= 5) return;
        mExerciseContainer.addView(mAddExerciseView);
    }

}
