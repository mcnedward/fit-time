package com.mcnedward.fittime.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mcnedward.fittime.models.Exercise;

/**
 * Created by Edward on 2/1/2017.
 */

public class ExerciseRepository extends Repository<Exercise> implements IExerciseRepository {

    public ExerciseRepository(Context context) {
        super(context);
    }

    @Override
    protected String[] getAllColumns() {
        return new String[]{
                DatabaseHelper.E_NAME,
                DatabaseHelper.E_TYPE
        };
    }

    @Override
    protected Exercise generateObjectFromCursor(Cursor cursor) {
//        Exercise exercise = new Exercise();
//        exercise.setName(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.E_NAME)));
//        exercise.setType(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.E_TYPE)));
//        return exercise;
        return null;
    }

    @Override
    protected ContentValues generateContentValuesFromEntity(Exercise entity) {
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.E_NAME, entity.getName());
        values.put(DatabaseHelper.E_TYPE, entity.getType());
        return values;
    }

    @Override
    protected String getTableName() {
        return DatabaseHelper.EXERCISE_TABLE;
    }
}
