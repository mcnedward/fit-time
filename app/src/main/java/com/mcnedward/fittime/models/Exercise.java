package com.mcnedward.fittime.models;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public class Exercise extends BaseEntity implements IExercise {

    public static final int TIMED = 0x00000000;
    public static final int REP = 0x00000004;
    private String mName;
    private int mType;
    private List<WorkSet> mWorkSets;

    public Exercise(Integer id, String name, @ExerciseType int type, List<WorkSet> workSets) {
        this.id = id;
        mName = name;
        mType = type;
        mWorkSets = workSets;
    }

    public Exercise(String name, @ExerciseType int type) {
        this(null, name, type, new ArrayList<>());
    }

    public WorkSet addSet(String value) {
        WorkSet workSet = new WorkSet(id, mWorkSets.size() + 1, mType, value);
        mWorkSets.add(workSet);
        return workSet;
    }

    /**
     * Removes a workSet from this exercise, if it exists. If it was removed, the number for all the other sets will be adjusted to account for the now deleted workSet.
     *
     * @param workSet
     */
    public void removeWorkSet(WorkSet workSet) {
        boolean removed = mWorkSets.remove(workSet);
        if (removed) {
            // Need to sort all the sets to account for the deleted one
            for (int i = 0; i < mWorkSets.size(); i++) {
                mWorkSets.get(i).setNumber(i + 1);
            }
        }
    }

    public void clearWorkSets() {
        mWorkSets.clear();
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

    public void setType(int type) {
        mType = type;
    }

    public List<WorkSet> getWorkSets() {
        return mWorkSets;
    }

    @IntDef({TIMED, REP})
    @Retention(RetentionPolicy.SOURCE)
    @interface ExerciseType {
    }

    @Override
    public String toString() {
        return mName;
    }
}
