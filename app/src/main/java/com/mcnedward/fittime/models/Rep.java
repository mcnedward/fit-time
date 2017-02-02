package com.mcnedward.fittime.models;

/**
 * Created by Edward on 2/1/2017.
 */

public class Rep {

    private int mNumber;
    private String mTime;

    public Rep(int number, String time) {
        mNumber = number;
        mTime = time;
    }

    public int getNumber() {
        return mNumber;
    }

    public void setNumber(int number) {
        mNumber = number;
    }

    public String getTime() {
        return mTime;
    }

    @Override
    public String toString() {
        return String.format("%s @ %s", mNumber, mTime);
    }

}
