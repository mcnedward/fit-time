package com.mcnedward.fittime.repositories;

import com.mcnedward.fittime.models.Set;

import java.util.List;

/**
 * Created by Edward on 2/5/2017.
 */

public interface ISetRepository extends IRepository<Set> {

    List<Set> getSetsByExerciseId(long exerciseId);

}
