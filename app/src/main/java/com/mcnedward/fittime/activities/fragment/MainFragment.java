package com.mcnedward.fittime.activities.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.activities.AddExerciseActivity;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.models.RepExercise;
import com.mcnedward.fittime.models.TimedExercise;
import com.mcnedward.fittime.views.ExerciseView;
import com.mcnedward.fittime.views.RepExerciseView;
import com.mcnedward.fittime.views.TimedExerciseView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public class MainFragment extends BaseFragment {
    private static final String TAG = MainFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AddExerciseActivity.class);
            startActivity(intent);
        });

        initialize(view);
        return view;
    }

    private void initialize(View view) {
        // TODO The list view should start out invisible
        view.findViewById(R.id.no_exercise_text).setVisibility(View.GONE);

        Exercise plank = new TimedExercise("Plank");
        Exercise pushUps = new RepExercise("Push-ups");
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(plank);
        exerciseList.add(pushUps);

        LinearLayout container = (LinearLayout) view.findViewById(R.id.container_exercises);
        container.setVisibility(View.VISIBLE);
        for (Exercise e : exerciseList) {
            ExerciseView exerciseView = null;
            if (e.getType() == Exercise.TIMED) {
                exerciseView = new TimedExerciseView(view.getContext(), e);
            } else if (e.getType() == Exercise.REP) {
                exerciseView = new RepExerciseView(view.getContext(), e);
            }
            container.addView(exerciseView);
        }
    }

}
