package com.mcnedward.fittime.repositories;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.mcnedward.fittime.models.Exercise;
import com.mcnedward.fittime.models.Set;

import java.util.List;

/**
 * Created by Edward on 2/1/2017.
 */

public class ExerciseRepository extends Repository<Exercise> implements IExerciseRepository {

    private ISetRepository mSetRepository;

    public ExerciseRepository(Context context) {
        super(context);
        mSetRepository = new SetRepository(context);
    }

    @Override
    protected Exercise generateObjectFromCursor(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.ID));
        String name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.E_NAME));
        int type = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseHelper.E_TYPE));
        List<Set> sets = mSetRepository.getSetsByExerciseId(id);
        return new Exercise(id, name, type == 0 ? Exercise.TIMED : Exercise.REP, sets);
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
