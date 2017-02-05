package com.mcnedward.fittime.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public abstract class Exercise extends BaseEntity {

    private String mName;
    private int mType;
    private List<Set> mSets;

    @IntDef({TIMED, REP})
    @Retention(RetentionPolicy.SOURCE)
    @interface ExerciseType {}
    public static final int TIMED = 0x00000000;
    public static final int REP = 0x00000004;

    public Exercise() {
        mSets = new ArrayList<>();
    }

    public Exercise(String name, @ExerciseType int type) {
        this();
        mName = name;
        mType = type;
    }

    public void addSet(String value) {
        mSets.add(new Set(mSets.size() + 1, value, mType));
    }

    /**
     * Removes a set from this exercise, if it exists. If it was removed, the number for all the other sets will be adjusted to account for the now deleted set.
     *
     * @param set
     */
    public void removeSet(Set set) {
        boolean removed = mSets.remove(set);
        if (removed) {
            // Need to sort all the sets to account for the deleted one
            for (int i = 0; i < mSets.size(); i++) {
                mSets.get(i).setNumber(i + 1);
            }
        }
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getType() {
        return mType;
    }

    public List<Set> getSets() {
        return mSets;
    }
}
