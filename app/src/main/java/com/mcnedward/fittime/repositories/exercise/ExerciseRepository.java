package com.mcnedward.fittime.repositories.exercise;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.SparseArray;

import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.models.History;
import com.mcnedward.fittime.models.WorkSet;
import com.mcnedward.fittime.repositories.DatabaseHelper;
import com.mcnedward.fittime.repositories.IRepository;
import com.mcnedward.fittime.repositories.Repository;
import com.mcnedward.fittime.repositories.workSet.WorkSetRepository;
import com.mcnedward.fittime.utils.Dates;
import com.mcnedward.fittime.utils.Extension;

import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public class ExerciseRepository extends Repository<Exercise> implements IRepository<Exercise> {

    private WorkSetRepository mSetRepository;

    public ExerciseRepository(Context context) {
        super(context);
        mSetRepository = new WorkSetRepository(context);
    }

    public History getHistoryForCurrentDate() {
        String dateStamp = Dates.getDatabaseDateStamp();
        // Get all the work sets first
        List<WorkSet> workSets = mSetRepository.getSetsForDay(dateStamp);
        // Then get the exercises for those work sets
        SparseArray<Exercise> exercises = new SparseArray();

        for (WorkSet workSet : workSets) {
            int exerciseId = workSet.getExerciseId();
            if (exercises.get(exerciseId) == null) {
                Exercise exercise = get(exerciseId);
                exercises.put(exerciseId, exercise);
            }
        }
        History history = new History(dateStamp, Extension.asList(exercises));
        return history;
    }

    @Override
    protected Exercise generateObjectFromCursor(Cursor cursor) {
        int id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.E_NAME));
        int type = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.E_TYPE));
        List<WorkSet> workSets = mSetRepository.getSetsByExerciseId(id);
        return new Exercise(id, name, type == 0 ? Exercise.TIMED : Exercise.REP, workSets);
    }

    @Override
    protected ContentValues generateContentValuesFromEntity(Exercise entity) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.ID, entity.getId());
        values.put(DatabaseHelper.E_NAME, entity.getName());
        values.put(DatabaseHelper.E_TYPE, entity.getType());
        return values;
    }

    @Override
    protected String getTableName() {
        return DatabaseHelper.EXERCISE_TABLE;
    }

    @Override
    protected String[] getAllColumns() {
        return new String[]{
                DatabaseHelper.ID,
                DatabaseHelper.E_NAME,
                DatabaseHelper.E_TYPE
        };
    }
}
