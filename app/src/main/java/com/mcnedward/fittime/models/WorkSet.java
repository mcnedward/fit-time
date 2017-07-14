package com.mcnedward.fittime.models;

/**
 * Created by Edward on 2/1/2017.
 */

public class WorkSet extends BaseEntity {

    private int mExerciseId;
    private int mNumber;
    private int mType;
    private String mValue;
    private String mWorkDate;

    public WorkSet(int id, int exerciseId, int number, int type, String value) {
        this(exerciseId, number, type, value);
        this.id = id;
    }

    WorkSet(int exerciseId, int number, int type, String value) {
        mExerciseId = exerciseId;
        mNumber = number;
        mType = type;
        mValue = value;
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

    public void setWorkDate(String workDate) {
        mWorkDate = workDate;
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
