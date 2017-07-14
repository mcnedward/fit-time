package com.mcnedward.fittime.models;

import java.util.List;

/**
 * Created by Edward on 7/12/2017.
 */

public class History extends BaseEntity {
    private String mHistoryDate;
    private List<Exercise> mExercises;

    public History(String historyDate, List<Exercise> exercises) {
        mHistoryDate = historyDate;
        mExercises = exercises;
    }

    public String getHistoryDate() {
        return mHistoryDate;
    }

    public void setHistoryDate(String historyDate) {
        mHistoryDate = historyDate;
    }

    public List<Exercise> getExercises() {
        return mExercises;
    }

    public void setExercises(List<Exercise> exercises) {
        mExercises = exercises;
    }
}
