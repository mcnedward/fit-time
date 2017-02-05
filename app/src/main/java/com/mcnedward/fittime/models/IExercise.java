package com.mcnedward.fittime.models;

import java.util.List;

/**
 * TODO I'm not sure this interface is even really needed, but I'll leave it in for now.
 * Created by Edward on 2/5/2017.
 */

public interface IExercise {
    Set addSet(String value);
    void removeSet(Set set);
    String getName();
    void setName(String name);
    int getType();
    void setType(int type);
    List<Set> getSets();
}
