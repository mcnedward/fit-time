package com.mcnedward.fittime.models;

import com.mcnedward.fittime.utils.Dates;

/**
 * This class represents a single work set for an exercise. This can be either the time set for a rep, or the number of reps.
 * Created by Edward on 2/1/2017.
 */
public class WorkSet extends BaseEntity {

    private int mExerciseId;        // The id of the exercise for this work set
    private int mType;              // The type of the work set, either timed or reps
    private String mValue;          // The value for the work set, either time or number of reps
    private String mWorkDate;       // The date the work set was logged
    private String mWorkDateTime;   // The date and time the work set was logged
    private boolean mLogged;        // Has this work set been logged in the db yet?

    /**
     * To be used only when building a work set from the db.
     */
    public WorkSet(int id, int exerciseId,int type, String value, String workDate, String workDateTime, boolean logged) {
        this.id = id;
        mExerciseId = exerciseId;
        mType = type;
        mValue = value;
        mWorkDate = workDate;
        mWorkDateTime = workDateTime;
        mLogged = logged;
    }

    WorkSet(int exerciseId, int type, String value) {
        mExerciseId = exerciseId;
        mType = type;
        mValue = value;
        // Set the date stamp when the work set is created
        mWorkDate = Dates.getDatabaseDateStamp();
        mWorkDateTime = Dates.getDatabaseDateTimeStamp();
    }

    public int getExerciseId() {
        return mExerciseId;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        mType = type;
    }

    public String getValue() {
        return mValue;
    }

    public void setValue(String value) {
        mValue = value;
    }

    public String getWorkDate() {
        return mWorkDate;
    }

    public String getWorkDateTime() {
        return mWorkDateTime;
    }

    public boolean isLogged() {
        return mLogged;
    }

    public void setIsLogged(boolean logged) {
        mLogged = logged;
    }

    public String toString(int workSetNumber) {
        if (mType == Exercise.TIMED) {
            return String.format("%s @ %s", workSetNumber, mValue);
        } else if (mType == Exercise.REP) {
            return String.format("%s x %s", workSetNumber, mValue);
        }
        String type = mType == Exercise.TIMED ? "Timed" : "Rep";
        return String.format("WorkSet %s: %s", type, mValue);
    }

    @Override
    public String toString() {
        String type = mType == Exercise.TIMED ? "Timed" : "Rep";
        return String.format("WorkSet %s: %s", type, mValue);
    }

}
