package com.mcnedward.fittime.utils;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * Created by Edward on 2/1/2017.
 */

public class Timer {

    private long mStartTime;
    private long mStopTime;
    private boolean mRunning;

    public Timer() {

    }

    public void start() {
        mStartTime = System.currentTimeMillis();
        mRunning = true;
    }

    public void stop() {
        mStopTime = System.currentTimeMillis();
        mRunning = false;
    }

    public void restart() {
        mStopTime = 0;
        start();
    }

    public void reset() {
        mStartTime = 0;
        mStopTime = 0;
        mRunning = false;
    }

    public boolean isRunning() {
        return mRunning;
    }

    public long getElapsedTime() {
        if (mRunning) {
            return System.currentTimeMillis() - mStartTime;
        }
        return mStopTime - mStartTime;
    }

    public String getFormattedTime() {
        long elapsed = getElapsedTime();
        if (elapsed <= 0) return "00:00:00";
        long minutes = TimeUnit.MILLISECONDS.toMinutes(elapsed) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(elapsed) % 60;
        long milliseconds = (elapsed / 10) % 100;
        return String.format("%02d:%02d:%02d", minutes, seconds, milliseconds);
    }

}
