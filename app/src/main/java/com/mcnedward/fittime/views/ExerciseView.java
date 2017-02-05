package com.mcnedward.fittime.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.adapter.RepListAdapter;
import com.mcnedward.fittime.models.Exercise;

/**
 * Created by Edward on 2/4/2017.
 */

public abstract class ExerciseView extends LinearLayout {

    protected Context context;
    protected Exercise exercise;
    private TextView mNoRepsText;
    private RepListAdapter mAdapter;
    private RecyclerView mRepList;

    public ExerciseView(Context context, Exercise exercise) {
        super(context);
        this.context = context;
        this.exercise = exercise;
        initialize();
    }

    public ExerciseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    private void initialize() {
        inflate(context, R.layout.item_exercise, this);

        // Add the view for the specific type of exercise
        LinearLayout container = (LinearLayout) findViewById(R.id.container_exercise);
        inflate(context, getLayoutResource(), container);

        // Timer view should only be shown for timed exercises
        if (exercise.getType() == Exercise.TIMED)
            (findViewById(R.id.text_timer)).setVisibility(VISIBLE);
        TextView mName = (TextView) findViewById(R.id.text_name);
        mNoRepsText = (TextView) findViewById(R.id.text_no_reps);
        mAdapter = new RepListAdapter(context, exercise, this);
        mRepList = (RecyclerView) findViewById(R.id.list_reps);
        mRepList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mRepList.setAdapter(mAdapter);

        mName.setText(exercise.getName());
    }

    public void onRepRemoved() {
        updateRepListVisible();
    }

    protected void addRep(String value) {
        exercise.addSet(value);
        mAdapter.notifyDataSetChanged();
        updateRepListVisible();
    }

    private void updateRepListVisible() {
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
