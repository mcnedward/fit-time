package com.mcnedward.fittime.activities.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.adapter.ExerciseListAdapter;
import com.mcnedward.fittime.models.Exercise;

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

        Exercise plank = new Exercise("Plank", "Timed");
        List<Exercise> exerciseList = new ArrayList<>();
        exerciseList.add(plank);

        ExerciseListAdapter adapter = new ExerciseListAdapter(view.getContext(), exerciseList);
        ListView listView = (ListView) view.findViewById(R.id.list_exercise);
        listView.setAdapter(adapter);
        listView.setVisibility(View.VISIBLE);
    }

}
