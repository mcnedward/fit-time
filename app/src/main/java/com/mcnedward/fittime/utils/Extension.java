package com.mcnedward.fittime.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.RippleDrawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import com.mcnedward.fittime.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public class Extension {
    private static final String TAG = Extension.class.getName();

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

    public static int tryGetInt(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            Log.w(TAG, "There was a problem formatting the input for the rep counter...", e);
            return 0;
        }
    }

    public static int tryGetInt(String value, int defaultValue) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            Log.w(TAG, "There was a problem formatting the input for the rep counter...", e);
            return defaultValue;
        }
    }

    public static String join(List<String> list, String delimiter) {
        StringBuilder sb = new StringBuilder();
        String loopDelimiter = "";
        for(String s : list) {
            sb.append(loopDelimiter);
            sb.append(s);
            loopDelimiter = delimiter;
        }
        return sb.toString();
    }

    public static <T> List<T> asList(SparseArray<T> sparseArray) {
        if (sparseArray == null) return null;
        List<T> arrayList = new ArrayList<T>(sparseArray.size());
        for (int i = 0; i < sparseArray.size(); i++)
            arrayList.add(sparseArray.valueAt(i));
        return arrayList;
    }
}
