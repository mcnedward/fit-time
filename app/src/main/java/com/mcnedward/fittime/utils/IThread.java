package com.mcnedward.fittime.utils;

/**
 * Created by Edward on 2/1/2017.
 */

public interface IThread {

    /**
     * Starts the thread and runs the start action.
     */
    void startThread();

    /**
     * Stops the thread and runs the stop action.
     */
    void stopThread();

    /**
     * Pause the thread.
     * @param pause
     */
    void pauseThread(boolean pause);

    boolean isRunning();
}
