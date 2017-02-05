package com.mcnedward.fittime.listeners;

import android.os.Handler;

/**
 * A listener for updating a UI from a Thread.
 * Created by Edward on 2/4/2017.
 */
public interface UIThreadListener {
    /**
     * Runs every frame of the Thread.
     */
    void run();

    /**
     * Returns the Handler for the UI.
     * @return
     */
    Handler getHandler();
}
