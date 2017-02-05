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
import com.mcnedward.fittime.models.Rep;

/**
 * Created by Edward on 2/4/2017.
 */

public abstract class ExerciseView extends LinearLayout {

    protected Context context;
    protected Exercise mExercise;
    private TextView mNoRepsText;
    private RecyclerView mRepList;
    private RepListAdapter mAdapter;

    public ExerciseView(Context context, Exercise exercise) {
        super(context);
        this.context = context;
        mExercise = exercise;
        initialize();
    }

    public ExerciseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    private void initialize() {
        inflate(context, R.layout.item_timed_exercise, this);
        mNoRepsText = (TextView) findViewById(R.id.text_no_reps);
        mAdapter = new RepListAdapter(context, mExercise, this);
        mRepList = (RecyclerView) findViewById(R.id.list_reps);
        mRepList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        mRepList.setAdapter(mAdapter);
    }

    public void onRepRemoved() {
        updateRepListVisible();
    }

    protected void addRep(Rep rep) {
        mExercise.addRep(rep);
        updateRepListVisible();
    }

    private void updateRepListVisible() {
        if (mExercise.getReps().size() <= 0) {
            mRepList.setVisibility(GONE);
            mNoRepsText.setVisibility(VISIBLE);
        } else {
            mRepList.setVisibility(VISIBLE);
            mNoRepsText.setVisibility(GONE);
        }
    }

}
