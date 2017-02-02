package com.mcnedward.fittime.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.mcnedward.fittime.R;

/**
 * Created by Edward on 2/1/2017.
 */

public class Extension {
    /**
     * Creates a new RippleDrawable for a ripple effect on a View.
     *
     * @param rippleColor     The color of the ripple.
     * @param backgroundColor The color of the background for the ripple. If this is 0, then there will be no background and the ripple effect will be circular.
     * @param context         The context.
     */
    public static void setRippleBackground(Context context, View view, int rippleColor, int backgroundColor) {
        view.setBackground(new RippleDrawable(
                new ColorStateList(
                        new int[][]
                                {
                                        new int[]{android.R.attr.state_window_focused},
                                },
                        new int[]
                                {
                                        ContextCompat.getColor(context, rippleColor),
                                }),
                backgroundColor == 0 ? null : new ColorDrawable(ContextCompat.getColor(context, backgroundColor)),
                null));
    }

    /**
     * Creates a new RippleDrawable for a ripple effect on a View. This will create a ripple with the default color of FireBrick for the ripple and GhostWhite for the background.
     *
     * @param context The context.
     */
    public static void setRippleBackground(Context context, View view) {
        setRippleBackground(context, view, R.color.FireBrick, R.color.GhostWhite);
    }
}
