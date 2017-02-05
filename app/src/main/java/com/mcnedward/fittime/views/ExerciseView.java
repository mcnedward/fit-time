package com.mcnedward.fittime.views;

import android.content.Context;
import android.content.Entity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.adapter.SetListAdapter;
import com.mcnedward.fittime.exceptions.EntityAlreadyExistsException;
import com.mcnedward.fittime.exceptions.EntityDoesNotExistException;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.models.Set;
import com.mcnedward.fittime.repositories.ExerciseRepository;
import com.mcnedward.fittime.repositories.SetRepository;

/**
 * Created by Edward on 2/4/2017.
 */

public abstract class ExerciseView extends LinearLayout {
    private static final String TAG = ExerciseView.class.getName();

    protected Context context;
    protected Exercise exercise;
    protected ExerciseRepository exerciseRepository;
    protected SetRepository setRepository;
    private TextView mNoRepsText;
    private SetListAdapter mAdapter;
    private RecyclerView mRepList;

    public ExerciseView(Context context, Exercise exercise) {
        super(context);
        this.exercise = exercise;
        initialize(context);
    }

    public ExerciseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    private void initialize(Context context) {
        this.context = context;
        inflate(context, R.layout.item_exercise, this);
        exerciseRepository = new ExerciseRepository(context);
        setRepository = new SetRepository(context);

        // Add the view for the specific type of exercise
        LinearLayout container = (LinearLayout) findViewById(R.id.container_exercise);
        inflate(context, getLayoutResource(), container);

        // Timer view should only be shown for timed exercises
        if (exercise.getType() == Exercise.TIMED)
            (findViewById(R.id.text_timer)).setVisibility(VISIBLE);
        TextView mName = (TextView) findViewById(R.id.text_name);
        mNoRepsText = (TextView) findViewById(R.id.text_no_reps);

        mAdapter = new SetListAdapter(context, exercise, this);
        mRepList = (RecyclerView) findViewById(R.id.list_reps);
        mRepList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mRepList.setAdapter(mAdapter);
        updateSetListVisible();

        mName.setText(exercise.getName());
    }

    public void onSetRemoved(Set set) {
        updateSetListVisible();
        try {
            setRepository.delete(set);
        } catch (EntityDoesNotExistException e) {
            Toast.makeText(context, "There was a problem when trying to save your set...", Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.getMessage(), e);
        }
    }

    protected void addSet(String value) {
        Set set = exercise.addSet(value);
        try {
            setRepository.save(set);
        } catch (EntityAlreadyExistsException e) {
            Toast.makeText(context, "There was a problem when trying to save your set...", Toast.LENGTH_SHORT).show();
            Log.e(TAG, e.getMessage(), e);
        }
        mAdapter.notifyDataSetChanged();
        updateSetListVisible();
    }

    private void updateSetListVisible() {
        if (exercise.getSets().size() <= 0) {
            mRepList.setVisibility(GONE);
            mNoRepsText.setVisibility(VISIBLE);
        } else {
            mRepList.setVisibility(VISIBLE);
            mNoRepsText.setVisibility(GONE);
        }
    }

    protected abstract int getLayoutResource();

}
