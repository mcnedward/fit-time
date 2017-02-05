package com.mcnedward.fittime.models;

/**
 * Created by Edward on 2/1/2017.
 */

public class Set {

    private int mNumber;
    private String mValue;
    private int mType;

    public Set(int number, String value, int type) {
        mNumber = number;
        mValue = value;
        mType = type;
    }

    void setNumber(int number) {
        mNumber = number;
    }

    public int getNumber() {
        return mNumber;
    }

    public String getValue() {
        return mValue;
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
