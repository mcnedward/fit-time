package com.mcnedward.fittime.views;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.adapter.RepListAdapter;
import com.mcnedward.fittime.adapter.ExerciseListAdapter;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.models.Rep;
import com.mcnedward.fittime.utils.Extension;
import com.mcnedward.fittime.utils.Timer;
import com.mcnedward.fittime.utils.TimerThread;

/**
 * Created by Edward on 2/1/2017.
 */

public class ExerciseView extends LinearLayout {

    private Context mContext;
    private Exercise mExercise;
    private Timer mTimer;
    private TextView mName;
    private TextView mTimerText;
    private TextView mNoRepsText;
    private RecyclerView mRepList;
    private ImageView mTimerButton;
    private ImageView mCheckButton;
    private RepListAdapter mAdapter;

    public ExerciseView(Context context, Exercise exercise) {
        super(context);
        mContext = context;
        mExercise = exercise;
        initialize();
    }

    public ExerciseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initialize();
    }

    private void initialize() {
        inflate(mContext, R.layout.item_timed_exercise, this);
        mTimer = new Timer();

        mName = (TextView) findViewById(R.id.text_name);
        mTimerText = (TextView) findViewById(R.id.text_timer);
        mNoRepsText = (TextView) findViewById(R.id.text_no_reps);

        mTimerButton = (ImageView) findViewById(R.id.button_timer);
        Extension.setRippleBackground(mContext, mTimerButton);
        mTimerButton.setOnClickListener(v -> toggleTimer());
        mCheckButton = (ImageView) findViewById(R.id.button_check);
        Extension.setRippleBackground(mContext, mCheckButton);
        mCheckButton.setOnClickListener(v -> finishRep());

        mAdapter = new RepListAdapter(mContext, mExercise);
        mRepList = (RecyclerView) findViewById(R.id.list_reps);
        mRepList.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mRepList.setAdapter(mAdapter);

        TimerThread mThread = new TimerThread(this);
        mThread.startThread();
    }

    private void toggleTimer() {
        if (!mTimer.isRunning()) {
            // Need to start the timer
            mTimer.start();
            mTimerButton.setImageResource(R.drawable.ic_replay_black_24dp);
            mCheckButton.setVisibility(VISIBLE);
        } else {
            // User wants to restart timer
            mTimer.restart();
        }
    }

    private void finishRep() {
        mTimer.stop();
        mTimerButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        mCheckButton.setVisibility(INVISIBLE);

        mNoRepsText.setVisibility(GONE);
        mRepList.setVisibility(VISIBLE);

        Rep rep = new Rep(mExercise.getReps().size() + 1, mTimer.getFormattedTime());
        mExercise.addRep(rep);
        mAdapter.notifyDataSetChanged();
    }

    /**
     * Runs on the TimerThread
     */
    public void run() {
        if (mTimer == null) return;
        String currentTime = mTimer.getFormattedTime();
        mTimerText.setText(currentTime);
    }

    public void update(ExerciseListAdapter adapter, Exercise exercise) {
        mExercise = exercise;
        mName.setText(mExercise.getName());
    }

}
