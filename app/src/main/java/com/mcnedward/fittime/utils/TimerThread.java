package com.mcnedward.fittime.utils;

import android.util.Log;

import com.mcnedward.fittime.views.ExerciseView;

/**
 * Created by Edward on 2/1/2017.
 */

public class TimerThread extends Thread implements IThread {
    private static final String TAG = TimerThread.class.getName();

    private boolean mStarted, mRunning, mPaused;
    private ExerciseView mExerciseView;

    public TimerThread(ExerciseView exerciseView) {
        super("Timer Thread");
        mExerciseView = exerciseView;
        mStarted = false;
        mRunning = false;
    }

    /**
     * Run the thread.
     */
    @Override
    public void run() {
        while (mRunning) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (mPaused) continue;
            if (mExerciseView == null || mExerciseView.getHandler() == null) return;
            mExerciseView.getHandler().post(() -> {
                    mExerciseView.run();
            });
        }
    }

    /**
     * Start the thread.
     */
    @Override
    public void start() {
        mStarted = true;
        super.start();
    }

    /**
     * Starts the thread and runs the abstract start action.
     */
    @Override
    public void startThread() {
        Log.d(TAG, "Starting " + getName() + " thread!");
        if (!mStarted)
            start();
        mRunning = true;
    }

    /**
     * Stops the thread and runs the abstract stop action.
     */
    @Override
    public void stopThread() {
        Log.d(TAG, "Stopping " + getName() + " thread!");
        mRunning = false;
        boolean retry = true;
        while (retry) {
            try {
                join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Pause the thread.
     * @param pause
     */
    @Override
    public void pauseThread(boolean pause) {
        mPaused = pause;
    }

    @Override
    public boolean isRunning() {
        return mRunning;
    }

}
