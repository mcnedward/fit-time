package com.mcnedward.fittime.utils;

import android.util.Log;

import com.mcnedward.fittime.listeners.UIThreadListener;

/**
 * Created by Edward on 2/1/2017.
 */

public class TimerThread extends Thread implements IThread {
    private static final String TAG = TimerThread.class.getName();

    private boolean mStarted, mRunning, mPaused;
    private UIThreadListener mListener;

    public TimerThread(UIThreadListener listener) {
        super("Timer Thread");
        mListener = listener;
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
                if (mPaused) continue;
                if (mListener == null || mListener.getHandler() == null) continue;
                mListener.getHandler().post(() -> {
                    mListener.run();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (Exception e) {
                Log.e(TAG, "Something went wrong in the TimerThread...", e);
            }
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
     *
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
