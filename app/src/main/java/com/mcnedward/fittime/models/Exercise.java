package com.mcnedward.fittime.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public class Exercise extends BaseEntity {
    private String mName;
    private String mType;
    private List<Rep> mReps;

    public Exercise() {
        mReps = new ArrayList<>();
    }

    public Exercise(String name, String type) {
        this();
        mName = name;
        mType = type;
    }

    public void addRep(Rep rep) {
        mReps.add(rep);
    }

    /**
     * Removes a rep from this exercise, if it exists. If it was removed, the number for all the other reps will be adjusted to account for the now deleted rep.
     *
     * @param rep
     */
    public void removeRep(Rep rep) {
        boolean removed = mReps.remove(rep);
        if (removed) {
            // Need to sort all the reps to account for the deleted one
            for (int i = 0; i < mReps.size(); i++) {
                mReps.get(i).setNumber(i + 1);
            }
        }
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public List<Rep> getReps() {
        return mReps;
    }

    public void setReps(List<Rep> reps) {
        mReps = reps;
    }
}
