package com.mcnedward.fittime.models;

/**
 * Created by Edward on 2/1/2017.
 */

public class Set extends BaseEntity {

    private long mExerciseId;
    private int mNumber;
    private int mType;
    private String mValue;

    public Set(long id, long exerciseId, int number, int type, String value) {
        this(exerciseId, number, type, value);
        this.id = id;
    }

    Set(long exerciseId, int number, int type, String value) {
        mExerciseId = exerciseId;
        mNumber = number;
        mType = type;
        mValue = value;
    }

    public long getExerciseId() {
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

    @Override
    public String toString() {
        if (mType == Exercise.TIMED) {
            return String.format("%s @ %s", mNumber, mValue);
        } else if (mType == Exercise.REP) {
            return String.format("%s x %s", mNumber, mValue);
        }
        return String.format("Set #%s", mNumber);
    }

}
