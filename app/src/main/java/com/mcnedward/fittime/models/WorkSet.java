package com.mcnedward.fittime.models;

/**
 * This class represents a single work set for an exercise. This can be either the time set for a rep, or the number of reps.
 * Created by Edward on 2/1/2017.
 */
public class WorkSet extends BaseEntity {

    private int mExerciseId;    // The id of the exercise for this work set
    private int mNumber;        // The order of the work set for an exercise
    private int mType;          // The type of the work set, either timed or reps
    private String mValue;      // The value for the work set, either time or number of reps
    private String mWorkDate;   // The date the work set was logged
    private boolean mLogged;    // Has this work set been logged in the db yet?

    public WorkSet(int id, int exerciseId, int number, int type, String value, String workDate, boolean logged) {
        this(exerciseId, number, type, value);
        this.id = id;
        mWorkDate = workDate;
        mLogged = logged;
    }

    WorkSet(int exerciseId, int number, int type, String value) {
        mExerciseId = exerciseId;
        mNumber = number;
        mType = type;
        mValue = value;
        mLogged = false;
    }

    public void log(String historyDate) {
        mWorkDate = historyDate;
        mLogged = true;
    }

    public int getExerciseId() {
        return mExerciseId;
    }

    public void setExerciseId(int exerciseId) {
        mExerciseId = exerciseId;
    }

    public int getNumber() {
        return mNumber;
    }

    void setNumber(int number) {
        mNumber = number;
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

    public boolean isLogged() {
        return mLogged;
    }

    @Override
    public String toString() {
        if (mType == Exercise.TIMED) {
            return String.format("%s @ %s", mNumber, mValue);
        } else if (mType == Exercise.REP) {
            return String.format("%s x %s", mNumber, mValue);
        }
        return String.format("WorkSet #%s", mNumber);
    }

}
