package com.mcnedward.fittime.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcnedward.fittime.R;
import com.mcnedward.fittime.listeners.UIThreadListener;
import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.models.Set;
import com.mcnedward.fittime.utils.Extension;
import com.mcnedward.fittime.utils.Timer;
import com.mcnedward.fittime.utils.TimerThread;

/**
 * Created by Edward on 2/1/2017.
 */

public class TimedExerciseView extends ExerciseView implements UIThreadListener {

    private Timer mTimer;
    private TextView mTimerText;
    private ImageView mTimerButton;
    private ImageView mCheckButton;
    private ImageView mStopButton;

    public TimedExerciseView(Context context, Exercise exercise) {
        super(context, exercise);
        initialize();
    }

    public TimedExerciseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    private void initialize() {
        mTimer = new Timer();

        mTimerText = (TextView) findViewById(R.id.text_timer);
        mTimerButton = (ImageView) findViewById(R.id.button_timer);
        Extension.setRippleBackground(context, mTimerButton);
        mTimerButton.setOnClickListener(v -> toggleTimer());
        mCheckButton = (ImageView) findViewById(R.id.button_check);
        Extension.setRippleBackground(context, mCheckButton);
        mCheckButton.setOnClickListener(v -> finishRep());
        mStopButton = (ImageView) findViewById(R.id.button_stop);
        Extension.setRippleBackground(context, mStopButton);
        mStopButton.setOnClickListener(v -> resetRep());

        TimerThread mThread = new TimerThread(this);
        mThread.startThread();
    }

    private void toggleTimer() {
        if (!mTimer.isRunning()) {
            // Need to start the timer
            mTimer.start();
            mTimerButton.setImageResource(R.drawable.ic_replay_black_24dp);
            mCheckButton.setVisibility(VISIBLE);
            mStopButton.setVisibility(VISIBLE);
        } else {
            // User wants to restart timer
            mTimer.restart();
        }
    }

    private void finishRep() {
        mTimer.stop();

        mTimerButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        mCheckButton.setVisibility(INVISIBLE);
        mStopButton.setVisibility(INVISIBLE);

        addRep(mTimer.getFormattedTime());
    }

    private void resetRep() {
        mTimer.reset();
        mTimerButton.setImageResource(R.drawable.ic_play_arrow_black_24dp);
        mCheckButton.setVisibility(INVISIBLE);
        mStopButton.setVisibility(INVISIBLE);
    }

    /**
     * Runs on the TimerThread
     */
    public void run() {
        if (mTimer == null) return;
        String currentTime = mTimer.getFormattedTime();
        mTimerText.setText(currentTime);
    }

    protected int getLayoutResource() {
        return R.layout.item_timed_exercise;
    }

}
