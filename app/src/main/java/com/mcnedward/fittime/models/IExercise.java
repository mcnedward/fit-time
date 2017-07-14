package com.mcnedward.fittime.models;

import java.util.List;

/**
 * TODO I'm not sure this interface is even really needed, but I'll leave it in for now.
 * Created by Edward on 2/5/2017.
 */

public interface IExercise {
    WorkSet createWorkSet(String value);
    void removeWorkSet(WorkSet workSet);
    String getName();
    void setName(String name);
    int getType();
    void setType(int type);
    List<WorkSet> getWorkSets();
}
